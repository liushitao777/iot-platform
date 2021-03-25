package com.cpiinfo.iot.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于切换数据源的核心注解。它可以在类或方法中进行注释。
 * @author TaoYu Kanyuxia
 * @since 1.0.0
 */
//ElementType.TYPE用于描述类、接口(包括注解类型) 或enum声明
//ElementType.METHOD用于描述方法
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DS {

  /**
   * groupName or specific database name or spring SPEL name.
   *
   * @return the database you want to switch
   */
  String value();
}