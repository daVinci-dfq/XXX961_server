package org.dfq.webserver.config;

import cn.hutool.core.util.StrUtil;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * WebSocket配置类
 */

@Slf4j
@Configuration  //配置类 使 Spring 在启动时加载该类中的 Bean 定义，将其纳入应用程序上下文管理
//@EnableWebSocket  // 对原生WebSocket协议的支持。手动管理信息传递，包括手动管理WebSocket连接的生命周期和消息的发送接收等。
@EnableWebSocketMessageBroker   // STOMP（Simple Text Oriented Messaging Protocol，基于信息代理），通过信息代理
                                // 路由和处理器管理，Spring会配置一个基于消息传递的WebSocket通信系统。
@RequiredArgsConstructor  //
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    final JwtTokenProvider jwtTokenProvider;

    // 以便可以在配置类中使用webSocketHandler
//    private  WebSocketHandler webSocketHandler;

//    public void WebSocketStompConfig(WebSocketHandler webSocketHandler) {
//        this.webSocketHandler= webSocketHandler;
//    }

//    final StompInterceptor stompInterceptor;

    /**
     * 注册STOMP端点（Endpoint），用于使用WebSocket连接服务。
     * @param registry 用于配置WebSocket和STOMP端点的类的对象。
     */



    // 添加这个Endpoint，这样在网页中就可以通过websocket连接上服务,也就是我们配置websocket的服务地址,并且可以指定是否使用socketjs
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocket")      // 将"/webSocket"注册为STOMP端点，连接这个端点即可进行WebSocket通讯。
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // 记录握手中的Session信息
                //        .setHandshakeHandler()      // 握手处理，连接的时候认证获取其他数据验证。
                .setAllowedOrigins("*")     // 允许跨域。
//                .addInterceptors(new WebSocketHandshakeInterceptor())      // 拦截处理，自定义拦截器。
                .withSockJS();      // 支持sockJS访问。

    }


////将 CORS 配置放在 addCorsMappings 方法中
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("http://*.example.com", "http://*.anotherdomain.com")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowCredentials(true);  // 允许带凭证的请求
//    }

    /**
     * 注册信息代理，设置信息请求的各种规范。
     * @param registry MessageBrokerRegistry
     */

    // 配置消息代理，哪种路径的消息会进行代理处理
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");     // 配置简单信息代理，客户端接收地址的前缀信息。
        registry.setApplicationDestinationPrefixes("/app");     // 应用服务器地址的前缀，表示所有以/app开头的客户端信息或请求都会路
                                                                // 由到带有@MessageMapping注解的方法中。
        registry.setUserDestinationPrefix("/user");     // 一对一消息使用前缀，默认/user。
    }



    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel channel) {
                StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
                // 如果是连接请求（CONNECT 命令），从请求头中取出 token 并设置到认证信息中。
                if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
                    // 从连接头中提取授权令牌。
                    String bearerToken = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);

                    // 验证令牌格式并提取用户信息。
                    if (StrUtil.isNotBlank(bearerToken) && bearerToken.startsWith("Bearer ")) {
                        try {
                            // 移除 "Bearer " 前缀，从令牌中提取用户信息(username), 并设置到认证信息中。
                            String tokenWithoutPrefix = bearerToken.substring(7);
                            String username = jwtTokenProvider.getUsername(tokenWithoutPrefix);

                            if (StrUtil.isNotBlank(username)) {
                                accessor.setUser(() -> username);
                                return message;
                            }
                        } catch (Exception e) {
                            log.error("Failed to process authentication token.", e);
                        }
                    }
                }
                // 不是连接请求，直接放行。
                return ChannelInterceptor.super.preSend(message, channel);
            }
        });
    }
}
