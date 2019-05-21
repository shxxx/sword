package com.epri.dlsc.sbs.calc.service

import java.text.SimpleDateFormat

object SubmitJob {

  def main(args: Array[String]): Unit = {
    val marketId = args(0)
    //yyyyMMdd
    val calcDateTime = args(1)
    //formulaType: xxx    formulaIds: id1,id2,id3,...
    val formulaTypeOrIds = args(2)
    val sourceFlag = args(3)
    val resultFlag = args(4)
    //key1=value1&key2=value3
    val scriptParams = args(5)
    //参数args(2)的类型标识  0表示formulaType 1表示formulaId
    val isFormulaId = args(6)

    var paramsObj: ScriptParams = null
    if(scriptParams != null && scriptParams.length > 0){
      val kvs = scriptParams.replace("#BlankSpace#", " ").split("&").map(kv => {
        val s = kv.split("=")
        (s(0), s(1))
      })
      for(kv <- kvs){
        if(paramsObj == null){
          paramsObj = ScriptParams.add(kv._1, kv._2)
        }else{
          paramsObj = paramsObj.add(kv._1, kv._2)
        }
      }
    }

    isFormulaId match {
      case "1" => SettleSession.newCreate
        .config(marketId, SourceTarget(sourceFlag, resultFlag),  paramsObj,  formulaTypeOrIds.split(","))
        .submit()
      case "0" => SettleSession.newCreate
        .config(marketId, SourceTarget(sourceFlag, resultFlag),  paramsObj, new SimpleDateFormat("yyyyMMdd").parse(calcDateTime), formulaTypeOrIds)
        .submit()
    }

  }
}
