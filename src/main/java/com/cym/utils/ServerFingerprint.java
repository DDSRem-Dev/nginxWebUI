package com.cym.utils;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ServerFingerprint {
    
    private static final String FINGERPRINT;
    
    static {
        try {
            FINGERPRINT = generateFingerprint();
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate server fingerprint", e);
        }
    }
    
    public static String getFingerprint() {
        return FINGERPRINT;
    }
    
    private static String generateFingerprint() throws Exception {
        StringBuilder sb = new StringBuilder();
        

        // 2. 网络接口 MAC 地址（取第一个非回环网卡）
        NetworkInterface ni = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
        if (ni != null) {
            byte[] mac = ni.getHardwareAddress();
            if (mac != null) {
                for (byte b : mac) {
                    sb.append(String.format("%02X", b));
                }
            }
        }
        
        // 3. 操作系统信息
        sb.append(System.getProperty("os.name"));
        sb.append(System.getProperty("os.version"));
        sb.append(System.getProperty("os.arch"));
        
        // 4. Java 运行时信息
        sb.append(System.getProperty("java.version"));
        sb.append(System.getProperty("java.vendor"));
        sb.append(System.getProperty("java.home"));
        
        
        // 生成 SHA-256 哈希
        return sha256(sb.toString());
    }
    
    private static String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(input.getBytes());
        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            hex.append(String.format("%02x", b));
        }
        return hex.toString().substring(0, 16); // 取前16位作为标识
    }
    
    public static void main(String[] args) {
        System.out.println("服务器唯一标识: " + getFingerprint());
    }
}