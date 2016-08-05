package com.bjut;

import javax.jws.WebService;

/**
 * Created by Administrator on 2016/8/4.
 */
@WebService
public interface IMessageService {
    public void dealMsg(MessageBean msg);

}
