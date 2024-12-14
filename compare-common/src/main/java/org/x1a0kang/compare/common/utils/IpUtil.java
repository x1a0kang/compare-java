package org.x1a0kang.compare.common.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.x1a0kang.compare.common.factory.CustomLoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author dongruijun
 * @version V1.0
 * @date 2020-04-10 21:56:21
 * @project app-data
 * @description
 **/
public class IpUtil {
    private static final Logger logger = CustomLoggerFactory.getLogger(IpUtil.class);
    public static String serverIp = "";
    private static final String UNKNOWN = "unknown";

    static {
        try {
            Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
            List<String> ipList = new ArrayList<>();
            while (netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ia = ni.getInetAddresses();
                while (ia.hasMoreElements()) {
                    InetAddress inetAddress = ia.nextElement();
                    String tempIp = inetAddress.getHostAddress();
                    if (tempIp != null && tempIp.startsWith("1") && !tempIp.startsWith("127")) {
                        ipList.add(tempIp);
                    }
                }
            }
            Collections.sort(ipList);
            logger.info("Server ip:{}", ipList);
            if (ipList.size() > 0) {
                serverIp = ipList.get(0);
            }
        } catch (Exception ex) {
            logger.error("解析服务器ip异常", ex);
        }
    }

    /**
     * 通过HttpServletRequest获取客户端真实ip
     * X-Forwarded-For 记录一个请求从客户端出发到目标服务器过程中经历的代理
     * 格式为X-Forwarded-For:client1,proxy1,proxy2
     * 第一个ip为客户端真实ip，后面的为经过的代理服务器的ip。
     */
    public static String clientIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");

        //Proxy-Client-IP：apache 服务代理
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }

        //WL-Proxy-Client-IP：weblogic 服务代理
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        //X-Real-IP：nginx服务代理
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }

        //HTTP_CLIENT_IP：有些代理服务器
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        //有些网络通过多层代理，那么获取到的ip就会有多个，一般都是通过逗号（,）分割开来，并且第一个ip为客户端的真实IP
        if (ip != null && ip.length() != 0) {
            ip = ip.split(",")[0];
        }

        //还是不能获取到，最后再通过request.getRemoteAddr()获取
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 获取本机ip
     *
     * @return
     */
    public static String getLocalIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            logger.error("获取本机ip失败", e);
        }
        return StringUtils.EMPTY;
    }
}