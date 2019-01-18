package com.epri.dlsc.sbs.annocation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.epri.dlsc.sbs.functions.type.MarketIdentifier;


/**
 * 方法调用场景申明
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsingMarket {
	public abstract MarketIdentifier[] value();
}