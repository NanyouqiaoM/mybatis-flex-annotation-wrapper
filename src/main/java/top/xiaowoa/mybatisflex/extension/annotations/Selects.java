package top.xiaowoa.mybatisflex.extension.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
public @interface Selects {
    Select[] value();
}
