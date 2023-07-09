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
package com.sgmw.contractAccrued.service.vo;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author monster
* @date 2023-07-08
**/
@Data
public class ContractMainDataVo implements Serializable {

    /** ID */
    private Integer id;

    /** 基地 */
    private String base;

    /** 费用属性 */
    private String costAttribute;

    /** 合同编号 */
    private String contractNumber;

    /** 合同起 */
    private String contractStart;

    /** 合同止 */
    private String contractEnd;

    /** 采购员 */
    private String purchaser;

    /** 供应商代码 */
    private String supplierCode;

    /** 供应商名称 */
    private String supplierName;

    /** 业务内容 */
    private String businessContent;

    /** 成本中心 */
    private String costCenter;

    /** 科目代码 */
    private String accountCode;

    /** 科目描述 */
    private String accountDescription;

    /** PR号 */
    private String prNumber;

    /** 结算方式 */
    private String settlementMethod;

    /** 合同类别 */
    private String contractType;

    /** 是否延期Y/N */
    private String delayStatus;


}