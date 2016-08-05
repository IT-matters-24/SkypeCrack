package com.bjut;

import com.bjut.Util.WatchDir;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016/7/28.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String SkypeDataPath=System.getenv("APPDATA")+"\\Skype";
        Path dir = Paths.get(SkypeDataPath);
        new WatchDir(dir, true).processEvents();


    }
}
