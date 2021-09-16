/*Copyright (C) 2020 THL A29 Limited, a Tencent company.  All rights reserved.

This source code is licensed under the Apache License Version 2.0.*/


package com.stone.apijson.extend;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**实现多重注解
 * @author Lemon
 */
@Documented
@Retention(RUNTIME)
@Target(TYPE)
@Inherited
public @interface RequestStructures {
	RequestStructure[] value();
}
