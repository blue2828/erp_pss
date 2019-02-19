package com.erp.exception;

import com.alibaba.fastjson.JSONObject;
import com.erp.activemq.JmsProducer;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.jms.Destination;

@ControllerAdvice
public class UnauthorizedException {
    @Autowired
    private JmsProducer jmsProducer;
    @ExceptionHandler(value = AuthorizationException.class)
    public void UnauthorizedExceptionHandler (Exception e) {
        Destination destination = new ActiveMQQueue("pushMsg");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errMsg", "没有权限");
        jmsProducer.sendMessage(destination, jsonObject.toJSONString());
    }
    @ExceptionHandler(value = UnknownAccountException.class)
    public void UnknownAccountExceptionHandler (Exception e) {
        Destination destination = new ActiveMQQueue("pushMsg");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errMsg", "账号不存在，请登录");
        jmsProducer.sendMessage(destination, jsonObject.toJSONString());
    }
}
