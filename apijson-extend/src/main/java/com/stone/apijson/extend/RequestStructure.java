/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package com.stone.apijson.extend;

import apijson.RequestMethod;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**通过注解实现对请求的设置，通过代码实现，避免了对request的操作
 * @author Lemon
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
@Repeatable(RequestStructures.class)
public @interface RequestStructure {
	RequestMethod method() default RequestMethod.GET;
	String tag() default "";
	String structure() default  "{}";
	String detail() default "";
	int version() default 1;
}
