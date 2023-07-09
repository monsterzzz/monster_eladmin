package com.sgmw.contractAccrued.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.sgmw.common.domain.ExcelUploadInfo;
import com.sgmw.contractAccrued.domain.ContractMainData;
import lombok.Data;
import me.zhengjie.exception.BadRequestException;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.*;


public class ContractMainDataListener implements ReadListener<ContractMainData> {

    private final List<ContractMainData> dataList;

    private final List<Map<String,String>> errorList;

    private final String redisPrefix;

    private final StringRedisTemplate template;

    private ExcelUploadInfo<ContractMainData> excelUploadInfo;

    private int row = 2;

    public ContractMainDataListener(StringRedisTemplate template,String redisPrefix) {
        this.dataList = new ArrayList<>();
        this.errorList  = new ArrayList<>();
        this.redisPrefix = redisPrefix;
        this.template = template;
    }

    public ExcelUploadInfo<ContractMainData> getExcelUploadInfo(){
        return excelUploadInfo;
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        ReadListener.super.invokeHead(headMap, context);
        List<String> tableHeader = new ArrayList<>();
        tableHeader.add("基地");
        tableHeader.add("费用属性");
        tableHeader.add("合同编号");
        tableHeader.add("合同起");
        tableHeader.add("合同止");
        tableHeader.add("采购员");
        tableHeader.add("供应商代码");
        tableHeader.add("供应商名称");
        tableHeader.add("业务内容");
        tableHeader.add("成本中心");
        tableHeader.add("科目代码");
        tableHeader.add("科目描述");
        tableHeader.add("PR号");
        tableHeader.add("结算方式");
        tableHeader.add("合同类别");
        tableHeader.add("是否延期Y/N");
        for (int i = 0; i < tableHeader.size(); i++) {
            if (!tableHeader.get(i).equals(headMap.get(i).getStringValue())) {
                throw new BadRequestException("表头不匹配,请检查表头是否正确。建议重新下载模板。");
            }
        }
    }

    @Override
    public void invoke(ContractMainData contractMainData, AnalysisContext analysisContext) {
        checkRow(this.row,contractMainData);
        dataList.add(contractMainData);
        this.row ++;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        this.excelUploadInfo = new ExcelUploadInfo<>(this.dataList,this.errorList);
        this.excelUploadInfo.upload(template,redisPrefix);
    }

    private boolean checkRow(int row,ContractMainData item){
        Map<String,String> errorMap = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        if (item.getBase() == null || item.getBase().equals("")) {
            stringBuilder.append("基地不能为空;");
        }
        if (item.getCostAttribute() == null || item.getCostAttribute().equals("")) {
            stringBuilder.append("费用属性不能为空;");
        }
        if (item.getContractNumber() == null || item.getContractNumber().equals("")) {
            stringBuilder.append("合同编号不能为空;");
        }
        if (item.getCostCenter() == null || item.getCostCenter().equals("")) {
            stringBuilder.append("成本中心不能为空;");
        }
        if (item.getPrNumber() == null || item.getPrNumber().equals("")) {
            stringBuilder.append("科目代码不能为空;");
        }
        if (!stringBuilder.toString().equals("")){
            errorMap.put("row",String.valueOf(row));
            errorMap.put("msg",stringBuilder.toString());
            errorList.add(errorMap);
        }
        return errorMap.size() == 0;
    }


}
