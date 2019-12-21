package com.paic.demo.tts.service.impl;

import com.iflytek.cloud.speech.*;
import com.paic.demo.tts.service.TtsService;
import com.paic.demo.tts.util.WaveHeader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
@Slf4j
public class TtsServiceImpl implements TtsService {

    //appid需要申请
    private String appid = "*****";

    public void tts(String content, String url) {

        SpeechUtility.createUtility(SpeechConstant.APPID +"="+appid);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String address = url+"/tts"+sdf.format(new Date())+".pcm";
        String target = url+"/tts"+sdf.format(new Date())+".wav";
        File f = new File(address);
        if (!f.exists()){
            log.info("文件不存在：",f);
        }
        //合成监听器
        SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
            //progress为合成进度0^100
            @Override
            public void onBufferProgress(int progress) {
                if (progress == 100){
                    if (f.exists()){
                        log.info("文件已存在：",f);
                        fileMsg(address);
                        System.out.println(progress);
                    }
                }
                System.out.println("=========" + progress);
            }
            //会话合成完成回调接口
            //uri为合成保存地址，error为错误信息，为null时表示合成会话成功
            @SneakyThrows
            @Override
            public void onSynthesizeCompleted(String uri, SpeechError error) {

                log.info("onCompleted enter");
                if (null != error){
                    log.info("onCompleted Code: " + error.getErrorCode());
//                    String text = error.getErrorDescription(true);
                }else {
                    if (f.exists()){
                        log.info("文件已存在：",f);
                        convertAudioFiles(address, target);
                        fileMsg(address);
                    }
                }
                log.info("uri==" + uri);
                log.info("onCompleted leave");
            }
            @Override
            public void onEvent(int arg0, int arg1, int arg2, int arg3, Object arg4, Object arg5) {
                //TODO Auto-generated method stub
            }
        };
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速，范围0^100
        mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0^100
        mTts.setParameter(SpeechConstant.VOLUME, "50");//设置音量，范围0^100
        //3.开始合成
        mTts.synthesizeToUri(content, address, synthesizeToUriListener);
    }

    /**
     * 转换音频文件
     * @param src 需要转换的pcm音频路径
     * @param target 保存转换后wav格式的音频路径
     * @throws Exception
     * ---------------------
     * 作者：EasonGod
     * 来源：CSDN
     * 原文：https://blog.csdn.net/qq_37980436/article/details/64923299
     */
    private static void convertAudioFiles(String src, String target) throws Exception {
        FileInputStream fis = new FileInputStream(src);
        FileOutputStream fos = new FileOutputStream(target);

        //计算长度
        byte[] buf = new byte[1024 * 4];
        int size = fis.read(buf);
        int PCMSize = 0;
        while (size != -1) {
            PCMSize += size;
            size = fis.read(buf);
        }
        fis.close();

        //填入参数，比特率等等。这里用的是16位单声道 8000 hz
        WaveHeader header = new WaveHeader();
        //长度字段 = 内容的大小（PCMSize) + 头部字段的大小(不包括前面4字节的标识符RIFF以及fileLength本身的4字节)
        header.fileLength = PCMSize + (44 - 8);
        header.FmtHdrLeth = 16;
        header.BitsPerSample = 16;
        header.Channels = 2;
        header.FormatTag = 0x0001;
        header.SamplesPerSec = 8000;
        header.BlockAlign = (short)(header.Channels * header.BitsPerSample / 8);
        header.AvgBytesPerSec = header.BlockAlign * header.SamplesPerSec;
        header.DataHdrLeth = PCMSize;

        byte[] h = header.getHeader();

        assert h.length == 44; //WAV标准，头部应该是44字节
        //write header
        fos.write(h, 0, h.length);
        //write data stream
        fis = new FileInputStream(src);
        size = fis.read(buf);
        while (size != -1) {
            fos.write(buf, 0, size);
            size = fis.read(buf);
        }
        fis.close();
        fos.close();
        System.out.println("Convert OK!");
    }

    private static void fileMsg(String str){
        File file = new File(str);
        String s = file.toString();
        String path = file.getPath();
        System.out.println("=========" + path);
    }
}
