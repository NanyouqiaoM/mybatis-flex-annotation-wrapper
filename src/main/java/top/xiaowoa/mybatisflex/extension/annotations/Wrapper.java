package top.xiaowoa.mybatisflex.extension.annotations;

import top.xiaowoa.mybatisflex.extension.enums.SqlOperator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface Wrapper {
    /**
     * 列名 默认字段名驼峰转下划线
     */
    String column() default "";

    /**
     * 操作符
     */
    SqlOperator operator() default SqlOperator.EQUALS;
}
