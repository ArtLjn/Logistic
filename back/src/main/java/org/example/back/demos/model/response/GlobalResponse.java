package org.example.back.demos.model.response;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private int code;
    private String info;
    // 可能还有其他字段，例如数据或错误详情

    public GlobalResponse(int code, String message) {
        this.code = code;
        this.info = message;
    }

}