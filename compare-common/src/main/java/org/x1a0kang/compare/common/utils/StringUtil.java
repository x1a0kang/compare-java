package org.x1a0kang.compare.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * @author dongruijun
 * @version V1.0
 * @date 2020-04-10 18:29:36
 * @project app-data
 * @description
 **/
public class StringUtil {
    private static final int ZERO = 0;
    private static final String EMPTY_STRING = "";
    /**
     * 默认的字符集编码
     * UTF-8 一个汉字占三个字节
     */
    private static final String CHAR_ENCODE = "UTF-8";
    private static final String ISO = "ISO-8859-1";

    /**
     * 判断是否是空字符串 null和"" 都返回 true
     *
     * @param str 判断的字符串
     * @return 是否有效
     */
    public static boolean isNullOrEmpty(String str) {
        return null == str || EMPTY_STRING.equals(str);
    }

    public static boolean isNotNullOrEmpty(String str) {
        return !isNullOrEmpty(str);
    }

    public static boolean isNullOrEmpty(Collection<?> collection) {
        return null == collection || collection.isEmpty();
    }

    public static boolean isNotNullOrEmpty(Collection<?> collection) {
        return !isNullOrEmpty(collection);
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNullOrEmpty(Map map) {

        return null == map || ZERO == map.size();
    }

    @SuppressWarnings("rawtypes")
    public static boolean isNotNullOrEmpty(Map map) {
        return !isNullOrEmpty(map);
    }


    /**
     * UTF-8 一个汉字占三个字节
     *
     * @param str 源字符串
     *            转换成字节数组的字符串
     * @return
     */
    public static byte[] stringToByte(String str, String charEncode) {
        byte[] destObj = null;
        try {
            if (null == str || "".equals(str.trim())) {
                destObj = new byte[0];
                return destObj;
            } else {
                destObj = str.getBytes(charEncode);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destObj;
    }

    /**
     * @param srcObj 源字节数组转换成String的字节数组
     * @return
     */
    public static String byteToString(byte[] srcObj, String charEncode) {
        String destObj = null;
        try {
            destObj = new String(srcObj, charEncode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return destObj.replaceAll("\0", " ");
    }

    /**
     * 字符串的压缩
     *
     * @param str 待压缩的字符串
     * @return 返回压缩后的字符串
     * @throws IOException
     */
    public static String compress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(str.getBytes());
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString(CHAR_ENCODE);
    }

    /**
     * 字符串的解压
     *
     * @param str 对字符串解压
     * @return 返回解压缩后的字符串
     * @throws IOException
     */
    public static String unCompress(String str) throws IOException {
        if (null == str || str.length() <= 0) {
            return str;
        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
        ByteArrayInputStream in = new ByteArrayInputStream(str
                .getBytes(CHAR_ENCODE));
        // 使用默认缓冲区大小创建新的输入流
        GZIPInputStream gzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n = 0;
        while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
            // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
            out.write(buffer, 0, n);
        }
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        return out.toString(CHAR_ENCODE);
    }

    /**
     * 字符串的压缩
     *
     * @param str 待压缩的字符串
     * @return 返回压缩后的字符串
     * 在使用GZIP进行压缩和解压时，如果压缩后的字节数组在传输过程中发生改变就会导致Not in GZIP format异常
     * 如果不是直接传输压缩后的字节数组而是字符串时，在转换为字符串时，一定要使用ISO-8859-1这样的单字节编码
     * 否则在将字符串转换为字节数组时会导致节数组产生变化
     */
    public static String strCompressToStr(String str) {
        if (null == str || str.length() <= 0) {
            return str;
        }
        try {
            // 创建一个新的 byte 数组输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 使用默认缓冲区大小创建新的输出流
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            // 将 b.length 个字节写入此输出流
            gzip.write(str.getBytes(CHAR_ENCODE));
            gzip.close();
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString(ISO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 字符串的解压
     *
     * @param str 对字符串解压
     * @return 返回解压缩后的字符串
     * 同strCompressToStr，创建数组输入流时应该使用和压缩时相同的ISO-8859-1这样的单字节编码
     * 最终将解码字节将缓冲区内容转换为字符串时，可以使用目标字符串的编码格式
     */
    public static String strUnCompressToStr(String str) {
        if (null == str || str.length() <= 0) {
            return str;
        }
        try {
            // 创建一个新的 byte 数组输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
            ByteArrayInputStream in = new ByteArrayInputStream(str
                    .getBytes(ISO));
            // 使用默认缓冲区大小创建新的输入流
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n = 0;
            while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString(CHAR_ENCODE);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static boolean hasChinese(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher matcher = pattern.matcher(str);
        return matcher.find();
    }

    public static int countChinese(String str) {
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);

            // 使用Unicode范围来判断字符是否是中文字符
            if (Character.UnicodeBlock.of(c) == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) {
                count++;
            }
        }

        return count;
    }


    /**
     * 判断字符串text是否符合格式regex
     *
     * @param text  要判断的字符串
     * @param regex 格式
     * @return 是否符合格式
     */
    public static boolean isMatchFormat(String text, String regex) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(text).matches();
    }

    public static String replaceChinesePunc(String s) {
        s = s.replaceAll("：", ":");
        s = s.replaceAll("。", ".");
        s = s.replaceAll("，", ",");
        s = s.replaceAll("；", ";");
        s = s.replaceAll("！", "!");
        s = s.replaceAll("（", "(");
        s = s.replaceAll("）", ")");
        return s;
    }

    /**
     * 将字符串里的字符每一位都转出AscII对应的数字
     *
     * @param input 要判断的字符串
     * @return 返回结果字符串
     */
    public static String stringtoAscII(String input) {

        StringBuilder asciiBuilder = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            asciiBuilder.append((int) c);
        }
        return asciiBuilder.toString();
    }

    /**
     * 判断Object是否为null,及其字符串是否为空串
     *
     * @param obj
     * @return
     */
    public static boolean nullOrStrEmpty(Object obj) {
        if (obj == null) {
            return true;
        }
        return isNullOrEmpty(obj.toString());
    }

    public static boolean notNullOrStrEmpty(Object obj) {
        return !nullOrStrEmpty(obj);
    }


    public static String removeBracket(String s) {
        Pattern pattern = Pattern.compile("\\(.*\\)");
        Matcher matcher = pattern.matcher(s);
        String tempDescription = s;
        if (matcher.find()) {
            tempDescription = matcher.replaceAll("");
        }
        return tempDescription;
    }

    public static int length(String str) {
        return null == str ? 0 : str.length();
    }

    // 移除所有中文字符
    public static String removeChineseCharacters(String input) {
        return input.replaceAll("[\\u4e00-\\u9fa5]", "");
    }
}
