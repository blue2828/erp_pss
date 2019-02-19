package com.erp.config.SocketConfig;

import com.erp.activemq.JmsProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;
import com.erp.websocket.PushMsgToView;
@Configuration("webSocketConfig")
public class WebSocketConfig {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
    @Autowired
    public void setJmsProducer (JmsProducer jmsProducer) {
        PushMsgToView.jmsProducer = jmsProducer;
    }
}
