package com.sirius.js2java.nashorn;

public class InvokeResult {

    Object payload;
    boolean success;
    int error_code;
    String error_msg;

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    @Override
    public String toString() {
        return "InvokeResult{" +
               "payload=" + payload +
               ", success=" + success +
               ", error_code=" + error_code +
               ", error_msg='" + error_msg + '\'' +
               '}';
    }
}
