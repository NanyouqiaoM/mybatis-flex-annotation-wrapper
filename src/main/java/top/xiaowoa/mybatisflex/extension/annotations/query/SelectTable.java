package top.xiaowoa.mybatisflex.extension.annotations.query;

import com.mybatisflex.core.constant.SqlConsts;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface SelectTable {
    /**
     * 表名
     */
    String name();

    /**
     * 别名
     */
    String alias();

    /**
     * 查询字段
     */
    SelectColumn[] columns() default @SelectColumn(name = SqlConsts.ASTERISK, alias = "");
}
