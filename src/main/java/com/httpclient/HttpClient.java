package com.httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
/** httpClient 简单实现
 * @author xiao
 * @version 1.0.0
 * @datetime 2016/7/7 16:39
 */
public class HttpClient {


    private static byte[] request = null;

    static {
        StringBuffer temp = new StringBuffer();
        temp.append("POST / HTTP/1.1\r\n");
        temp.append("Host: 101.200.131.11:80\r\n");
        //temp.append("Connection: keep-alive\r\n");
        temp.append("Cache-Control: max-age=0\r\n");
        temp.append("User-Agent: Mozilla/5.0 (Windows NT 5.1) AppleWebKit/536.11 (KHTML, like Gecko) Chrome/20.0.1132.47 Safari/536.11\r\n");
        temp.append("Accept: text/html; charset=UTF-8,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8\r\n");
        temp.append("Accept-Encoding: gzip,deflate,sdch\r\n");
        temp.append("Accept-Language: zh-CN,zh;q=0.8\r\n");
        temp.append("Accept-Charset: GBK,utf-8;q=0.7,*;q=0.3\r\n");
        temp.append("\r\n");
        request = temp.toString().getBytes();
    }

    public Response sendHttpRequest() {
        try {
            final SocketChannel socket  = SocketChannel.open(new InetSocketAddress("101.200.131.11", 80));
            socket.configureBlocking(false);//配置通道使用非阻塞模式
            while (!socket.finishConnect()) {
                Thread.sleep(10);
            }
            socket.write(ByteBuffer.wrap(request));
            Thread.sleep(30);
            return getResponse(socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;
    }

    private Response getResponse(SocketChannel socket) throws IOException {
        int read = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024);// 创建1024字节的缓冲
        while ((read = socket.read(buffer)) != -1) {
            if (read == 0 ) {
                break;
            }
            buffer.flip();// flip方法在读缓冲区字节操作之前调用。
            buffer.clear();// 清空缓冲
        }
        String result = new String(buffer.array());
        BufferedReader br = new BufferedReader(new StringReader(result));
        return new Response(br);
    }

    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        for (int i = 0; i<10; i++){
            Response response = client.sendHttpRequest();
            System.out.println(response);
        }

    }

}