package com.paic.demo.tts.test;

import com.paic.demo.tts.service.impl.TtsServiceImpl;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws IOException {
        String url = "E:\\ads";
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        String address = url+"/tts"+sdf.format(new Date())+".pcm";
        TtsServiceImpl ttsService = new TtsServiceImpl();
        ttsService.tts("现在可以将服务器端启动了,由于设置了用户名密码",url);
//        File file = new File(address);
//        String s = file.toString();
//        String path = file.getPath();
//        System.out.println(s);
//        FileInputStream fis = new FileInputStream(url + "/tts20191220102445.pcm");
//        FileOutputStream fos = new FileOutputStream(target);
//        File file = new File(url + "/tts20191220102445.pcm");
//        byte[] bytes = Files.readAllBytes(file.toPath());
//        System.out.println(bytes);
//        //计算长度
//        byte[] buf = new byte[1024 * 4];
//        System.out.println(buf.length);
//        int size = fis.read(buf);
//        System.out.println(size);
    }
}
