package com.httpclient;

import java.util.HashMap;

/**
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/7/8 12:47
 */
abstract class Message extends HashMap<String, String> {

    static final String PROTOCOL = "Protocol";

    static final String CONTENT = "content";

    public String getProtocol(){
        return get(PROTOCOL);
    }

    public String getContent(){
        return get(CONTENT);
    }



}
