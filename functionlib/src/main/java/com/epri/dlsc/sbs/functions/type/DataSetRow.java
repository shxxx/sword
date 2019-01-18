package com.epri.dlsc.sbs.functions.type;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
/**
 * 数据集中的一行数据对象
 */
public class DataSetRow {
	private Map<String, String> row;
	private String _Value_Field_ID_;
    
    public DataSetRow(String _Value_Field_ID_, Map<String, String> row) {
    	this._Value_Field_ID_ = _Value_Field_ID_;
    	this.row = row;
    }

    public void setValue(String value){
    	row.put(_Value_Field_ID_, value);
    }
    public void setValue(double value) {
    	row.put(_Value_Field_ID_, String.valueOf(value));
    }
    public double getValue(){
    	Object value = row.get(_Value_Field_ID_);
    	if(value != null) {
    		try {
    			return Double.valueOf(value.toString());
    		}catch(Exception e) {
    			e.printStackTrace();
    			System.out.println("getValue不能获取一个数字类型的值");
    		}
    	}
    	return 0D;
    }
    
    public void setAttribute(String attributeName, Object value) {
    	String _attribute_id_ = _Field_ID_(attributeName.trim());
    	row.put(_attribute_id_, value.toString());
    }
    /**
     * JDK6 JS执行不能根据实际类型自动转换，全部按字符串处理
     * 公式定义采用getAttribute()方法获取数据后，需要使用JS-Number()方法强制转换
     * 例如:var value = dataSetObject.getAttribute("电量");
     * 	   var b = Number(value) + 10;
     * 否则加法运算符将当成字符串拼接符
     * JDK8 能够根据实际数据类型自动转存，则不存在这个问题
     * @param attributeName
     * @return
     */
    public String getAttribute(String attributeName){
    	if(attributeName == null || "".equals(attributeName.trim())){
    		return null;
    	}
    	String _attribute_id_ = _Field_ID_(attributeName.trim());
		Object attributeValue = row.get(_attribute_id_);
		if(attributeValue == null) {
			return null;
		}
        return attributeValue.toString();
    }
    public double getAttributeValue(String attributeName) {
    	if(attributeName == null || "".equals(attributeName.trim())){
    		return 0D;
    	}
    	String _attribute_id_ = _Field_ID_(attributeName.trim());
		Object attributeValue = row.get(_attribute_id_);
    	if(attributeValue != null) {
    		try {
    			return Double.valueOf(attributeValue.toString());
    		}catch(Exception e) {
    			e.printStackTrace();
    			System.out.println("getValue不能获取一个数字类型的值");
    		}
    	}
        return 0D;
    }
    
    private static String _Field_ID_(String fieldName) {
      MessageDigest md = null;
    	try {
    		md = MessageDigest.getInstance("MD5");
    	} catch (NoSuchAlgorithmException e) {
    		e.printStackTrace();
    	}
      md.update(fieldName.getBytes());
      return new BigInteger(1, md.digest()).toString(16);
    }
}
