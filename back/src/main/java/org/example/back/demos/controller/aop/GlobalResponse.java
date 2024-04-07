package org.example.back.demos.controller.aop;

/**
 * @author ljn
 * @Description:
 * @date 2024/4/7/11:14
 */

public class GlobalResponse {
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private int code;
    private String message;
    // 可能还有其他字段，例如数据或错误详情

    public GlobalResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

}