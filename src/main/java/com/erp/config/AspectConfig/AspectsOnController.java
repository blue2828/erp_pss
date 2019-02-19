package com.erp.config.AspectConfig;

import com.alibaba.fastjson.JSONObject;
import com.erp.activemq.JmsProducer;
import com.erp.utils.ContextUtil;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jms.Destination;
import java.util.Arrays;


@Aspect
@Component
public class AspectsOnController {
    @Autowired
    private JmsProducer jmsProducer;
    private static String[] EXCLUDEMETHODS =
            {"validateUser", "isLogined", "getUserImg", "getUserByIdOrName", "logout", "userUtilInController"};
    private static Logger logger = LoggerFactory.getLogger(AspectsOnController.class);
    @Pointcut("execution(public * com.erp.contorller.*.*.*(..))")
    public void  isLogined() {

    }

    @Before("isLogined()")
    public void isLogined (JoinPoint joinPoint) {
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        boolean flag = Arrays.asList(EXCLUDEMETHODS).contains(methodName);
        if (flag)
            return;
        boolean isLogined = ContextUtil.isLogined();
        if (!isLogined) {
            Destination destination = new ActiveMQQueue("pushMsg");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("errMsg", "登录超时或者尚未登录");
            jmsProducer.sendMessage(destination, jsonObject.toJSONString());
        }
    }

}
