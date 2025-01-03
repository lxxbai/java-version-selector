package io.github.lxxbai.jvs.common.util;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * Http 工具类
 *
 * @author 王大锤
 */
@Slf4j
public class HttpUtil {

    private static final HttpClient CLIENT = HttpClient.newHttpClient();


    public static <T> T get(String url, Class<T> clazz) {
        try {
            String responseBody = sendGetRequest(url);
            return ObjectMapperUtil.toObj(responseBody, clazz);
        } catch (Exception e) {
            log.error("HttpUtil.get error", e);
        }
        return null;
    }

    public static <T> T get(String url, TypeReference<T> typeRef) {
        try {
            String responseBody = sendGetRequest(url);
            return ObjectMapperUtil.toObj(responseBody, typeRef);
        } catch (Exception e) {
            log.error("HttpUtil.get error", e);
        }
        return null;
    }

    /**
     * 发送 GET 请求
     *
     * @param url 请求 URL
     * @return 响应体字符串
     * @throws Exception 如果发生错误
     */
    public static String sendGetRequest(String url) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .GET()
                .build();
        return sendRequest(request);
    }

    /**
     * 发送 POST 请求
     *
     * @param url     请求 URL
     * @param body    请求体
     * @param headers 请求头
     * @return 响应体字符串
     * @throws Exception 如果发生错误
     */
    public static String sendPostRequest(String url, String body, Map<String, String> headers) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(url))
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .headers(headers.entrySet().stream()
                        .map(entry -> entry.getKey() + ": " + entry.getValue())
                        .toArray(String[]::new))
                .build();
        return sendRequest(request);
    }


    /**
     * 发送 HTTP 请求并返回响应体
     *
     * @param request HTTP 请求
     * @return 响应体字符串
     * @throws Exception 如果发生错误
     */
    private static String sendRequest(HttpRequest request) throws Exception {
        HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("HTTP Request Failed with status: " + response.statusCode());
        }
        return response.body();
    }
}
