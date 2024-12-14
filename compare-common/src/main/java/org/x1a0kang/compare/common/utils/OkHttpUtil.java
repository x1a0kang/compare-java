package org.x1a0kang.compare.common.utils;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


class ReadTimeoutInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        final Integer newReadTimeoutMillis = request.tag(Integer.class);
        if (newReadTimeoutMillis != null) {
            return chain.withReadTimeout(newReadTimeoutMillis, TimeUnit.MILLISECONDS)
                    .proceed(request);
        }
        return chain.proceed(request);

    }
}

/**
 * 基于okhttp3的http访问工具
 *
 * @author qinkai
 * @author cuixin
 * @date 2020/11/25
 */
public class OkHttpUtil {
    private static final Logger logger = CustomLoggerFactory.getLogger(OkHttpUtil.class);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    /**
     * 超时时间等参数设置
     */
    private static final int CONNECT_TIMEOUT = 1;
    private static final int READ_TIMEOUT = 1;
    private static final int WRITE_TIMEOUT = 1;
    /**
     * 连接池大小
     */
    private static final int MAX_CONNECTIONS = 100;

    /**
     * HTTP实例
     */
    private static OkHttpClient client;

    static {
        initHttpClient();
    }

    /**
     * 初始化HTTP客户端
     */
    private static void initHttpClient() {
        // 进行数据初始化
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                // 设置读取超时时间
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                // 设置写的超时时间
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                // 设置连接超时时间
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                // 自定义读超时时间拦截器
                .addInterceptor(new ReadTimeoutInterceptor())
                // 使用连接池
                .connectionPool(pool());
        client = builder.build();
    }

    /**
     * 使用连接池，复用HTTP/HTTPS连接
     */
    private static ConnectionPool pool() {
        return new ConnectionPool(MAX_CONNECTIONS, 5, TimeUnit.MINUTES);
    }

    public static String synchronousPost(String url) {
        Request request = buildPostRequest(url, null, null);
        return synchronousRequest(request);
    }

    /**
     * 同步发送POST请求，会一直阻塞到结果返回
     *
     * @param json 请求体使用json格式
     * @return String 出错返回null
     */
    public static String synchronousPost(String url, String json) {
        Request request = buildPostRequest(url, json, null);
        return synchronousRequest(request);
    }

    /**
     * 同步发送POST请求，会一直阻塞到结果返回
     *
     * @param json 请求体使用json格式
     * @return String 出错返回null
     */
    public static String synchronousPost(String url, String json, Integer readTimeoutMillis) {
        Request request = buildPostRequest(url, json, null, readTimeoutMillis);
        return synchronousRequest(request);
    }

    /**
     * 异步发送POST请求，如果结果可读则回调callback
     *
     * @param json 请求体使用json格式
     */
    public static void asynchronousPost(String url, String json, Callback callback) {
        Request request = buildPostRequest(url, json, null);
        client.newCall(request).enqueue(callback);
    }


    /**
     * 同步发送POST请求，会一直阻塞到结果返回
     *
     * @param json    请求体使用json格式，添加请求头
     * @param headers 请求头
     * @return String 出错返回null
     */
    public static String synchronousPost(String url, String json, Map<String, String> headers) {
        Request request = buildPostRequest(url, json, headers);
        return synchronousRequest(request);
    }

    /**
     * 同步发送POST请求，会一直阻塞到结果返回
     *
     * @param json    请求体使用json格式，添加请求头
     * @param headers 请求头
     * @return String 出错返回null
     */
    public static String synchronousPost(String url, String json, Map<String, String> headers, Integer readTimeoutMillis) {
        Request request = buildPostRequest(url, json, headers, readTimeoutMillis);
        return synchronousRequest(request);
    }

    /**
     * 异步发送POST请求，如果结果可读则回调callback
     *
     * @param json    请求体使用json格式，添加请求头
     * @param headers 请求头
     */
    public static void asynchronousPost(String url, String json, Map<String, String> headers, Callback callback) {
        Request request = buildPostRequest(url, json, headers);
        client.newCall(request).enqueue(callback);
    }

    /**
     * 同步发送get请求，会一直阻塞到结果返回
     *
     * @return String 出错返回null
     */
    public static String synchronousGet(String url) {
        Request request = buildGetRequest(url, null);
        return synchronousRequest(request);
    }

    /**
     * 同步发送get请求，会一直阻塞到结果返回
     *
     * @return String 出错返回null
     */
    public static String synchronousGet(String url, Map<String, String> params, Map<String, String> headers) {
        Request request = buildGetRequest(url, params, headers);
        return synchronousRequest(request);
    }

    public static String synchronousGet(String url, Map<String, String> params, Map<String, String> headers, Integer readTimeoutMillis) {
        Request request = buildGetRequest(url, params, headers, readTimeoutMillis);
        return synchronousRequest(request);
    }

    /**
     * 同步发送get请求，会一直阻塞到结果返回
     */
    public static void asynchronousGet(String url, Callback callback) {
        Request request = buildGetRequest(url, null);
        client.newCall(request).enqueue(callback);
    }

    /**
     * 同步发送get请求，会一直阻塞到结果返回
     *
     * @return String 出错返回null
     */
    public static String synchronousGet(String url, Map<String, String> params) {
        Request request = buildGetRequest(url, params);
        return synchronousRequest(request);
    }

    /**
     * 异步发送POST请求，如果结果可读则回调callback
     *
     * @param params 查询字符串
     */
    public static void asynchronousGet(String url, Map<String, String> params, Callback callback) {
        Request request = buildGetRequest(url, params);
        client.newCall(request).enqueue(callback);
    }

    private static String synchronousRequest(Request request) {
        String url = request.url().toString();
        long startTime = System.currentTimeMillis();
        try (Response response = client.newCall(request).execute()) {
            long endTime = System.currentTimeMillis();
            logger.info("调用接口{}, request={}, 耗时{}ms", request.url(), JsonUtil.toJson(request), endTime - startTime);
            if (response.isSuccessful() && null != response.body()) {
                return response.body().string();
            } else {
                logger.error("http请求失败，url = {}，request={}，response={}", url, request, response);
                MDC.put("errorCode", String.valueOf(response.code()));
                MDC.put("errorMsg", url + " " + HttpStatus.valueOf(response.code()).getReasonPhrase());
                return null;
            }
        } catch (IOException ex) {
            logger.error("http请求超时，url = {}，request={}，error={}", url, request, ex.getMessage());
            MDC.put("errorCode", String.valueOf(HttpStatus.REQUEST_TIMEOUT.value()));
            MDC.put("errorMsg", String.format("url = %s，exception = %s，message = %s", url, ex.getClass().getSimpleName(), ex.getMessage()));
            return null;
        } catch (Exception ex) {
            logger.error("http请求失败，url = {}，request={}，error={}", url, request, ex.getMessage());
            MDC.put("errorCode", "-1");
            MDC.put("errorMsg", url + " " + ex.getMessage());
            return null;
        }
    }

    private static Request buildPostRequest(String url, String json, Map<String, String> headers, Integer readTimeoutMillis) {
        Request.Builder builder = new Request.Builder();
        if (json != null && !("".equals(json))) {
            RequestBody body = RequestBody.create(JSON, json);
            builder.post(body);
        }
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        return builder.url(url).tag(Integer.class, readTimeoutMillis).build();
    }


    private static Request buildPostRequest(String url, String json, Map<String, String> headers) {
        return buildPostRequest(url, json, headers, null);
    }


    private static Request buildGetRequest(String url, Map<String, String> params, Map<String, String> headers, Integer readTimeoutMillis) {
        Request.Builder builder = new Request.Builder();
        HttpUrl httpUrl = HttpUrl.parse(url);
        if (Objects.isNull(httpUrl)) {
            throw new RuntimeException("url不规范 url = " + url);
        }
        HttpUrl.Builder httpBuilder = httpUrl.newBuilder();
        // 如果有查询字符串
        if (params != null) {
            params.forEach(httpBuilder::addQueryParameter);
        }
        // 添加请求头
        if (headers != null) {
            headers.forEach(builder::addHeader);
        }
        return builder.url(httpBuilder.build()).tag(Integer.class, readTimeoutMillis).build();
    }

    private static Request buildGetRequest(String url, Map<String, String> params, Map<String, String> headers) {
        return buildGetRequest(url, params, headers, null);
    }


    private static Request buildGetRequest(String url, Map<String, String> params) {
        return buildGetRequest(url, params, null, null);
    }
}