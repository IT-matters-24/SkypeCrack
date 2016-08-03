package com.bjut;


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
    private Path userInfoFile;      //每个用户生成一个{userName}.seq

    private int end;                //读取到的最大数据


    public DataBaseServer(Path userDB, String userName) {
        this.userDB = userDB;
        this.userInfoFile = Paths.get(userName + ".ser");

    }


    @Override
    public void run() {
        logger.debug("当前扫描的数据库:" + userDB);
        UserInfo curUser = null;

        //第一次使用就创建
        if (Files.notExists(userInfoFile)) {
            try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()))) {
                Files.createFile(userInfoFile);
                out.writeObject(curUser = new UserInfo(0));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(userInfoFile.toFile()))) {
            curUser = (UserInfo) ois.readObject();
            curUser.setEnd(1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        while (!Thread.currentThread().interrupted()) {

        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(userInfoFile.toFile()));) {
            out.writeObject(curUser);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
