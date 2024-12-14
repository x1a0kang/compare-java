package org.x1a0kang.compare.common.factory;

/**
 * Created on 2023-11-23
 *
 * @author jiangning
 * @description 自定义LoggerFactory，Aspectj编织org.slf4j.Logger外的解决方案
 **/
public final class CustomLoggerFactory {

    public static Logger getLogger(Class<?> clazz) {
        return new Logger(org.slf4j.LoggerFactory.getLogger(clazz));
    }

    public static Logger getLogger(String name) {
        return new Logger(org.slf4j.LoggerFactory.getLogger(name));
    }
}
