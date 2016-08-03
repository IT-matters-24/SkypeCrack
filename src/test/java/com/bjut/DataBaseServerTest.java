package com.bjut;

import com.bjut.bean.UserInfoBean;
import junit.framework.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016/8/1.
 */
public class DataBaseServerTest {
    Logger logger = LogManager.getLogger(DataBaseServerTest.class);
    Path userInfoFile = Paths.get("jyc1992928.ser");
    @Test
    public void testPath() {

        logger.trace("我是trace信息");
        logger.debug("我是debug信息");
        logger.info("我是info信息");    //info级别的信息
        logger.warn("我是warn信息");
        logger.error("我是error信息");   //error级别的信息，参数就是你输出的信息
        logger.fatal("我是fatal信息");
    }

    @Test
    public void testFile() {
        DataBaseServer dataBaseServer=new DataBaseServer(null,"jyc1992928");
        dataBaseServer.createUserFile();
        UserInfoBean userInfo=dataBaseServer.readUserFile();
        Assert.assertEquals(userInfo.getEnd(),0);
        userInfo.setEnd(10);
        dataBaseServer.writeUserFile(userInfo);
        userInfo=dataBaseServer.readUserFile();
        Assert.assertEquals(userInfo.getEnd(),10);

    }


}
