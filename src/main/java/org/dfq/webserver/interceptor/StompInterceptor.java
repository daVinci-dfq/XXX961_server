package org.dfq.webserver.interceptor;

import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dfq.webserver.service.JwtService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class StompInterceptor implements ChannelInterceptor {

    final JwtService jwtService;

//    @Override
//    public Message<?> preSend(@Nonnull Message<?> message, @Nonnull MessageChannel messageChannel) {
//        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
//        assert accessor != null;
//        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
//            List<String> nativeHeader = accessor.getNativeHeader("Authorization");
//            if (nativeHeader != null && !nativeHeader.isEmpty()) {
//                String token = nativeHeader.get(0);
//
//            }
//        }
//        return message;
//    }


}
