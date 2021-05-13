package com.szlx.netty;


import java.util.Arrays;

/**
 * @author Sunny
 * @version 1.0
 * @date 2021/5/13 14:42
 */
public class Response<T> {
    private String requestId;
    private Integer status;
    private byte[] data;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "requestId='" + requestId + '\'' +
                ", status=" + status +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
