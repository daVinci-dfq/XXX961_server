package org.dfq.webserver.constant;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;

/**
 * @author dfq
 * 常量类
 */

@Slf4j
@RequiredArgsConstructor
public class WebsocketConstant {

    /**
     * 允许跨域的域名
     */
    public static final List<String> ALLOWED_ORIGINS = List.of("http://localhost:9610");


}
