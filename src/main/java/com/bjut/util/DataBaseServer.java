package com.bjut.util;


import com.bjut.bean.MessageBean;
import com.bjut.bean.UserInfoBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Administrator on 2016/7/31.
 * 每当有新的用户登录,WatchDir发来一个请求.
 * 为到来的数据库扫描请求进行服务
 */
public class DataBaseServer implements Runnable {
    private static Logger logger = LogManager.getLogger(DataBaseServer.class);
    private String userDB;          //userDB, absolute path
    private String userName;
    //   private int end;                //last line have read
    private Path userInfoFile;      //store 'end' in {userName}.seq file for each user

    private JdbcTemplate jdbcTemplate = (JdbcTemplate) ApplicationUtil.getBean("jdbcTemplate");
    ;


    public DataBaseServer(String userDB, String userName) {
        this.userDB = userDB;
        this.userInfoFile = Paths.get(userName + ".ser");
        ((BasicDataSource) jdbcTemplate.getDataSource()).setUrl(userDB);

    }


    @Override
    public void run() {
        logger.debug("scanning DB:" + userDB);

        int savePoint = HttpUtil.getSavePoint(userName);
        List<MessageBean> msgs;
        //todo scannging db with the help of userFile
        while (!Thread.currentThread().interrupted()) {
            msgs = obtainReadyMsg(savePoint);
            savePoint = msgs.get(msgs.size()).getId();
            HttpUtil.sendMsgs(msgs);
        }

    }





    private List<MessageBean> obtainReadyMsg(int savePoint) {
        String sqlMax = "SELECT MAX(id) AS maxID FROM Messages";
        String sqlBet = "SELECT * FROM Messages WHERE id BETWEEN ? AND ?";
        Integer tableEnd = jdbcTemplate.queryForObject(sqlMax, Integer.class);

        List<MessageBean> msgs = jdbcTemplate.query(sqlBet, new Object[]{savePoint, tableEnd}, new RowMapper<MessageBean>() {
            @Override
            public MessageBean mapRow(ResultSet rs, int rowNum) throws SQLException {
                MessageBean bean = new MessageBean();
                bean.setId(rs.getInt("id"));
                bean.setAuthor(rs.getString("author"));
                bean.setId(rs.getInt("type"));
                bean.setDialog_partner(rs.getString("dialog_partner"));
                bean.setReason(rs.getString("reason"));
                bean.setTimestamp__ms(rs.getInt("timestamp__ms"));
                return bean;
            }
        });
        return msgs;

    }




    @Deprecated
    private void createUserFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
            out.writeObject(new UserInfoBean(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private UserInfoBean readUserFile() {
        UserInfoBean curUser = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userInfoFile.toFile()))) {
            curUser = (UserInfoBean) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return curUser;
        }

    }

    @Deprecated
    private void writeUserFile(UserInfoBean curUser) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
            out.writeObject(curUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
