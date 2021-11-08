package com.kruger.test.admin.rest.model;

import java.time.LocalDateTime;

public class Response<C> {
    private Boolean OK;
    private LocalDateTime dateTime;
    private String message;
    private String code;
    private C content;

    public Response() {
    }

    public Response(Boolean oK, LocalDateTime dateTime, String message, String code, C content) {
        OK = oK;
        this.dateTime = dateTime;
        this.message = message;
        this.code = code;
        this.content = content;
    }

    public Response(String message, C content) {
        this.dateTime = LocalDateTime.now();
        this.OK = true;
        this.message = message;
        this.content = content;
        this.code = "000";
    }

    public Response(Boolean oK, String message) {
        OK = oK;
        this.message = message;
        this.dateTime = LocalDateTime.now();
        this.code = "000";
    }

    public Response(C content) {
        this.dateTime = LocalDateTime.now();
        this.OK = true;
        this.content = content;
        this.code = "000";
    }

    public Response(Boolean oK, String message, String code) {
        OK = oK;
        this.message = message;
        this.code = code;
        this.dateTime = LocalDateTime.now();
    }

    public Boolean getOK() {
        return OK;
    }

    public void setOK(Boolean oK) {
        OK = oK;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public C getContent() {
        return content;
    }

    public void setContent(C content) {
        this.content = content;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Response [OK=" + OK + ", code=" + code + ", content=" + content + ", dateTime=" + dateTime
                + ", message=" + message + "]";
    }

}
