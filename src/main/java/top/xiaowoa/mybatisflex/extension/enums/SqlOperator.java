package top.xiaowoa.mybatisflex.extension.enums;

import com.mybatisflex.core.constant.SqlConsts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PUBLIC)
public enum SqlOperator {
    /**
     * ignore
     */
    IGNORE(""),

    /**
     * >
     */
    GT(SqlConsts.GT),

    /**
     * >=
     */
    GE(SqlConsts.GE),

    /**
     * <
     */
    LT(SqlConsts.LT),

    /**
     * <=
     */
    LE(SqlConsts.LE),

    /**
     * like %value%
     */
    LIKE(SqlConsts.LIKE),

    /**
     * like value%
     */
    LIKE_LEFT(SqlConsts.LIKE),

    /**
     * like %value
     */
    LIKE_RIGHT(SqlConsts.LIKE),

    /**
     * not like %value%
     */
    NOT_LIKE(SqlConsts.NOT_LIKE),

    /**
     * not like value%
     */
    NOT_LIKE_LEFT(SqlConsts.NOT_LIKE),

    /**
     * not like %value
     */
    NOT_LIKE_RIGHT(SqlConsts.NOT_LIKE),

    /**
     * =
     */
    EQUALS(SqlConsts.EQUALS),

    /**
     * !=
     */
    NOT_EQUALS(SqlConsts.NOT_EQUALS),

    /**
     * is null
     */
    IS_NULL(SqlConsts.IS_NULL),

    /**
     * is not null
     */
    IS_NOT_NULL(SqlConsts.IS_NOT_NULL),

    /**
     * in
     */

    IN(SqlConsts.IN),

    /**
     * not in
     */

    NOT_IN(SqlConsts.NOT_IN),

    /**
     * between
     */

    BETWEEN(SqlConsts.BETWEEN),

    /**
     * not between
     */

    NOT_BETWEEN(SqlConsts.NOT_BETWEEN);

    /**
     * sql value
     */
    String value;
}
