package top.xiaowoa.mybatisflex.extension.annotations;

import com.mybatisflex.core.constant.SqlConsts;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Select {
    /**
     * 查询的字段
     */
    String[] columns() default SqlConsts.ASTERISK;

    /**
     * entity 类
     */
    Class<?> entityClasses();
}
