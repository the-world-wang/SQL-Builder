# SQL-Builder
Thanks to jooq, I was inspired to write this code.

# get started
```java
  select(distinct("name"))
    .from("merchant")
    .where(field("sn").is("123456")
            .and(field("id").is("123456")).and(field("name").like("123"))
            .or(field("name").gte("123"))
            .or(field("name").isNull()))
    .groupBy("name")
    .orderBy("ctime", Sort.DESC)
    .limit(5, 10).getSQL();
```

# support
- orderBy
- groupBy
- equal
- gt(e)
- lt(e)
- like
- isNull
- distinct

# TODO
- [x] join
- [x] clause
- [ ] having
- [ ] count
- [ ] max,avg,min..etc