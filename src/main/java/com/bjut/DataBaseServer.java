package com.bjut;


import com.bjut.bean.UserInfoBean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private Path userDB;
    private int end;                //last line have read
    private Path userInfoFile;      //store 'end' in {userName}.seq file for each user


    public DataBaseServer(Path userDB, String userName) {
        this.userDB = userDB;
        this.userInfoFile = Paths.get(userName + ".ser");

    }

     void createUserFile() {

         try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
            out.writeObject(new UserInfoBean(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    UserInfoBean readUserFile() {
        UserInfoBean curUser = null;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userInfoFile.toFile()))) {
            curUser = (UserInfoBean) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return curUser;
    }

     void writeUserFile(UserInfoBean curUser) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
            out.writeObject(curUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()  {
        logger.debug("scanning DB:" + userDB);
        if (Files.notExists(userInfoFile)) {
            createUserFile();
        }
        UserInfoBean curUser = readUserFile();

        //todo scannging db with the help of userFile
        while (!Thread.currentThread().interrupted()) {

        }
        writeUserFile(curUser);
    }
}
