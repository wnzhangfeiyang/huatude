package com.mxgraph.mapper;

import com.mxgraph.entity.UserInfo;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserInfoMapper implements ResultSetMapper<UserInfo> {
    @Override
    public UserInfo map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        UserInfo userInfo = new UserInfo();
        String open_id = resultSet.getString("open_id");
        String union_id = resultSet.getString("union_id");
        String user_name = resultSet.getString("user_name");
        String user_id = resultSet.getString("user_id");
        Long id = resultSet.getLong("id");
        String phone_num = resultSet.getString("phone_num");
        Integer status = resultSet.getInt("status");
        Long create_date = resultSet.getLong("create_date");
        Long lastmod_date = resultSet.getLong("lastmod_date");
        Integer sex = resultSet.getInt("sex");
        String province = resultSet.getString("province");
        String city = resultSet.getString("city");
        String country = resultSet.getString("country");
        String head_img_url = resultSet.getString("head_img_url");
        userInfo.setOpenId(open_id);
        userInfo.setUnionId(union_id);
        userInfo.setUserName(user_name);
        userInfo.setUserId(user_id);
        userInfo.setId(id);
        userInfo.setPhoneNum(phone_num);
        userInfo.setStatus(status);
        userInfo.setCreateDate(create_date);
        userInfo.setLastModDate(lastmod_date);
        userInfo.setSex(sex);
        userInfo.setProvince(province);
        userInfo.setCity(city);
        userInfo.setCountry(country);
        userInfo.setHeadImgUrl(head_img_url);
        return userInfo;
    }
}
