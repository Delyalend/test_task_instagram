//package com.instagram.configuration;
//
//import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
//import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;
//
//public class ConfigurationWebSocketSecurity extends AbstractSecurityWebSocketMessageBrokerConfigurer {
//
//    @Override
//    protected void configureInbound(
//            MessageSecurityMetadataSourceRegistry messages) {
//        messages
//                .simpDestMatchers("/test/**").authenticated()
//                .anyMessage().authenticated();
//    }
//
//}
