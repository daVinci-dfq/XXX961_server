package org.dfq.webserver.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dfq.webserver.service.JwtService;
import org.springframework.messaging.support.ChannelInterceptor;

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
