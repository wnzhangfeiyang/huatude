package com.mxgraph.online;

import com.alibaba.fastjson.JSONObject;
import com.mxgraph.dao.UserInfoDao;
import com.mxgraph.entity.UserInfo;
import com.mxgraph.enums.StatusEnum;
import com.mxgraph.response.R;
import com.mxgraph.util.H2JdbcCreateUtil;
import com.mxgraph.util.WxAuthUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.UUID;

public class WxServlet extends HttpServlet {

    private static Logger log = LoggerFactory.getLogger(WxServlet.class);
    private static String FETCH_USER = "https://api.weixin.qq.com/sns/userinfo";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("application/json; charset=utf-8");
        PrintWriter writer = resp.getWriter();
        String code = req.getParameter("code");
        if(StringUtils.isBlank(code)){
            throw new ServletException("invaild code ......");
        }
        String url="https://api.weixin.qq.com/sns/oauth2/access_token?" +
                "appid=" + WxAuthUtil.wxAppId+
                "&secret=" +WxAuthUtil.wxSecret+
                "&code=" +code+
                "&grant_type=authorization_code";
        JSONObject jsonObject = WxAuthUtil.doGetHttp(url);
        if(Objects.nonNull(jsonObject)){
            log.info("fetch accessToken and openId is success:{}", JSONObject.toJSONString(jsonObject));
            String accessToken = JSONObject.toJSONString(jsonObject.get("access_token"));
            String openId = JSONObject.toJSONString(jsonObject.get("openid"));
            log.info("accessToken:{}, openid:{}", accessToken, openId);
            // 接下来通过这两个参数去获取用户信息
            String user_url = FETCH_USER + "?access_token=" + accessToken + "&openid" + openId;
            jsonObject= WxAuthUtil.doGetHttp(user_url);
            if(Objects.nonNull(jsonObject)){
                String unionid = JSONObject.toJSONString(jsonObject.get("unionid"));
                String nickname =JSONObject.toJSONString(jsonObject.get("nickname"));
                String sex = JSONObject.toJSONString(jsonObject.get("sex"));
                String province = JSONObject.toJSONString(jsonObject.get("province"));
                String city = JSONObject.toJSONString(jsonObject.get("city"));
                String country = JSONObject.toJSONString(jsonObject.get("country"));
                String headimgurl = JSONObject.toJSONString(jsonObject.get("headimgurl"));
                //通过unionId 去查库，如果存在就更新一下，不存在在进行插库 通过jdbi 去连接数据库(代码很简洁)，并且不需要手动关连接
                UserInfoDao userInfoDao = H2JdbcCreateUtil.initH2Db();
                UserInfo userInfo = userInfoDao.findUserInfoByUnionId(unionid);
                if(Objects.nonNull(userInfo)){
                    //此处开始更新userInfo信息，并且返回对象
                    writer.println(R.data(userInfo));
                } else {
                    log.info("start add userInfo");
                    long now = System.currentTimeMillis();
                    String uuid = get16UUID();
                    int insert = userInfoDao.addUserInfo(nickname, unionid, uuid, StatusEnum.N.getCode(), now, now, openId, sex, province, city, country, headimgurl);
                    if (insert > 0) {
                        log.info("the user is add success, userId:{}, openId:{}, unionId:{}", uuid, openId, unionid);
                    }
                    //添加完成后返回对象
                    UserInfo newUserInfo = userInfoDao.findUserInfoByUnionId(unionid);
                    //组装成json数据，通过R.data来组装
                    writer.println(R.data(newUserInfo));
                }
            }
        }
    }

    public static String get16UUID(){
        UUID id=UUID.randomUUID();
        String[] idd=id.toString().split("-");
        return idd[0]+idd[1]+idd[2];
    }

    public static void main(String[] args) {
        R<Boolean> data = R.data(Boolean.TRUE);
        System.out.println(data);
    }


}
