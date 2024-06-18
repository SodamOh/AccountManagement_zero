package com.example.account.aop;

/*
 동시성을 제어하기 위해 레디스, 어노테이션을 활용하는 실습
 */

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface AccountLock {

}
