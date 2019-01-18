package com.epri.dlsc.sbs.functions.type;

/**
 * 函数算法参数类型定义
 */
public enum ArgumentType{
	//字符串
	STRING {
		@Override
		public String toString() {
			return "string";
		}
	},
	//数字
	NUMBER {
		@Override
		public String toString() {
			return "number";
		}
	},
	//数据集
	DATASET {
		@Override
		public String toString() {
			return "dataset";
		}
	};
	public abstract String toString();
}
