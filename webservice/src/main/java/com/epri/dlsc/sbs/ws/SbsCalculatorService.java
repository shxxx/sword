package com.epri.dlsc.sbs.ws;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author zhoutx
 * 配置说明
 * 1、创建 /WEB-INF/cluster-conf.properties配置文件,配置如下属性：
 * spark.home		设置spark主目录,例如:spark.home=/usr/spark-2.0.2-bin-hadoop2.6
 * spark.master 	设置spark集群的master节点,例如：spark.master=spark://dlsc-239:7077,node1:7077
 * class.fullname 	设置提交的jar程序包main函数所在类的全名,例如:class.fullname=com.sgcc.dlsc.sbs.submit.SubmitCalApp
 * application.jar	设置提交的jar包所在的位置,例如： application.jar=/home/sbs-calcore.jar
 */
public class SbsCalculatorService {

    private static String sparkMaster;
    private static String sparkHome;
    private static String classFullName;
    private static String applicationJar;
    private static volatile RunningCalcTask runningTasks = new RunningCalcTask();

    static{
        sparkHome = readConfigure("spark.home");
        sparkMaster = readConfigure("spark.master");
        classFullName = readConfigure("class.fullname");
        applicationJar = readConfigure("application.jar");
    }

    /**
     * 结算并行计算
     * @param marketId 场景
     * @param formulaType 公式类型
     * @param dateTime	日期
     * @param source	源
     * @param result	目标
     * @param scriptParams	参数  key1
     * @return String
     */
    public String requestCalculatorByFormulaType(
            String marketId,
            String formulaType,
            java.util.Date dateTime,
            String source,
            String result,
            HashMap<String, String> scriptParams){
        String calcDateTime = new SimpleDateFormat("yyyyMMdd").format(dateTime);
        if(sparkMaster == null){
            return returnValue(false, "服务配置信息错误");
        }
        Process applicationProcess;
        StringBuilder scriptParamsString = new StringBuilder();
        for(Map.Entry<String, String> entry: scriptParams.entrySet()){
            if(scriptParamsString.length() > 0) {
                scriptParamsString.append("&");
            }
            scriptParamsString.append(entry.getKey()).append("=").append(entry.getValue());
        }
        try {
            synchronized (SbsCalculatorService.class){
                if(runningTasks.isRunning(marketId, formulaType, calcDateTime)){
                    return returnValue(false, "任务正在执行");
                }
                runningTasks.add(marketId, formulaType, calcDateTime);
            }
            //初始化日志服务
            initLog();
            StringBuilder submitString = new StringBuilder();
            submitString.append(sparkHome).append("/bin/spark-submit")
                    .append(" --class ").append(classFullName)
                    .append(" --master ").append(sparkMaster)
                    .append(" ").append(applicationJar).append(" ")
                    .append(marketId).append(" ")
                    .append(calcDateTime).append(" ")
                    .append(formulaType).append(" ")
                    .append(source).append(" ")
                    .append(result).append(" ")
                    .append(scriptParamsString.toString().replace(" ", "#BlankSpace#")).append(" ")
                    .append("0");
            //提交程序
            applicationProcess = Runtime.getRuntime().exec(submitString.toString());
            //必须有下面两行代码，否则由于Process标准输入缓存有限会导致进程被挂起
            logging(applicationProcess.getInputStream());
            logging(applicationProcess.getErrorStream());
        } catch (Exception e1) {
            return error(e1);
        }
        try{
            applicationProcess.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            runningTasks.remove(marketId, formulaType, calcDateTime);
        }
        return returnValue(true, "完成");
    }

    public String requestCalculatorByFormulaIds(
            String marketId,
            String[] formulaIds,
            java.util.Date dateTime,
            String source,
            String result,
            HashMap<String, String> scriptParams){
        String calcDateTime = new SimpleDateFormat("yyyyMMdd").format(dateTime);
        if(formulaIds == null || formulaIds.length < 1){
            return returnValue(false, "没有待计算的公式");
        }
        if(sparkMaster == null){
            return returnValue(false, "服务配置信息错误");
        }
        StringBuilder formulaIdsString = new StringBuilder();

        for(String formulaId: formulaIds){
            if(formulaIdsString.length() > 0){
                formulaIdsString.append(",");
            }
            formulaIdsString.append(formulaId);
        }
        StringBuilder scriptParamsString = new StringBuilder();
        for(Map.Entry<String, String> entry: scriptParams.entrySet()){
            if(scriptParamsString.length() > 0) {
                scriptParamsString.append("&");
            }
            scriptParamsString.append(entry.getKey()).append("=").append(entry.getValue());
        }
        Process applicationProcess;
        try {
            synchronized (SbsCalculatorService.class){
                if(runningTasks.isRunning(marketId, formulaIdsString.toString(), calcDateTime)){
                    return returnValue(false, "任务正在执行");
                }
                runningTasks.add(marketId, formulaIdsString.toString(), calcDateTime);
            }
            //初始化日志服务
            initLog();

            StringBuilder submitString = new StringBuilder();
            submitString.append(sparkHome).append("/bin/spark-submit")
                    .append(" --class ").append(classFullName)
                    .append(" --master ").append(sparkMaster)
                    .append(" ").append(applicationJar).append(" ")
                    .append(marketId).append(" ")
                    .append(calcDateTime).append(" ")
                    .append(formulaIdsString).append(" ")
                    .append(source).append(" ")
                    .append(result).append(" ")
                    .append(scriptParamsString.toString().replace(" ", "#BlankSpace#")).append(" ")
                    .append("1");
            //提交程序
            applicationProcess = Runtime.getRuntime().exec(submitString.toString());
            //必须有下面两行代码，否则由于Process标准输入缓存有限会导致进程被挂起
            logging(applicationProcess.getInputStream());
            logging(applicationProcess.getErrorStream());

        } catch (Exception e1) {
            return error(e1);
        }
        try{
            applicationProcess.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            runningTasks.remove(marketId, formulaIdsString.toString(), calcDateTime);
        }
        return returnValue(true, "完成");
    }

    private String error(Exception e) {
        Map returnInfo = new HashMap();
        StringWriter sWriter = new StringWriter();
        PrintWriter pWriter = new PrintWriter(sWriter);
        e.printStackTrace(pWriter);
        String err = "服务异常:" + sWriter.toString();
        pWriter.close();
        returnInfo.put("successful", false);
        returnInfo.put("message", err);
        return JSON.toJSONString(returnInfo);
    }

    private String returnValue(boolean successful, String message){
        Map returnInfo = new HashMap();
        returnInfo.put("successful", successful);
        returnInfo.put("message", message);
        return JSON.toJSONString(returnInfo);
    }

    /**
     * 获取日志信息
     * @return
     */
    public String logPrint(){
        return logMessage();
    }

    //日志信息
    private static StringBuffer log;

    private String readerTmp;

    //初始化计算任务
    private void initLog(){
        log = new StringBuffer();
    }

    private String logMessage(){
        if(log != null){
            return log.toString();
        }else{
            return "";
        }
    }

    //日志信息
    private void logging(InputStream in){
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while((readerTmp = reader.readLine()) != null){
                        log.append(readerTmp).append("\r\n");
                        if(runningTasks.isEmpty()){
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally{
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    //读取配置信息
    private static String readConfigure(String key) {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("../cluster-conf.properties");
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(key, null);
        }catch (Exception ignored){}
        return null;
    }

}

class RunningCalcTask{
    private Set runningCalcTasks = new HashSet<String>();
    boolean isRunning(String flag1, String flag2, String flag3){
        return runningCalcTasks.contains(flag1 + flag2 + flag3);
    }
    void add(String flag1, String flag2, String flag3){
        runningCalcTasks.add(flag1 + flag2 + flag3);
    }
    void remove(String flag1, String flag2, String flag3){
        runningCalcTasks.remove(flag1 + flag2 + flag3);
    }
    boolean isEmpty(){
        return runningCalcTasks.isEmpty();
    }
}
