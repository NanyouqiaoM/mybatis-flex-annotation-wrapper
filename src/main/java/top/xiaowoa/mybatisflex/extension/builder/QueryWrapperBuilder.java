package top.xiaowoa.mybatisflex.extension.builder;

import com.mybatisflex.core.constant.SqlConsts;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryCondition;
import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.table.EntityMetaObject;
import com.mybatisflex.core.util.StringUtil;
import org.apache.ibatis.reflection.MetaObject;
import top.xiaowoa.mybatisflex.extension.annotations.Select;
import top.xiaowoa.mybatisflex.extension.annotations.Where;
import top.xiaowoa.mybatisflex.extension.annotations.query.SelectTable;

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
        for (Field field : fields) {
            Where where = field.getAnnotation(Where.class);
            if (where == null) {
                continue;
            }
            String column = where.column();
            query.eq()
        }
        Arrays.stream(clazz.getDeclaredFields()).forEach(field -> {
            Where where = field.getAnnotation(Where.class);
            if (where == null) {

            }
            if (where != null) {
                String column = where.column();
                if (StringUtil.hasText(column)) {
                    column = field.getName();
                }
                MetaObject metaObject = EntityMetaObject.forObject(object, reflectorFactory);

                query.and();
                query.eq(column, field.getName());
            }
        });
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
}
