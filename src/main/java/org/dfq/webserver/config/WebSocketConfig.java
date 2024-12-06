package org.dfq.webserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

/**
 * @author daVinci
 * WebSocket配置类
 */

@Slf4j
@Configuration  //配置类 使Spring在启动时加载该类中的Bean定义，将其纳入应用程序上下文管理。
//@EnableWebSocket  // 对原生WebSocket协议的支持。手动管理信息传递，包括手动管理WebSocket连接的生命周期和消息的发送接收等。
@EnableWebSocketMessageBroker   // STOMP（Simple Text Oriented Messaging Protocol，基于信息代理），通过信息代理
                                // 路由和处理器管理，Spring会配置一个基于消息传递的WebSocket通信系统。
@RequiredArgsConstructor  // 自动生成构造函数
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * 注册STOMP端点（Endpoint），用于使用WebSocket连接服务。
     * @param registry 用于配置WebSocket和STOMP端点的类的对象。
     *                   它用于配置WebSocket端点，包括端点的路径、端点的访问权限、端点的消息格式等。
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/webSocket")      // 将"/webSocket"注册为STOMP端点，连接这个端点即可进行WebSocket通讯。
                .addInterceptors(new HttpSessionHandshakeInterceptor())  // 记录握手中的Session信息
                //        .setHandshakeHandler()      // 握手处理，连接的时候认证获取其他数据验证。
                .setAllowedOrigins("http://localhost:9610")     // 允许跨域.
                                                                // 这个地方踩坑，原来一直用“*”，这样会和Access-Control-Allow-Origin冲突。
//                .addInterceptors(new WebSocketHandshakeInterceptor())      // 拦截处理，自定义拦截器。
                .withSockJS();      // 支持sockJS访问。
    }

    /**
     * 注册信息代理，设置信息请求的各种规范。
     * @param registry MessageBrokerRegistry 用于配置信息代理的类的对象。
     *                   它用于配置信息代理，包括消息代理的前缀、消息代理的路径、消息代理的类型等。
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");     // 配置简单信息代理，客户端接收地址的前缀信息。
        registry.setApplicationDestinationPrefixes("/app");     // 应用服务器地址的前缀，表示所有以/app开头的客户端信息或请求都会路
                                                                // 由到带有@MessageMapping注解的方法中。
        registry.setUserDestinationPrefix("/user");     // 一对一消息使用前缀，默认/user。
    }
}
