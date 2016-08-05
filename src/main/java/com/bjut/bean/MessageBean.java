package com.bjut.bean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Created by Administrator on 2016/8/4.
 */
@XmlRootElement(name = "Customer")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"id", "author", "type",
        "dialog_partner", "body_xml", "reason", "timestamp", "ip"})

public class MessageBean {
    private int id;                        // ID号
    private String author;                    // 发送方
    private int type;                    // 消息类型
    private String dialog_partner;            // 接受方
    private String body_xml;                // 消息内容
    private String reason;                    //add by jiang
    private int timestamp;                // 时间戳
    private String ip;                        // ip地址

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDialog_partner() {
        return dialog_partner;
    }

    public void setDialog_partner(String dialog_partner) {
        this.dialog_partner = dialog_partner;
    }

    public String getBody_xml() {
        return body_xml;
    }

    public void setBody_xml(String body_xml) {
        this.body_xml = body_xml;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
