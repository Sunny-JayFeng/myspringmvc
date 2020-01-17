package myspringmvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SessionAttribute {

    //  @SessionAttributes 只能作用在类上，
    //  作用是将指定的Model中的键值对添加至session中，方便在下一次请求中使用。
    //  目标是通过 @SessionAttributes 注解将Model中attrName为 "user","age","name" 的值添加至 session 中

    String[] value();
}
