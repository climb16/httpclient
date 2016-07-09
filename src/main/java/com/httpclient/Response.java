package com.httpclient;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * HTTP 响应信息
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/7/8 12:05
 */
public class Response extends Message {

    static final String STATUS_LINE = "StatusLine";
    static final String STATUS_CODE = "StatusCode";

    Response(BufferedReader reader){
        readHeader(reader);
        readBody(reader);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取响应头
     * @param reader
     */
    private void readHeader(BufferedReader reader){
        try {
            //读取第一行，即响应状态行
            String s = reader.readLine();
            put(STATUS_LINE, s);
            String[] line = s.split(" ");
            if (line.length >= 3){
                put(PROTOCOL, line[0].trim());
                put(STATUS_CODE, line[1].trim());
            }
            //读取响应头信息
            while((s = reader.readLine()) != null) {
                if (s != null && s.contains(":")){
                    line = s.split(":");
                    if (line.length >= 2){
                        put(line[0], line[1]);
                    }
                }
                //如果遇见单独的换行,表示头信息已读完
                if(s.equals("")){
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取响应体
     * @param reader
     */
    private void readBody(BufferedReader reader){
        StringBuffer sb = new StringBuffer();
        String s;
        try {
            while((s = reader.readLine()) != null) {
               sb.append(s);
            }
            put(CONTENT, sb.toString());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public Response(){}

    public String getStatusLine(){
        return get(STATUS_LINE);
    }

    public String getStatusCode(){
        return get(STATUS_CODE);
    }
}