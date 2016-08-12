package com.bjut;

import com.bjut.util.WatchDir;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2016/7/28.
 */
public class Main {
    public static void main(String[] args) throws Exception {
       // System.out.println(Thread.currentThread().getContextClassLoader().getResource("") );
         new ClassPathXmlApplicationContext("applicationContext.xml");
        String SkypeDataPath = System.getenv("APPDATA") + "\\Skype";
        Path dir = Paths.get(SkypeDataPath);

        new WatchDir(dir).processEvents();
       // watchDir(dir, true).processEvents();

    }
}
