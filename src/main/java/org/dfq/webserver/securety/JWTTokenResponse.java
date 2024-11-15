package org.dfq.webserver.securety;

import lombok.Data;

@Data
public class JWTTokenResponse {
    private String token;

    public JWTTokenResponse(String token) {
        this.token = token;
    }

    // 省略getter和setter方法
}