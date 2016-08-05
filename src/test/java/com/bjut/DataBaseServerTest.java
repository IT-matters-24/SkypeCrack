package com.bjut;

import com.bjut.Util.DataBaseServer;
import com.bjut.bean.UserInfoBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DataBaseServerTest {
    private Logger logger = LogManager.getLogger(DataBaseServerTest.class);
    Path userInfoFile = Paths.get("jyc1992928.ser");
    private UserInfoBean userInfoBean=new UserInfoBean(0);

    @Autowired
    private JdbcTemplate jdbcTemplate;

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
        DataBaseServer dataBaseServer = new DataBaseServer(null, "jyc1992928");
//        dataBaseServer.createUserFile();
//        UserInfoBean userInfo=dataBaseServer.readUserFile();
//        Assert.assertEquals(userInfo.getEnd(),0);
//        userInfo.setEnd(10);
//        dataBaseServer.writeUserFile(userInfo);
//        userInfo=dataBaseServer.readUserFile();
//        Assert.assertEuals(userInfo.getEnd(),10);
    }

    @Test
    public void testDB() {
        Assert.assertNotNull(jdbcTemplate);
        ((BasicDataSource) jdbcTemplate.getDataSource()).
                setUrl("jdbc:sqlite:C:\\Users\\Administrator\\AppData\\Roaming\\Skype\\jyc1992928\\main.db");
        jdbcTemplate.query("select max(id) as maxID from Messages", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                userInfoBean.setEnd(rs.getInt(1));
            }
        });
        System.out.println(userInfoBean.getEnd());
    }


}
