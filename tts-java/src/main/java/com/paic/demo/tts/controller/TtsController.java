package com.paic.demo.tts.controller;

import com.paic.demo.tts.service.TtsService;
import com.paic.demo.tts.util.ResultInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("tts")
@Api("tts Restful api")
public class TtsController {
    @Autowired
    private TtsService ttsService;

    @ResponseBody
    @RequestMapping(value = "tts.do", method = RequestMethod.POST)
    @ApiOperation(value = "在线语音合成", notes = "在线语音合成", response = ResultInfo.class, httpMethod = "POST")
    public void queryCaseListNew(String content, String url){
        ttsService.tts(content,url);
    }
}
