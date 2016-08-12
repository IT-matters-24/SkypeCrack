package com.bjut.bean;

import java.io.Serializable;

/**
 *
 * Store last line number that have been read
 */
 public class UserInfoBean implements Serializable {

    private Integer end;

    public UserInfoBean(int end) {

        this.end = end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public Integer getEnd() {
        return end;
    }


}
