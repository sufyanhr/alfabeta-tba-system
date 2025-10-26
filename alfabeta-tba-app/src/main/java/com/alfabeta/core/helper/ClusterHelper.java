package com.alfabeta.core.helper;

public class ClusterHelper {
    public static String nodeId() {
        return java.net.InetAddress.getLoopbackAddress().getHostName();
    }
}
