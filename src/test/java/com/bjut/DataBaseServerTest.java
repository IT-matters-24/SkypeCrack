package com.bjut;

import junit.framework.Assert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
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
        Path userInfoFile = Paths.get("jyc1992928.ser");
        Path userInfoFile1 = Paths.get("jyc19929dd28.ser");

        logger.trace("我是trace信息");
        logger.debug("我是debug信息");
        logger.info("我是info信息");    //info级别的信息
        logger.warn("我是warn信息");
        logger.error("我是error信息");   //error级别的信息，参数就是你输出的信息
        logger.fatal("我是fatal信息");
    }

    @Test
    public void testCreateFile() {
        String userName = "jyc1992928";
        new Thread(new DataBaseServer(null, userName)).start();
        Path userInfoFile = Paths.get("jyc1992928.ser");
        Assert.assertTrue(Files.exists(userInfoFile));
    }

    @Test
    public void testReadFile() {
        UserInfo curUser=null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userInfoFile.toFile()))) {
            curUser = (UserInfo) ois.readObject();
            curUser.setEnd(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assert.assertEquals(curUser.getEnd(),1);
    }
}
