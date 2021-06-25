package com.mxgraph.online;

import com.mxgraph.util.WxAuthUtil;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

public class LoginServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String baseUrl = "https://draw.huatu.app/weixin";
        String url = "https://open.weixin.qq.com/connect/qrconnect?" +
                "appid=" + WxAuthUtil.wxAppId +
                "&redirect_uri=" + URLEncoder.encode(baseUrl, "UTF-8")+
                "&response_type=code" +
                "&scope=snsapi_userinfo" +
                "&state=STATE#wechat_redirect";
        resp.sendRedirect(url);
    }
}
