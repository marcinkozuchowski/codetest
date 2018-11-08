package com.pierceecom.blog.resource.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

/**
 * Marks method or type that it requires non empty body. Otherwise 405 error code is thrown.
 * @author marcin.kozuchowski
 *
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.TYPE})
public @interface EmptyBodyNotAllowed {

}
