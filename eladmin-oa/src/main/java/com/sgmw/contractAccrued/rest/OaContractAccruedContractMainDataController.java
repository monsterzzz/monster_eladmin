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
package com.sgmw.contractAccrued.rest;

import com.sgmw.common.domain.AjaxResult;
import com.sgmw.contractAccrued.domain.ContractMainData;
import com.sgmw.contractAccrued.service.IContractMainDataService;
import com.sgmw.contractAccrued.service.params.ContractMainDataQueryCriteria;
import me.zhengjie.annotation.Log;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author monster
* @date 2023-07-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "合同主数据管理")
@RequestMapping("/api/oaContractAccruedContractMainData")
public class OaContractAccruedContractMainDataController {

    @Resource
    private IContractMainDataService contractMainDataService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    public void exportOaContractAccruedContractMainData(HttpServletResponse response, ContractMainDataQueryCriteria criteria) throws IOException {
        contractMainDataService.download(contractMainDataService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询合同主数据")
    @ApiOperation("查询合同主数据")
    public AjaxResult queryOaContractAccruedContractMainData(ContractMainDataQueryCriteria criteria, Pageable pageable){
        return AjaxResult.success(contractMainDataService.queryAll(criteria,pageable));
    }

    @PostMapping
    @Log("新增或修改合同主数据")
    @ApiOperation("新增合同主数据")
    public AjaxResult createOaContractAccruedContractMainData(@Validated @RequestBody ContractMainData resources){
        return AjaxResult.success(contractMainDataService.createOrUpdate(resources));
    }


    @DeleteMapping
    @Log("删除合同主数据")
    @ApiOperation("删除合同主数据")
    public AjaxResult deleteOaContractAccruedContractMainData(@RequestBody Integer[] ids) {
        contractMainDataService.deleteAll(ids);
        return AjaxResult.success();
    }

    @PostMapping("/conditionDelete")
    @Log("条件删除合同主数据")
    @ApiOperation("删除合同主数据")
    public AjaxResult conditionDeleteOaContractAccruedContractMainData(@RequestBody ContractMainDataQueryCriteria criteria) {
        contractMainDataService.deleteByCondition(criteria);
        return AjaxResult.success();
    }

    @PostMapping("/upload")
    @Log("上传合同主数据文件")
    @ApiOperation("上传合同主数据文件")
    public AjaxResult uploadOaContractAccruedContractMainData(MultipartFile file) throws IOException {
        return AjaxResult.success(contractMainDataService.upload(file));
    }

    @PostMapping("/saveUpload/{fileId}")
    @Log("保存上传合同主数据文件")
    @ApiOperation("保存上传合同主数据文件")
    public AjaxResult saveUploadOaContractAccruedContractMainData(@PathVariable("fileId") String fileId) throws IOException {
        contractMainDataService.saveUpload(fileId);
        return AjaxResult.success();
    }

    @PostMapping("/cancelUpload/{fileId}")
    @Log("取消上传合同主数据文件")
    @ApiOperation("上传合同主数据文件")
    public AjaxResult cancelUploadOaContractAccruedContractMainData(@PathVariable("fileId") String fileId) throws IOException {
        contractMainDataService.cancelUpload(fileId);
        return AjaxResult.success();
    }

    @GetMapping("/downloadErrorMsg/{fileId}")
    @Log("下载上传合同主数据文件错误信息")
    @ApiOperation("下载上传合同主数据文件错误信息")
    public void downloadErrorMsgOaContractAccruedContractMainData(@PathVariable String fileId,HttpServletResponse response) throws IOException {
        contractMainDataService.downloadErrorInfo(fileId,response);
    }

}