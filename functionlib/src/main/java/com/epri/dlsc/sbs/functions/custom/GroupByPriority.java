package com.epri.dlsc.sbs.functions.custom;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.epri.dlsc.sbs.functions.type.DataSetRow;

/**
 * @author zhoutx
 * 排序字段名[>,<]
 *  "排序字段名>"值大优先，即从大到小排序
 *  "排序字段名<"值小优先，即从小到大排序
 */
public final class GroupByPriority{
	Pattern priorPattern = Pattern.compile("^\\s*(.+?)([><])*\\s*$");
	//优先级从高到低排序分组
	private List<List<DataSetRow>> group = new ArrayList<List<DataSetRow>>();
	
	private GroupByPriority(){}
	
	public GroupByPriority(List<DataSetRow> datas, String[] priors){
		executeGroup(datas, priors);
	}
	//执行按优先级分组
	private void executeGroup(List<DataSetRow> datas, String[] priors){
		if(datas != null && datas.size() > 0 && priors != null && priors.length > 0){
			//优先级因素从左至右排列，最左侧起决定性作用，即为最后的排序条件
			for(int i = priors.length -1; i >= 0; i--){
				String priorKey = priors[i];
				Matcher matcher = priorPattern.matcher(priorKey);
				if(matcher.find()){
					final String pname = matcher.group(1);
					final String psymbol = matcher.group(2);
					Collections.sort(datas, new Comparator<DataSetRow>(){
						public int compare(DataSetRow o1, DataSetRow o2) {
							String left = o1.getAttribute(pname) == null ? "0" : o1.getAttribute(pname).toString();
							String right = o2.getAttribute(pname) == null ? "0" : o2.getAttribute(pname).toString();
							int compareValue = 0;
							if(Double.valueOf(left) > Double.valueOf(right)){
								compareValue = 1;
							}
							else if(Double.valueOf(left) < Double.valueOf(right)){
								compareValue = -1;
							}
							else{
								compareValue = 0;
							}
							if(">".equals(psymbol)){
								compareValue *= -1;
							}
							return compareValue;
						}
					});
				}
			}
			//按照排好的顺序进行分组
			for(int i = 0; i < datas.size(); i++){
				for(int j = 0; j < priors.length; j++){
					if(i == 0){
						List<DataSetRow> firstGroup = new ArrayList<DataSetRow>();
						firstGroup.add(datas.get(0));
						group.add(firstGroup);
						break;
					}else{
						String priorKey = priors[j];
						Matcher matcher = priorPattern.matcher(priorKey);
						if(matcher.find()){
							final String pname = matcher.group(1);
							final String psymbol = matcher.group(2);
							if("<".equals(psymbol) || ">".equals(psymbol)){
								
								String prev = datas.get(i).getAttribute(pname) == null ? "0" : datas.get(i).getAttribute(pname).toString();
								String curr = datas.get(i-1).getAttribute(pname) == null ? "0" : datas.get(i-1).getAttribute(pname).toString();
								
								if(prev.compareTo(curr) != 0){
									List<DataSetRow> otherGroup = new ArrayList<DataSetRow>();
									otherGroup.add(datas.get(i));
									group.add(otherGroup);
									break;
								}
							}
						}
					}
					//与上一个所有优先级参考因素都相同，则两者优先级一致，属于同一个分组中
					if(j == priors.length - 1){
						group.get(group.size() -1).add(datas.get(i));
					}
				}
			}
		}
	}
	
	public List<List<DataSetRow>> getGroup() {
		return group;
	}
}