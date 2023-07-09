package com.sgmw.common.util;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.HashMap;
import java.util.Map;

public class AjaxResultData{

    public static Map<String,Object> getData(Object data,long page,long size,long total){
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        map.put("page",page);
        map.put("size",size);
        map.put("total",total);
        return map;
    }

    public static Map<String,Object> getData(Object data){
        Map<String,Object> map = new HashMap<>();
        map.put("data",data);
        return map;
    }

}
