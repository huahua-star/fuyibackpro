package org.jeecg.modules.zzj.util;

import com.alibaba.fastjson.JSONObject;

public class JsonObjects {

    public static String turnjson(Object value) {
        final String jsons  = JSONObject.toJSONString(value);
            return jsons;
    }





}
