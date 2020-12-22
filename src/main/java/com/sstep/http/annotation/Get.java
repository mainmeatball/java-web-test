package com.sstep.http.annotation;

import java.lang.annotation.*;


/**
 * @author sstepanov
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Get {

    String[] value() default "/";
}
