package top.xiaowoa.mybatisflex.extension.builder;

import com.mybatisflex.core.constant.SqlConsts;
import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.query.QueryTable;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.util.StringUtil;
import top.xiaowoa.mybatisflex.extension.annotations.Select;
import top.xiaowoa.mybatisflex.extension.annotations.query.SelectTable;

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

        return query;
    }

    private static void handleSelect(QueryWrapper query, Class<?> clazz) {
        List<SelectTable> selectTableList = findSelectTables(clazz);
        if (selectTableList.isEmpty()) {
            return;
        }
        for (SelectTable selectTable : selectTableList) {
            QueryTable queryTable = new QueryTable(selectTable.name(), selectTable.alias());
        }

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
