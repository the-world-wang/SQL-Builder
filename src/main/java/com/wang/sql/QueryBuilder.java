package com.wang.sql;

import com.wang.sql.condition.Condition;
import com.wang.sql.field.Field;
import com.wang.sql.field.Sort;

import java.util.*;

import static com.wang.sql.field.Field.field;

/**
 * Created by paopao on 16/11/26.
 */
public class QueryBuilder {

    private Set<Field> columns = new HashSet<>();
    private Set<Field> tables = new HashSet<>();
    private List<Object> args = new ArrayList<>();
    private Set<JoinBuilder> joins;
    private Set<Condition> where;
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

    public JoinBuilder join(Field field) {
        if (joins == null) {
            joins = new HashSet<>();
        }
        JoinBuilder join = new JoinBuilder(this, field);
        joins.add(join);
        return join;
    }

    public JoinBuilder join(String field) {
        return join(field(field));
    }

    public QueryBuilder where(Condition... conditions) {
        if (where == null) {
            where = new HashSet<>();
        }
        where.addAll(Arrays.asList(conditions));
        for (Condition condition : conditions) {
            args.addAll(condition.getArgs());
        }
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
        args.add(offset);
        args.add(limit);
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
        sb.append(JoinerUtils.commaJoiner.join(columns));
        sb.append(" FROM ");
        sb.append(JoinerUtils.commaJoiner.join(tables));

        if (joins != null) {
            for (JoinBuilder join : joins) {
                sb.append(" JOIN ");
                sb.append(join);
                sb.append("\n");
            }
        }
        if (where != null) {
            sb.append(" WHERE (");
            for (Condition condition : where) {
                // TODO and
                sb.append(condition.getSQL());
            }
            sb.append(")\n");
        }
        if (groupBy != null) {
            sb.append(" GROUP BY ");
            for (Field field : groupBy) {
                sb.append(field.getName());
            }
        }
        if (orderBy != null) {
            for (Map.Entry<Sort, Field> order : orderBy.entrySet()) {
                sb.append(" ORDER BY ");
                sb.append(order.getValue().getName());
                sb.append(" ");
                sb.append(order.getKey().name());
            }
        }
        if (limit != null) {
            sb.append(" limit ?,?");
        }
        return sb.toString();
    }

    public Object[] getArgs() {
        return args.toArray();
    }
}
