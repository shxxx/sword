package com.epri.dlsc.sbs.annocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epri.dlsc.sbs.functions.type.ArgumentType;


/**
 * 用于标记函数算法方法中的参数说明
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface FunctionVariable {
	public abstract ArgumentType type();
	public abstract String desc();
}
