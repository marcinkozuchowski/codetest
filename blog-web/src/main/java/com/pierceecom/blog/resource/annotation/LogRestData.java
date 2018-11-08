package com.pierceecom.blog.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/**
 * Method marked with this annotation will have logged all request and response data.
 * If type is marked then it applies to all its (exposed as rest service) methods.
 * @author marcin.kozuchowski
 *
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface LogRestData {

}
