package com.mxgraph.dao;

import com.mxgraph.entity.UserInfo;
import com.mxgraph.mapper.UserInfoMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

@RegisterMapper(value = UserInfoMapper.class)
public interface UserInfoDao {
    @SqlQuery("select * from user_info where id = :id")
    UserInfo findUserInfoById(@Bind("id") Long id);

    @SqlQuery("select * from user_info where union_id = :union_id")
    UserInfo findUserInfoByUnionId(@Bind("union_id") String union_id);

    @SqlUpdate("insert into user_info(user_name, union_id, user_id, status, create_date, lastmod_date, open_id, sex, province, city, country, head_img_url) " +
            "values (:user_name, :union_id, :user_id, :status ,:create_date ,:lastmod_date, :open_id, :sex, :province, :city, country, :head_img_url)")
    int addUserInfo(@Bind("user_name") String user_name, @Bind("union_id") String union_id,
                    @Bind("user_id") String user_id, @Bind("status") int status,
                    @Bind("create_date") long create_date, @Bind("lastmod_date") long lastmod_date,
                    @Bind("open_id") String openId, @Bind("sex") String sex,
                    @Bind("province") String province, @Bind("city") String city,
                    @Bind("country") String country, @Bind("head_img_url") String headimgurl);
}
