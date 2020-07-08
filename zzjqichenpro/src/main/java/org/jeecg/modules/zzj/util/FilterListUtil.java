package org.jeecg.modules.zzj.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FilterListUtil {

    public static String conversion(String conversion){
        Map<String,String> map=new HashMap<>();
        map.put("ALIPAY","0");
        map.put("WXPAY","1");
        map.put("YLPAY","2");
        return map.get(conversion);
    }


}
