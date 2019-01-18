package com.epri.dlsc.sbs.functions.inner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.epri.dlsc.sbs.annocation.FunctionDescription;
import com.epri.dlsc.sbs.annocation.FunctionMethod;
import com.epri.dlsc.sbs.annocation.FunctionTemplate;
import com.epri.dlsc.sbs.annocation.FunctionVariable;
import com.epri.dlsc.sbs.annocation.UsingMarket;
import com.epri.dlsc.sbs.functions.custom.GroupByPriority;
import com.epri.dlsc.sbs.functions.type.ArgumentType;
import com.epri.dlsc.sbs.functions.type.DataSetRow;
import com.epri.dlsc.sbs.functions.type.MarketIdentifier;

final public class STD {
	@UsingMarket({MarketIdentifier.ALL})
	@FunctionMethod("四舍五入算法")
	@FunctionTemplate("round(数值,精度)")
	@FunctionDescription("<pre>"
			+ "(1)\n"
			+ "var value1 = <B>round(#D[数据集.值,匹配项<...>,过滤项<...>],精度)</B>;\n"
			+ "(2)\n"
			+ "var tmpValue = #D[数据集.值,匹配项<...>,过滤项<...>]</B>;\n"
			+ "var value2 = <B>round(tmpValue,精度)</B>;</pre>")
    public double round(
    		@FunctionVariable(type = ArgumentType.NUMBER, desc = "数据集表达式") double value,
    		@FunctionVariable(type = ArgumentType.NUMBER, desc = "精度") int precision){
		return new BigDecimal(Double.toString(value)).setScale(precision, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
	
	/**
	 * 数据集对象
	 * var dataSetObj = DataSet.toObject(#D[数据集.值,匹配项<...>,过滤项<...>]);
	 * var value = dataSetObj.value; 与 dataSetObj.getAttribute("值")等价
	 * var unitName = dataSetObj.getAttribute("结算单元ID");
	 * var unitNetFee = dataSetObj.getAttribute("电费");
	 * 
	 * @param rows
	 * @return
	 */
	@UsingMarket({MarketIdentifier.ALL})
	@FunctionMethod("将数据集表达式转成JS对象")
	@FunctionTemplate("toObject(数据集)")
	@FunctionDescription("<pre>"
			+ "var dataSetObj = <B>toObject(#D[数据集.值,匹配项<...>,过滤项<...>])</B>;\n"
			+ "var value = dataSetObj.getValue(); //与 dataSetObj.getAttribute(\"值\");\n"
			+ "var unitName = dataSetObj.getAttribute(\"结算单元ID\"); \n"
			+ "var unitNetFee = dataSetObj.getAttributeValue(\"电费\"); </pre>")
    public DataSetRow toObject(
    		@FunctionVariable(type = ArgumentType.DATASET, desc = "数据集表达式") List<DataSetRow> rows){
        return rows == null ? null : rows.get(0);
    }
	/**
	 * 数据集数组对象
	 * var dataSetObjs = DataSet.toArrayObject(#D[上网电量数据源.上网电量,匹配项<...>,过滤项<...>]);
	 * if(dataSetObjs != null && dataSetObjs.length > 0){
	 * 	  for(var i = 0; i < dataSetObjs.length; i++){
	 *       if(dataSetObjs[i].value < 0){
	 *         dataSetObjs[i].setAttribute("备注", "上网电量不正确");
	 *         ....
	 *       }
	 * 	  }
	 * }
	 * 
	 * @param rows
	 * @return
	 */
	@UsingMarket(MarketIdentifier.ALL)
	@FunctionMethod("将数据集表达式转成JS对象数组")
	@FunctionTemplate("toArrayObject(数据集)")
	@FunctionDescription("<pre>"
			+ "var dataSetObjs = <B>toArrayObject(#D[上网电量数据源.上网电量,匹配项<结算单元,..>,过滤项<...>])</B>;\n"
			+ "if(dataSetObjs != null && dataSetObjs.length > 0){\n"
			+ "   for(var i = 0; i < dataSetObjs.length; i++){\n"
			+ "      var 上网电量 = dataSetObjs[i].getValue();//等价于getAttributeValue(\"上网电量\"); \n"
            + "      var 结算单元ID = dataSetObjs[i].getAttribute(\"结算单元\"); \n"
			+ "		 ...\n"
			+ "   }\n"
			+ "}</pre>")
    public DataSetRow[] toArrayObject(
    		@FunctionVariable(type = ArgumentType.DATASET, desc = "数据集表达式") List<DataSetRow> rows){
    	if(rows == null || rows.size() == 0) {
    		return null;
    	}
        return rows.toArray(new DataSetRow[rows.size()]);
    }
	/**
	 * var 存在吗 = DataSet.isExist(#D[数据集.值,匹配项<...>,过滤项<...>]);
	 * if(存在吗 == false){
	 *     return 0;
	 * }
	 * @param rows
	 * @return
	 */
	@UsingMarket(MarketIdentifier.ALL)
	@FunctionMethod("判断是否能够匹配到该数据集")
	@FunctionTemplate("isExist(数据集)")
	@FunctionDescription("<pre>"
			+ "var 存在吗 = <B>isExist(#D[数据集.值,匹配项<...>,过滤项<...>])</B>;\n"
			+ "if(存在吗 == false){\n"
			+ "	   ...\n"	
			+ "    return 123;\n"
			+ "}</pre>")
    public boolean isExist(@FunctionVariable(type = ArgumentType.DATASET, desc = "数据集表达式") List<DataSetRow> rows){
        return rows != null && rows.size() > 0;
    }
	/**
	 * 求和算法
	 * @param rows
	 * @return
	 */
	@UsingMarket(MarketIdentifier.ALL)
	@FunctionMethod("数据集求和算法")
	@FunctionTemplate("sum(数据集)")
	@FunctionDescription("<pre>"
			+ "var 多个数据集求和 = <B>sum(#D[数据集.值,匹配项<...>,过滤项<...>])</B>;\n"
			+ "</pre>")
	//在分布式计算中采用spark的sum函数
    public double sum(@FunctionVariable(type = ArgumentType.DATASET, desc = "数据集表达式") List<DataSetRow> rows){
		if(rows == null || rows.size() == 0){
        	return 0;
        }
		BigDecimal sum = new BigDecimal(0);
        for(DataSetRow row: rows) {
        	double value = row.getValue();
        	sum = sum.add(new BigDecimal(value));
        }
        return sum.doubleValue();
    }
	/**
	 * 均值算法
	 * @param rows
	 * @return
	 */
	@UsingMarket(MarketIdentifier.ALL)
	@FunctionMethod("数据集均值算法")
	@FunctionTemplate("average(数据集)")
	@FunctionDescription("<pre>"
			+ "var 平均值 = <B>average(#D[数据集.值,匹配项<...>,过滤项<...>])</B>;\n"
			+ "</pre>")
	//在分布式计算中，采用spark中的avg函数
	public double average(@FunctionVariable(type = ArgumentType.DATASET, desc = "数据集表达式") List<DataSetRow> rows){
		if(rows == null || rows.size() == 0){
        	return 0;
        }
		double sum = sum(rows);
		double average = new BigDecimal(sum).divide(new BigDecimal(rows.size()), 3, RoundingMode.HALF_UP).doubleValue();
		return average;
	}
	
	/**
	 * 数据分解算法（一对多用法）
	 * @param totalEnergy 待分解总电量
	 * @param datas	数据集数据
	 * @param refrenceField 分解优先级参数"refrenceField>"数值大的优先  "refrenceField<"数值小的优先 " refrenceField"与"refrenceField="相同,按prio1值的比例
	 * @return 
	 */
	@UsingMarket(MarketIdentifier.ALL)
	@FunctionMethod("数据分摊算法")
	@FunctionTemplate("divide(总电量, 数据集, \"参照字段1, 参照字段2, ...\")")
	@FunctionDescription("<pre>"
			+ "(1)使用变量:\n"
			+ "var 上网电量 = #D[上网电量数据集.上网电量,匹配项<...>,过滤项<...>];\n"
			+ "<B>divide(上网电量, #D[结算成分数据集.应结电量,匹配项<...>,过滤项<...>], \"机组容量>,合同价格<,合同电量\")</B>;\n"
			+ "return #D[结算成分数据集.应结电量,匹配项<...>,过滤项<...>];\n"
			+ "(2)直接使用数据集:\n"
			+ "<B>divide(#D[上网电量数据集.上网电量,匹配项<...>,过滤项<...>],\n"
			+ "    #D[结算成分数据集.应结电量,匹配项<...>,过滤项<...>],\n"
			+ "    \"机组容量>,合同价格<,合同电量\")</B>;\n"
			+ "<B>第三参数-参照字段的使用说明:</B>\n"
			+ "假设数据集中包含另外三个A,B,C数值型字段\n"
			+ "<B>a. \"A>,B<,C\"</B>  表示先参照A字段值大的优先,然后参照B字段小的优先,A和B相同的最后参照C字段的比例\n"
			+ "<B>b. \"A>,B<\"</B>    表示先参照A字段值大的优先,然后参照B字段小的优先,A和B相同的等比例均分\n"
			+ "<B>c. \"C\"</B>        表示直接按照C字段比例分摊\n"
			+ "</pre>")
	public void divide(@FunctionVariable(type=ArgumentType.NUMBER, desc="数值变量或数据集-总电量") double totalEnergy, 
					   @FunctionVariable(type=ArgumentType.DATASET, desc="数据集-数据分解目标") List<DataSetRow> datas, 
					   @FunctionVariable(type=ArgumentType.STRING, desc="字符串-数据分解参照字段") String refrenceField){
		if(refrenceField == null || "".equals(refrenceField.trim())) {
			throw new RuntimeException("缺少数据分解参照参数");
		}
		divideAlgorithm(totalEnergy, datas, refrenceField.split(","));
	}
	private void divideAlgorithm(double energy, List<DataSetRow> datas, String ... prios){
		BigDecimal totalEnergy = new BigDecimal(energy);
		GroupByPriority groupByPriority = new GroupByPriority(datas, prios);
		//1、prios最后一个是=，或者不带=的，表示按此列的比例分摊
		String lastPrio = prios[prios.length - 1];
		if(!lastPrio.contains(">") && !lastPrio.contains("<")) {
			//按比例分配
			divideByReference(totalEnergy, groupByPriority, lastPrio);
		}else if(lastPrio.contains(">") || lastPrio.contains("<")) {
			//2、prios最后一个是>，或者<的，表示按优先级递减式分摊，电量不够时，同一优先级的均分处理
			surplusDivideByReference(totalEnergy, groupByPriority, lastPrio);
		}
	}
	/**
	 * 比例式
	 * 1、当只有一个组时，按比例分配
	 * 2、当有多个优先级组时：
	 * 	1）总数偏少时，计算到的最后一个组按比例分配；
	 *  2）总数偏大时，最后一组将兜底数据按比例分配
	 * @param totalEnergy 总电量
	 * @param groupByPriority 多个优先级分租
	 * @param referenceAttributeName 参考字段
	 */
	private void divideByReference(
			BigDecimal totalEnergy,
			GroupByPriority groupByPriority,
			String referenceAttributeName) {
		List<List<DataSetRow>> groups = groupByPriority.getGroup();
		if(groups != null && groups.size() == 1) {
			divideByReference(totalEnergy, groups.get(0), referenceAttributeName);
		}else if(groups.size() > 1) {
			BigDecimal surplusSum = totalEnergy;
			for(int g = 0; g < groups.size(); g++) {
				List<DataSetRow> group = groups.get(g);
				BigDecimal oneGroupSum = oneGroupSum(group);
				if(surplusSum.doubleValue() > 0 && surplusSum.doubleValue() < oneGroupSum.doubleValue()) {
					divideByReference(surplusSum, group, referenceAttributeName);
				}else if(surplusSum.doubleValue() <= 0) {
					oneGroupSetupZero(group);
				}else if(g == groups.size() - 1) {//最后一组兜底数据较多时
					divideByReference(surplusSum, group, referenceAttributeName);
				}
				surplusSum = surplusSum.subtract(oneGroupSum);
			}
		}
	}
	/**递减式
	 * 1、当只有一个组时，平均分配
	 * 2、当有多个优先级组时：
	 * 	1）总数偏少时，计算到的最后一个组平均分配；
	 *  2）总数偏大时，最后一组将兜底数据平均分配
	 * @param totalEnergy
	 * @param groupByPriority
	 * @param referenceAttributeName
	 */
	private void surplusDivideByReference(
			BigDecimal totalEnergy,
			GroupByPriority groupByPriority,
			String referenceAttributeName) {
		List<List<DataSetRow>> groups = groupByPriority.getGroup();
		if(groups != null && groups.size() == 1) {
			divideByAvg(totalEnergy, groups.get(0));
		}else if(groups.size() > 1) {
			BigDecimal surplusSum = totalEnergy;
			for(int g = 0; g < groups.size(); g++) {
				List<DataSetRow> group = groups.get(g);
				BigDecimal oneGroupSum = oneGroupSum(group);
				if(surplusSum.doubleValue() > 0 && surplusSum.doubleValue() < oneGroupSum.doubleValue()) {
					divideByAvg(surplusSum, group);
				}else if(surplusSum.doubleValue() <= 0) {
					oneGroupSetupZero(group);
				}else if(g == groups.size() - 1) {//最后一组兜底数据较多时
					divideByAvg(surplusSum, group);
				}
				surplusSum = surplusSum.subtract(oneGroupSum);
			}
		}
	}
	
	//单组求和
	private BigDecimal oneGroupSum(List<DataSetRow> group) {
		BigDecimal sum = new BigDecimal(0); 
		if(group != null && group.size() > 0) {
			for(DataSetRow row: group) {
				sum = sum.add(new BigDecimal(row.getValue()));
			}
		}
		return sum;
	}
	//单组全部置零
	private void oneGroupSetupZero(List<DataSetRow> group) {
		if(group != null) {
			for(DataSetRow row: group) {
				row.setValue("0");
			}
		}
	}
	
	//平均分配
	private void divideByAvg(BigDecimal totalEnergy, List<DataSetRow> rows) {
		BigDecimal sum = new BigDecimal(0);
		if(rows != null && rows.size() > 0) {
			BigDecimal scale = new BigDecimal(1).divide(new BigDecimal(rows.size()), 10, RoundingMode.HALF_UP);
			for(int i = 0; i < rows.size(); i++) {
				if(i == rows.size() - 1) {
					rows.get(i).setValue(totalEnergy.subtract(sum).toString());
				}else {
					BigDecimal value = totalEnergy.multiply(scale).setScale(3, RoundingMode.HALF_UP);
					sum = sum.add(value);
					rows.get(i).setValue(value.toString());
				}
			}
		}
	}
	//按比例分配
	private void divideByReference(BigDecimal totalEnergy, List<DataSetRow> rows, String referenceAttributeName) {
		BigDecimal referenceSum = new BigDecimal(0);
		BigDecimal splitSum = new BigDecimal(0);
		if(rows != null && rows.size() > 0) {
			for(DataSetRow row: rows) {
				Object valueObj = row.getAttribute(referenceAttributeName);
				BigDecimal refValue = valueObj == null ? new BigDecimal(0) : new BigDecimal(valueObj.toString());
				referenceSum = referenceSum.add(refValue);
			}
			for(int i = 0; i < rows.size(); i++) {
				if(i == rows.size()-1) {
					rows.get(i).setValue(totalEnergy.subtract(splitSum).toString());
				}else {
					Object valueObj = rows.get(i).getAttribute(referenceAttributeName);
					BigDecimal refValue = valueObj == null ? new BigDecimal(0) : new BigDecimal(valueObj.toString());
					BigDecimal scale = refValue.divide(referenceSum, 10, RoundingMode.HALF_UP);
					BigDecimal resultValue = totalEnergy.multiply(scale).setScale(3, RoundingMode.HALF_UP);
					rows.get(i).setValue(resultValue.toString());
					splitSum = splitSum.add(resultValue);
				}
			}
		}
	}
}