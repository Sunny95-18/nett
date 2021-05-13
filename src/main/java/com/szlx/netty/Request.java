package com.szlx.netty;

import java.util.Arrays;

/**
 * @author Sunny
 * @version 1.0
 * @date 2021/5/13 14:37
 */
public class Request<T> {

    private String requestId;
    private Integer requestType;
    private byte[] data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId='" + requestId + '\'' +
                ", requestType=" + requestType +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
