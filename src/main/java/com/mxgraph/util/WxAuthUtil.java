package com.mxgraph.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class WxAuthUtil {
    private static Logger log = LoggerFactory.getLogger(WxAuthUtil.class);
    public static final String wxAppId = "";
    public static final String wxSecret = "";

    public static JSONObject doGetHttp(String url){
        JSONObject jsonObject = null;
        CloseableHttpClient build = HttpClientBuilder.create().build();
        HttpUriRequest request = new HttpGet(url);
        try (CloseableHttpResponse execute = build.execute(request)) {
            if(execute.getStatusLine().getStatusCode() >= 200 && execute.getStatusLine().getStatusCode() < 400){
                HttpEntity entity = execute.getEntity();
                String result = EntityUtils.toString(entity, "UTF-8");
                jsonObject = JSONObject.parseObject(result);
            }
        } catch (IOException e) {
            log.error("io error, because of:{}", e.getMessage());
        }
        return jsonObject;
    }
}
