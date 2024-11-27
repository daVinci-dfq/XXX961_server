package org.dfq.webserver.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    // 签名密钥
    private static final String SECRET = "hello JWT *%$#$&";

    public static String generateToken(Map<String, String> payload) {
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.HOUR, 24);  // 24小时

            JWTCreator.Builder builder = JWT.create();
            payload.forEach(builder::withClaim);
            String token = builder.withIssuedAt(new Date()).withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));
            return token;
        } catch (Exception e) {
            e.printStackTrace();  // 打印异常信息到控制台或日志文件
            throw new RuntimeException("Error generating token", e);  // 或者抛出自定义异常
        }
    }

    public static DecodedJWT decodeToken(String token) {
        try {
            JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            return jwtVerifier.verify(token);
        } catch (Exception e) {
            e.printStackTrace();  // 打印异常信息到控制台或日志文件
            throw new RuntimeException("Error decoding token", e);  // 或者抛出自定义异常
        }
    }

}


