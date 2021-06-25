package com.mxgraph.util;


import com.alibaba.fastjson.JSONObject;
import com.mxgraph.dao.UserInfoDao;
import com.mxgraph.entity.UserInfo;
import org.h2.jdbcx.JdbcConnectionPool;

import org.h2.jdbcx.JdbcDataSource;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class H2JdbcCreateUtil {
    private static Logger log = LoggerFactory.getLogger(H2JdbcCreateUtil.class);
    private static final String JDBC_DRIVER = "org.h2.Driver";
    private static final String DB_URL = "jdbc:h2:/Users/zhangfeiyang/database/h2/data.h2;AUTO_SERVER=TRUE;MODE=MySQL;DATABASE_TO_LOWER=TRUE";

    public static UserInfoDao initH2Db(){
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(DB_URL);
        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(jdbcDataSource);
        DBI dbi = new DBI(jdbcConnectionPool);
        // 为什么要选择onDemand ，当通过open调用时，会发现需要手动关闭其连接，而onDemand不需要手动关闭
        //创建一个新的 sql 对象，它将分别根据需要和可以从该 dbi 实例获取和释放连接。您不应显式关闭此 sql 对象 @param sqlObjectType 带有声明所需行为的注释的接口 @param <SqlObjectType> @return 指定类型的新 sql 对象，带有专用句柄
        UserInfoDao userInfoDao = dbi.onDemand(UserInfoDao.class);
        return userInfoDao;
    }


    public static void main(String[] args) {
        JdbcDataSource jdbcDataSource = new JdbcDataSource();
        jdbcDataSource.setURL(DB_URL);
        JdbcConnectionPool jdbcConnectionPool = JdbcConnectionPool.create(jdbcDataSource);
        DBI dbi = new DBI(jdbcConnectionPool);
        UserInfoDao open = dbi.onDemand(UserInfoDao.class);
        UserInfo userInfoById1 = open.findUserInfoById(1L);
        System.out.println(JSONObject.toJSONString(userInfoById1));
    }
}
