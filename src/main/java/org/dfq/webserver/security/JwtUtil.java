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

    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String generateToken(Map<String,String> payload){
        // 指定token过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);  // 24小时

        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach(builder::withClaim);
        // 指定签发时间、过期时间 和 签名算法，并返回token
        String token = builder.withIssuedAt(new Date()).withExpiresAt(calendar.getTime()).sign(Algorithm.HMAC256(SECRET));
        return token;
    }


    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token类
     */
    public static DecodedJWT decodeToken(String token){
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }
}


