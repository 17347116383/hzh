

package com.itdy.hqsm.security.myshiro.utils;



import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @date 2019/10/13
 * @Author Administrator
 * 返回数据
 * @email
 */
public class BaseResult extends HashMap<String, Object> {



    private static final long serialVersionUID = 1L;



    public BaseResult() {
        put("Status", Constant.OK);
        put("msg", Constant.OK_MSG);
    }

    public static BaseResult error() {
        return error(Constant.FAIL, "未知异常，请联系管理员");
    }

    public static BaseResult error(String msg) {
        return error(Constant.FAIL, msg);
    }

    public static BaseResult error(int code, String msg) {
        BaseResult r = new BaseResult();
        r.put("Status", code);
        r.put("msg", msg);
        return r;
    }

    public static BaseResult ok(String msg) {
        BaseResult r = new BaseResult();
        r.put("msg", msg);
        return r;
    }

    public static BaseResult ok(int code, String msg) {
        BaseResult r = new BaseResult();
        r.put("Status", code);
        r.put("msg", msg);
        return r;
    }

    public static BaseResult ok(Map<String, Object> map) {
        BaseResult r = new BaseResult();
        r.putAll(map);
        return r;
    }

    public static BaseResult ok() {
        return new BaseResult();
    }

    @Override
    public BaseResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static BaseResult toMap(String key, Object value) {
        BaseResult r = new BaseResult();
        r.clear();
        r.put(key, value);
        return r;

    }
}
