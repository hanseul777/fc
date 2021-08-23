package org.zerock.fc.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)//어노테이션을(클래스선언부)에 건다 -> 타겟을 지정
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {

    String value();
}
