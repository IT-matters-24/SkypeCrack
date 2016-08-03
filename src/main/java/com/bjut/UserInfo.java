package com.bjut;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/8/1.
 *
 */
public class UserInfo implements Serializable {
    private String userName;
    private int end;

    public UserInfo( int end) {

        //this.userName = userName;
        this.end = end;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public String getUserName() {

        return userName;
    }

    public int getEnd() {
        return end;
    }


}
