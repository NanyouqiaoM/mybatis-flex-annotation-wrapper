package top.xiaowoa.mybatisflex.extension.builder;

import com.mybatisflex.core.constant.SqlConsts;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.table.BaseReflectorFactory;
import com.mybatisflex.core.table.EntityMetaObject;
import com.mybatisflex.core.util.Reflectors;
import com.mybatisflex.core.util.StringUtil;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.Reflector;
import org.apache.ibatis.reflection.ReflectorFactory;
import top.xiaowoa.mybatisflex.extension.annotations.Select;
import top.xiaowoa.mybatisflex.extension.annotations.Where;
import top.xiaowoa.mybatisflex.extension.annotations.query.SelectTable;
import top.xiaowoa.mybatisflex.extension.enums.SqlOperator;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mybatisflex.core.constant.SqlConsts.REFERENCE;

public class QueryWrapperBuilder {
    public static QueryWrapper build(Object object) {
        QueryWrapper query = new QueryWrapper();
        if (object == null) {
            return query;
        }
        Class<?> clazz = object.getClass();
        handleSelect(query, clazz);
        handleWhere(query, object);
        return query;
    }

    private static void handleWhere(QueryWrapper query, Object object) {
        Class<?> clazz = object.getClass();
        Field[] fields = clazz.getDeclaredFields();
        MetaObject metaObject = EntityMetaObject.forObject(object, reflectorFactory);
        for (Field field : fields) {
            Where where = field.getAnnotation(Where.class);
            if (where == null) {
                continue;
            }
            String column = where.column();
            if (!StringUtil.hasText(column)) {
                column = StringUtil.camelToUnderline(field.getName());
            }
            Object value = handleValue(where.operator(), metaObject.getValue(field.getName()));
            QueryColumn queryColumn = new QueryColumn(column);
            QueryCondition condition = new QueryCondition();
            condition.setColumn(queryColumn);
            condition.setLogic(where.operator().value);
            condition.setValue(value);
            query.and(condition);
        }
    }

    private static Object handleValue(SqlOperator operator, Object value) {
        switch (operator) {
            case LIKE, NOT_LIKE -> value = "%" + value + "%";
            case LIKE_LEFT, NOT_LIKE_LEFT -> value = value + "%";
            case LIKE_RIGHT, NOT_LIKE_RIGHT -> value = "%" + value;
            case IS_NULL, IS_NOT_NULL -> value = null;
            case null, default -> {
            }
        }
        return value;
    }

    private static void handleSelect(QueryWrapper query, Class<?> clazz) {
        List<SelectTable> selectTableList = findSelectTables(clazz);
        if (selectTableList.isEmpty()) {
            return;
        }
        QueryTable[] queryTables = new QueryTable[selectTableList.size()];
        for (int i = 0; i < selectTableList.size(); i++) {
            SelectTable selectTable = selectTableList.get(i);
            QueryTable queryTable = new QueryTable(selectTable.name(), selectTable.alias());
            QueryColumn[] queryColumns = Arrays.stream(selectTable.columns()).map(selectColumn -> new QueryColumn(queryTable, selectColumn.name(), selectColumn.alias())).toArray(QueryColumn[]::new);
            query.select(queryColumns);
            queryTables[i] = queryTable;
        }
        query.from(queryTables);
    }

    private static List<SelectTable> findSelectTables(Class<?> clazz) {
        Select annotation = clazz.getAnnotation(Select.class);
        if (annotation == null) {
            SelectTable selectTable = clazz.getAnnotation(SelectTable.class);
            if (selectTable == null) {
                return Collections.emptyList();
            }
            return List.of(selectTable);
        }
        return List.of(annotation.tables());
    }

    public static void main(String[] args) {
        String tableName = "";
        String columns = StringUtil.hasText(tableName) ? tableName + REFERENCE + SqlConsts.ASTERISK : SqlConsts.ASTERISK;
    }

    private static final ReflectorFactory reflectorFactory = new BaseReflectorFactory() {
        @Override
        public Reflector findForClass(Class<?> type) {

            return Reflectors.of(type);
        }
    };
}
