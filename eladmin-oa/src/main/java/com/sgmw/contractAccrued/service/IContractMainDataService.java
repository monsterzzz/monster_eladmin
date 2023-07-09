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
package com.sgmw.contractAccrued.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sgmw.common.domain.ExcelUploadInfo;
import com.sgmw.contractAccrued.domain.ContractMainData;
import com.sgmw.contractAccrued.service.vo.ContractMainDataVo;
import com.sgmw.contractAccrued.service.params.ContractMainDataQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @description 服务接口
* @author monster
* @date 2023-07-08
**/
public interface IContractMainDataService extends IService<ContractMainData> {


    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(ContractMainDataQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<ContractMainDataVo>
    */
    List<ContractMainDataVo> queryAll(ContractMainDataQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param ids ID
     * @return ContractMainDataVo
     */
    List<ContractMainData> findByIds(Integer[] ids);

    /**
    * 创建或编辑
    * @param resources /
    * @return ContractMainDataVo
    */
    ContractMainDataVo createOrUpdate(ContractMainData resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Integer[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<ContractMainDataVo> all, HttpServletResponse response) throws IOException;

    /**
     * 上传文件
     * @param file 文件
     * @throws IOException /
     */
    ExcelUploadInfo<ContractMainData> upload(MultipartFile file) throws IOException;

    /**
     * 保存文件
     * @param fileId 文件Id
     */
    void saveUpload(String fileId);

    /**
     * 取消上传
     * @param fileId 文件Id
     */
    void cancelUpload(String fileId);

    /**
     * 下载错误信息
     * @param fileId 文件Id
     */

    void downloadErrorInfo(String fileId,HttpServletResponse response) throws IOException;

    /**
     * 条件批量删除
     * @param criteria 删除参数
     */
    void deleteByCondition(ContractMainDataQueryCriteria criteria);


}