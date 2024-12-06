package org.dfq.webserver.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.dfq.webserver.constant.WebsocketConstant.ALLOWED_ORIGINS;

/**
 * @author: daVinci
 * @description: 解决跨域问题的过滤器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class CorsFilter implements Filter {

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        // 跨域处理
        if (req instanceof HttpServletRequest request && res instanceof HttpServletResponse response) {

            // 跨域请求，判断是否是允许的域名
            String origin = request.getHeader("Origin");
            response.setHeader("Access-Control-Allow-Origin", ALLOWED_ORIGINS.contains(origin) ? origin : "*");
            response.setHeader("Vary", "Origin");

            // 预检请求
            response.setHeader("Access-Control-Max-Age", "3600");

            // 允许cookie
            response.setHeader("Access-Control-Allow-Credentials", "true");

            // 允许的请求方法
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");

            // 允许的请求头
            response.setHeader("Access-Control-Allow-Headers",
                    "Origin, X-Requested-With, Content-Type, Accept, " + "X-CSRF-TOKEN");
        }

        // 放行
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {}

}
