package com.sgmw.common.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.utils.FileUtil;
import org.springframework.data.redis.core.StringRedisTemplate;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Data
public class ExcelUploadInfo <T>{

    private Boolean status;

    private String fileId = UUID.randomUUID().toString().replaceAll("-", "");

    private List<T> data;

    private List<Map<String,String>> errorList;

    public ExcelUploadInfo<T> toVo(){
        ExcelUploadInfo<T> excelUploadInfo = new ExcelUploadInfo<>();
        excelUploadInfo.setStatus(this.status);
        excelUploadInfo.setFileId(this.fileId);
        excelUploadInfo.setErrorList(errorList.subList(0, Math.min(errorList.size(), 50)));
        return excelUploadInfo;
    }

    public ExcelUploadInfo(List<T> data,List<Map<String,String>> errorList){
        this.status = errorList.size() == 0;
        this.errorList = errorList;
        this.data = data;
    }

    public ExcelUploadInfo(){}

    public List<T> getData() {
        return data;
    }


    public void upload(StringRedisTemplate template, String redisPrefix) {
        String redisKey = redisPrefix + this.getFileId();
        template.opsForValue().set(redisKey, JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect),10, TimeUnit.MINUTES);
    }

    public static <E> ExcelUploadInfo<E> getInfo (StringRedisTemplate template,String redisPrefix, String fileId) {
        String redisKey = redisPrefix  +  fileId;
        String jsonStr = template.opsForValue().get(redisKey);
        return JSON.parseObject(jsonStr,new TypeReference<ExcelUploadInfo<E>>() {});
    }

    public static void deleteRedisData(StringRedisTemplate template,String redisPrefix, String fileId) {
        String redisKey = redisPrefix + fileId;
        template.delete(redisKey);
    }

    public static <T> void download(StringRedisTemplate template,String redisPrefix, String fileId, HttpServletResponse response) throws IOException {
        String redisKey = redisPrefix +  fileId;
        String jsonStr = template.opsForValue().get(redisKey);
        ExcelUploadInfo<T> excelUploadInfo = JSON.parseObject(jsonStr,new TypeReference<ExcelUploadInfo<T>>() {});
        if (excelUploadInfo == null || excelUploadInfo.getErrorList() == null || excelUploadInfo.getErrorList().size() == 0) {
            throw new BadRequestException("下载失败，文件不存在或已过期");
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String,String> row : excelUploadInfo.getErrorList()) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("行号", row.get("row"));
            map.put("错误信息", row.get("msg"));
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }



}
