package com.paic.demo.tts.util;

import lombok.Data;

@Data
public class ResultInfo {

    private ResultCode code;

    private String message;

    private Object data;
}
