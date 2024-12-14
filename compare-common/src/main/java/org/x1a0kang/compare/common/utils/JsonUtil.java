package org.x1a0kang.compare.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import static com.fasterxml.jackson.core.JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN;
import static com.fasterxml.jackson.databind.DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS;

/**
 * @author dongruijun
 * @version V1.0
 * @date 2020-04-13 21:07:15
 * @project app-data
 * @description Jackson json Utils
 **/
public class JsonUtil {
    private static final Logger logger = CustomLoggerFactory.getLogger(JsonUtil.class);
    /**
     * ObjectMapper 线程安全
     * ObjectMapper支持从byte[]、File、InputStream、字符串等数据的JSON反序列化。
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        //大小写脱敏
        MAPPER.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        //忽略不认识的属性
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //序列化BigDecimal 禁用科学计数法
        MAPPER.enable(WRITE_BIGDECIMAL_AS_PLAIN);
        MAPPER.enable(USE_BIG_DECIMAL_FOR_FLOATS);
        MAPPER.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        MAPPER.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public static <T> T fromJson(String jsonData, Class<T> clazz) {
        try {
            if (StringUtils.isEmpty(jsonData)) {
                return null;
            }
            return MAPPER.readValue(jsonData, clazz);
        } catch (Exception ex) {
            logger.error("Json 解析错误， str={}，ex =", jsonData, ex);
            return null;
        }
    }

    public static <T> T fromBytes(byte[] jsonData, Class<T> clazz) {
        try {
            return MAPPER.readValue(jsonData, clazz);
        } catch (Exception ex) {
            logger.error("Json 解析错误， str={}，ex =", jsonData, ex);
            return null;
        }
    }

    /**
     * 用于反序列化复杂对象，比如Map
     */
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            if (StringUtils.isEmpty(json)) {
                return null;
            }
            return MAPPER.readValue(json, typeReference);
        } catch (Exception ex) {
            logger.error("Json 解析错误， str={}，ex =", json, ex);
            return null;
        }
    }

    /**
     * 用于反序列化复杂对象，比如Map
     */
    public static <T> T fromJson(InputStream inputStream, TypeReference<T> typeReference) {
        try {
            return MAPPER.readValue(inputStream, typeReference);
        } catch (Exception ex) {
            logger.error("Json 解析错误，ex =", ex);
            return null;
        }
    }

    public static String toJson(Object object) {
        try {
            /**
             * ObjectMapper是JSON操作的核心，Jackson的所有JSON操作都是在ObjectMapper中实现。
             * ObjectMapper有多个JSON序列化的方法，可以把JSON字符串保存File、OutputStream等不同的介质中。
             * writeValue(File arg0, Object arg1)把arg1转成json序列，并保存到arg0文件中。
             * writeValue(OutputStream arg0, Object arg1)把arg1转成json序列，并保存到arg0输出流中。
             * writeValueAsBytes(Object arg0)把arg0转成json序列，并把结果输出成字节数组。
             * writeValueAsString(Object arg0)把arg0转成json序列，并把结果输出成字符串。
             */
            return MAPPER.writeValueAsString(object);
        } catch (Exception ex) {
            logger.error("序列化失败，ex = ", ex);
            return "";
        }
    }

    public static byte[] toBytes(Object object) {
        try {
            return MAPPER.writeValueAsBytes(object);
        } catch (Exception ex) {
            return new byte[0];
        }
    }

    public static LinkedHashMap<String, Object> convertObjectToMap(Object object) {
        return JsonUtil.fromJson(JsonUtil.toJson(object), new TypeReference<LinkedHashMap<String, Object>>() {
        });
    }
}
