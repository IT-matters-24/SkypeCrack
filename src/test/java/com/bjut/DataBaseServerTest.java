package com.bjut;

import com.bjut.util.DataBaseServer;
import com.bjut.bean.MessageBean;
import com.bjut.bean.UserInfoBean;
import com.google.gson.Gson;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:applicationContext.xml"})
public class DataBaseServerTest {
    private Logger logger = LogManager.getLogger(DataBaseServerTest.class);
    Path userInfoFile = Paths.get("jyc1992928.ser");
    private UserInfoBean userInfoBean = new UserInfoBean(0);


    //private DataBaseServer dataBaseServer=new DataBaseServer(null, "jyc1992928");

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
       // DataBaseServer dataBaseServer =
//        dataBaseServer.createUserFile();
//        UserInfoBean userInfo=dataBaseServer.readUserFile();
//        Assert.assertEquals(userInfo.getEnd(),0);
//        userInfo.setEnd(10);
//        dataBaseServer.writeUserFile(userInfo);
//        userInfo=dataBaseServer.readUserFile();
//        Assert.assertEuals(userInfo.getEnd(),10);
    }

    @Test
    public void testDB() throws IOException {
        Assert.assertNotNull(jdbcTemplate);
        ((BasicDataSource) jdbcTemplate.getDataSource()).
                setUrl("jdbc:sqlite:C:\\Users\\Administrator\\AppData\\Roaming\\Skype\\jyc1992928\\main.db");
        String sqlMax = "SELECT MAX(id) AS maxID FROM Messages";
        String sqlBet = "SELECT * FROM Messages WHERE id BETWEEN ? AND ?";
        Integer tableEnd = jdbcTemplate.queryForObject(sqlMax, Integer.class);

        List<MessageBean> l = jdbcTemplate.query(sqlBet, new Object[]{1200, tableEnd}, new RowMapper<MessageBean>() {
            @Override
            public MessageBean mapRow(ResultSet rs, int rowNum) throws SQLException {
                MessageBean bean = new MessageBean();
                bean.setId(rs.getInt("id"));
                bean.setAuthor(rs.getString("author"));
                bean.setId(rs.getInt("type"));
                bean.setDialog_partner(rs.getString("dialog_partner"));
                bean.setReason(rs.getString("reason"));
                bean.setTimestamp__ms(rs.getInt("timestamp__ms"));
                // bean.setIp(rs.getString("ip"));

                return bean;
            }
        });
        for (MessageBean m : l) {
            HttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost("http://localhost:8080/dealMsg1.do");
         //   post.setHeader("accept", "application/json");
            Gson gson = new Gson();

            StringEntity entity = new StringEntity(gson.toJson(m));
            entity.setContentType("application/json;charset=utf-8");

            post.setEntity(entity);
            HttpResponse response = httpClient.execute(post);
        }
    }


    @Test
    public void testGetMaxID(){
        Assert.assertEquals(DataBaseServer.getMaxID("jyc1992928"),1000);
    }

}
