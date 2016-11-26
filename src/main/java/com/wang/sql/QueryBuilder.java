package com.wang.sql;

import com.wang.sql.condition.Condition;

import java.util.*;

import static com.wang.sql.Field.field;

/**
 * Created by paopao on 16/11/26.
 */
public class QueryBuilder {

    private Set<Field> columns = new HashSet<>();
    private Set<Field> tables = new HashSet<>();
    private Set<Condition> conditions = new HashSet<>();
    private Long limit;
    private Long offset;
    private Map<Sort, Field> orderBy;
    private Set<Field> groupBy;

    private QueryBuilder() {

    }

    public static QueryBuilder select(Field... columns) {
        QueryBuilder select = new QueryBuilder();
        select.columns.addAll(Arrays.asList(columns));
        return select;
    }

    public static QueryBuilder select(String... columns) {
        QueryBuilder select = new QueryBuilder();
        for (String column : columns) {
            select.columns.add(field(column));
        }
        return select;
    }

    public QueryBuilder from(Field... tables) {
        this.tables.addAll(Arrays.asList(tables));
        return this;
    }

    public QueryBuilder from(String... tables) {
        for (String table : tables) {
            this.tables.add(field(table));
        }
        return this;
    }

    public QueryBuilder where(Condition... conditions) {
        this.conditions.addAll(Arrays.asList(conditions));
        return this;
    }

    public QueryBuilder limit(long limit) {
        this.offset = 0L;
        this.limit = limit;
        return this;
    }

    public QueryBuilder limit(long offset, long limit) {
        this.offset = offset;
        this.limit = limit;
        return this;
    }

    public QueryBuilder groupBy(Field field) {
        if (groupBy == null) {
            groupBy = new HashSet<>();
        }
        groupBy.add(field);
        return this;
    }

    public QueryBuilder groupBy(String field) {
        return groupBy(field(field));
    }

    public QueryBuilder orderBy(Field field, Sort sort) {
        if (orderBy == null) {
            orderBy = new HashMap<>();
        }
        orderBy.put(sort, field);
        return this;
    }

    public QueryBuilder orderBy(String field, Sort sort) {
        return orderBy(field(field), sort);
    }

    public String getSQL() {
        if (columns.size() == 0) {
            throw new IllegalException("");
        }
        if (tables.size() == 0) {
            throw new IllegalException("");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        for (Field column : columns) {
            sb.append(column.toString());
        }
        sb.append(" FROM ");
        for (Field table : tables) {
            sb.append(table.toString());
        }
        sb.append(" WHERE (");
        for (Condition condition : conditions) {
            sb.append(condition.toString());
        }
        sb.append(")");
        if (groupBy != null) {
            sb.append(" GROUP BY ");
            for (Field field : groupBy) {
                sb.append(field.name);
            }
        }
        if (orderBy != null) {
            for (Map.Entry<Sort, Field> order : orderBy.entrySet()) {
                sb.append(" ORDER BY ");
                sb.append(order.getValue().name);
                sb.append(" ");
                sb.append(order.getKey().name());
            }
        }
        if (limit != null) {
            sb.append(String.format(" limit (%d,%d) ", offset, limit));
        }
        return sb.toString();
    }


    public static void main(String[] args) {
        String result = select("*")
                .from("merchant")
                .where(field("sn").is("123456").and(field("id").is("123456")))
                .orderBy("ctime", Sort.DESC)
                .limit(5, 10).getSQL();

        String result2 = select(field("name"))
                .from("merchant")
                .where(field("sn").is("123456").or(field("name").is("123")))
                .groupBy("name")
                .orderBy("ctime", Sort.DESC)
                .limit(5, 10).getSQL();
        System.out.println(result);
        System.out.println(result2);
    }
}
