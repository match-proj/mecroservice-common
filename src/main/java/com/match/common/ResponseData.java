package com.match.common;

import com.match.common.exception.BusinessException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;

/**
 * @Author zhangchao
 * @Date 2019/4/25 17:31
 * @Version v1.0
 */
public class ResponseData<T> extends LinkedHashMap<String, Object> {

    private static final String APLIT = "_";
    private static final String RESP_CODE = "resp_code";
    private static final String RESP_MSG = "resp_msg";
    private static final String CLAZZ = "clazz";
    private static final String CAUSE = "cause";

    public ResponseData(int code, String msg) {
        setResp_code(code);
        setResp_msg(msg);
    }

    public ResponseData(int code, BusinessException e) {
        setResp_code(code);
        setResp_msg(e.getErrorMsg());
        setClazz(e);
    }

    public ResponseData() {
        setResp_code(200);
        setResp_msg("OK");
    }

    public ResponseData<T> addObject(Object obj) {
        for (Field declaredField : obj.getClass().getDeclaredFields()) {
            try {
                declaredField.setAccessible(true);
                Object value = declaredField.get(obj);
                if(value instanceof Date && value != null){
                    put(revertName(declaredField.getName()), ((Date)value).getTime());
                }else{
                    put(revertName(declaredField.getName()), value);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return this;
    }


    private String revertName(String name) {
        return name;
//        StringBuffer buffer = new StringBuffer("");
//        for (char c : name.toCharArray()) {
//            if (c >= 65 && c <= 90) {
//                buffer.append(APLIT);
//                buffer.append(((char) (c + 32)));
//            } else {
//                buffer.append(c);
//            }
//        }
//        return buffer.toString();
    }


    public ResponseData<T> addAttrs(String key, Object val) {
        put(key, val);
        return this;
    }

    public int getResp_code() {
        return   (int)get(RESP_CODE);
    }

    public void setResp_code(int resp_code) {
        this.put(RESP_CODE, resp_code);
    }

    public String getResp_msg() {
        return (String) get(RESP_MSG);
    }

    public void setResp_msg(String resp_msg) {
        this.put(RESP_MSG, resp_msg);
    }

    public void setClazz(Exception e){
        this.put(CLAZZ,e.getClass().getName());
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.close();
        this.put(CAUSE,sw.toString().split("\n"));
    }
}
