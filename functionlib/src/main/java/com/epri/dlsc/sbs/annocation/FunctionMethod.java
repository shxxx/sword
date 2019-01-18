package com.epri.dlsc.sbs.annocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 用于标记方法是公式可以直接调用的函数算法
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionMethod {
	//函数算法方法简述
	public abstract String value();
}
