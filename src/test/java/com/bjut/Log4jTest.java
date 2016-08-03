package com.bjut;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * Created by Administrator on 2016/8/1.
 */
public class Log4jTest {
    @Test
    public void testLogLevel(){
        Logger logger = LogManager.getLogger(Log4jTest.class);
        System.out.println(logger.getClass().getName());
        logger.trace("我是trace信息");
        logger.debug("我是debug信息");
        logger.info("我是info信息");    //info级别的信息
        logger.warn("我是warn信息");
        logger.error("我是error信息");   //error级别的信息，参数就是你输出的信息
        logger.fatal("我是fatal信息");
       // logger.log(Level.DEBUG, "我是debug信息");   //这个就是制定Level类型的调用：谁闲着没事调用这个，也不一定哦！
    }


}
