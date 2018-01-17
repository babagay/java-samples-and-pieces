package JavaCore.Module06;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * https://habrahabr.ru/company/e-Legion/blog/206208/
 * https://habrahabr.ru/post/88908/
 */

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Foo
{

    String type() default "Object";

    String[] tags() default "";
}
