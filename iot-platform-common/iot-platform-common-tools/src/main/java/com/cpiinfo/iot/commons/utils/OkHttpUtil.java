package com.cpiinfo.iot.commons.utils;

import okhttp3.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Map;

public class OkHttpUtil {

    // MEDIA_TYPE <==> Content-Type
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    // MEDIA_TYPE_TEXT post请求不是application/x-www-form-urlencoded的，全部直接返回，不作处理，即不会解析表单数据来放到request parameter map中。
    // 所以通过request.getParameter(name)是获取不到的。只能使用最原始的方式，读取输入流来获取。
    private static final MediaType MEDIA_TYPE_TEXT = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    private static final MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");

    private static OkHttpClient client = new OkHttpClient();

    private static RestTemplate restTemplate = new RestTemplate();

    static {
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        org.springframework.http.MediaType t = org.springframework.http.MediaType.parseMediaType("application/json;charset=UTF-8");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(t);
        headers.add("Accept", org.springframework.http.MediaType.APPLICATION_JSON.toString());
    }

    /**
     * 为啥okhttp会报400???
     * @param url 。。。。。。。。。。。。。。。
     * @return
     */
    public static Object sendGetPic(String url) {
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * get方式请求
     *
     * @param url
     * @return String
     */
    public static String sendGetUrl(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            assert response.body() != null;
            String result = response.body().string();
            response.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException("error while get url " + url, e);
        }
    }

    /**
     * get方式请求
     *
     * @param url
     * @return 根据内容类型，返回字符串或二进制流
     */
    public static Object sendGetUrlStream(String url) {
        Request request = new Request.Builder()
                .url(url).addHeader("User-Agent", "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:0.9.4)")
                .build();
        Response response;
        try {
            response = client.newCall(request).execute();
            assert response.body() != null;
            if (MEDIA_TYPE_STREAM.equals(response.body().contentType())) {
                return response.body().byteStream();
            } else {
                String result = response.body().string();
                response.close();
                return result;
            }
        } catch (IOException e) {
            throw new RuntimeException("error while get url " + url, e);
        }
    }

    /**
     * post+json方式请求
     *
     * @param url
     * @param json
     * @return String
     */
    public static String sendPostJson(String url, String json) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response;
        try {
            String result = null;
            response = client.newCall(request).execute();
            if (response.body() != null)
                result = response.body().string();
            response.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException("error while post url " + url + ", json:" + json, e);
        }
    }

    /**
     * post+map方式请求
     *
     * @param url
     * @param params
     * @return String
     */
    public static String sendPostMap(String url, Map<String, String> params) {
        StringBuilder content = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> entry = iterator.next();
            content.append(entry.getKey()).append("=").append(entry.getValue());
            if (iterator.hasNext()) {
                content.append("&");
            }
        }

        RequestBody requestBody = RequestBody.create(MEDIA_TYPE_TEXT, content.toString());
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response response;
        try {
            String result = null;
            response = client.newCall(request).execute();
            if (response.body() != null)
                result = response.body().string();
            response.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException("error while post url " + url + ",content:" + content, e);
        }
    }

}