package com.bjut;

import javax.jws.WebService;

/**
 * Created by Administrator on 2016/8/4.
 */
@WebService(endpointInterface = "com.bjut.IMessageService")
public class MessageService implements IMessageService {

    @Override
    public void dealMsg(MessageBean msg) {
        System.out.println(msg.getId());
    }
}
