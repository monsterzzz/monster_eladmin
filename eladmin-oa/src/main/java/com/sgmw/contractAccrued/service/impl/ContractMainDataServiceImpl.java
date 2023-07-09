/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package com.sgmw.contractAccrued.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sgmw.common.domain.ExcelUploadInfo;
import com.sgmw.common.util.AjaxResultData;
import com.sgmw.contractAccrued.domain.ContractMainData;
import com.sgmw.contractAccrued.listener.ContractMainDataListener;
import com.sgmw.contractAccrued.mapper.ContractMainDataMapper;
import com.sgmw.contractAccrued.service.vo.ContractMainDataVo;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.utils.FileUtil;
import com.sgmw.contractAccrued.service.IContractMainDataService;
import com.sgmw.contractAccrued.service.params.ContractMainDataQueryCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
* @description 服务实现
* @author monster
* @date 2023-07-08
**/
@Service
@Slf4j
public class ContractMainDataServiceImpl extends ServiceImpl<ContractMainDataMapper,ContractMainData> implements IContractMainDataService {

    private final String redisPrefix = "contractAccrued:ContractMainData:";

    @Autowired
    private StringRedisTemplate redisTemplate;


    @Override
    public Map<String,Object> queryAll(ContractMainDataQueryCriteria criteria, Pageable pageable){
        IPage<ContractMainData> page = new Page<>(pageable.getPageNumber(), pageable.getPageSize());
        IPage<ContractMainData> pageData = this.baseMapper.selectPage(page,criteria.getQueryWrapper());
        return AjaxResultData.getData(pageData.getRecords(),pageData.getCurrent(),pageData.getSize(),pageData.getTotal());
    }

    @Override
    public List<ContractMainDataVo> queryAll(ContractMainDataQueryCriteria criteria){
        return this.baseMapper.selectList(criteria.getQueryWrapper()).stream().map(ContractMainData::toVo).collect(Collectors.toList());
    }

    @Override
    public List<ContractMainData> findByIds(Integer[] ids) {
        return this.baseMapper.selectBatchIds(Arrays.asList(ids));
    }

    @Override
    @Transactional
    public ContractMainDataVo createOrUpdate(ContractMainData resources) {
        try{
            this.saveOrUpdate(resources);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("保存失败\n" + e);
        }
        return resources.toVo();
    }

    @Override
    public void deleteAll(Integer[] ids) {
        try{
            this.baseMapper.deleteBatchIds(Arrays.asList(ids));
        }catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("删除失败\n" + e);
        }
    }

    @Override
    public void download(List<ContractMainDataVo> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ContractMainDataVo ContractMainData : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("费用属性", ContractMainData.getCostAttribute());
            map.put("合同编号", ContractMainData.getContractNumber());
            map.put("合同起", ContractMainData.getContractStart());
            map.put("合同止", ContractMainData.getContractEnd());
            map.put("采购员", ContractMainData.getPurchaser());
            map.put("供应商代码", ContractMainData.getSupplierCode());
            map.put("供应商名称", ContractMainData.getSupplierName());
            map.put("业务内容", ContractMainData.getBusinessContent());
            map.put("成本中心", ContractMainData.getCostCenter());
            map.put("科目代码", ContractMainData.getAccountCode());
            map.put("科目描述", ContractMainData.getAccountDescription());
            map.put("PR号", ContractMainData.getPrNumber());
            map.put("结算方式", ContractMainData.getSettlementMethod());
            map.put("合同类别", ContractMainData.getContractType());
            map.put("是否延期Y/N", ContractMainData.getDelayStatus());
            map.put("基地", ContractMainData.getBase());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public ExcelUploadInfo<ContractMainData> upload(MultipartFile file) throws IOException {
        ContractMainDataListener listener = new ContractMainDataListener(this.redisTemplate,this.redisPrefix);
        EasyExcel.read(file.getInputStream(), ContractMainData.class,listener).sheet(0).doRead();
        return listener.getExcelUploadInfo().toVo();
    }

    @Override
    @Transactional
    public void saveUpload(String fileId) {
        ExcelUploadInfo<ContractMainData> excelUploadInfo = ExcelUploadInfo.getInfo(redisTemplate,redisPrefix,fileId);
        if (excelUploadInfo == null) {
            throw new BadRequestException("数据已过期，请重新上传");
        }
        if (excelUploadInfo.getErrorList() != null && excelUploadInfo.getErrorList().size() > 0) {
            throw new BadRequestException("数据校验失败，请修改后重新上传");
        }
        List<ContractMainData> list = excelUploadInfo.getData();
        try{
            this.saveBatch(list);
            ExcelUploadInfo.deleteRedisData(redisTemplate,redisPrefix,fileId);
        }catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestException("保存失败\n" + e);
        }

    }

    @Override
    public void cancelUpload(String fileId) {
        ExcelUploadInfo.deleteRedisData(redisTemplate,redisPrefix,fileId);
    }

    @Override
    public void downloadErrorInfo(String fileId,HttpServletResponse response) throws IOException {
        ExcelUploadInfo.download(redisTemplate,redisPrefix,fileId,response);
    }

    @Override
    public void deleteByCondition(ContractMainDataQueryCriteria criteria) {
        try{
            this.baseMapper.delete(criteria.getQueryWrapper());
        }catch (Exception e){
            e.printStackTrace();
            throw new BadRequestException("删除失败\n" + e);
        }
    }

}