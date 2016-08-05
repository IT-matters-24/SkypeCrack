package com.bjut.Util;


import com.bjut.bean.UserInfoBean;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016/7/31.
 * 每当有新的用户登录,WatchDir发来一个请求.
 * 为到来的数据库扫描请求进行服务
 */
public class DataBaseServer implements Runnable {
    private static Logger logger = LogManager.getLogger(DataBaseServer.class);
    private String userDB;          //userDB, absolute path
    private int end;                //last line have read
    private Path userInfoFile;      //store 'end' in {userName}.seq file for each user

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public DataBaseServer(String userDB, String userName) {
        this.userDB = userDB;
        this.userInfoFile = Paths.get(userName + ".ser");

    }



    @Override
    public void run() {
        logger.debug("scanning DB:" + userDB);
        if (Files.notExists(userInfoFile)) {
            createUserFile();
        }
        UserInfoBean curUser = readUserFile();

        //todo scannging db with the help of userFile
        while (!Thread.currentThread().interrupted()) {
            ((BasicDataSource)jdbcTemplate.getDataSource()).setUrl(userDB);
        }
        writeUserFile(curUser);
    }

    private void createUserFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
            out.writeObject(new UserInfoBean(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void writeUserFile(UserInfoBean curUser) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
            out.writeObject(curUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
