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
package com.sgmw.contractAccrued.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.sgmw.contractAccrued.service.vo.ContractMainDataVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.io.Serializable;

/**
* @description /
* @author monster
* @date 2023-07-08
**/

@Data
@TableName("oa_contract_accrued_contract_main_data")
public class ContractMainData implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    @ExcelIgnore
    private Integer id;

    @TableField("`base`")
    @ApiModelProperty(value = "基地")
    private String base;

    @TableField("`cost_attribute`")
    @ApiModelProperty(value = "费用属性")
    private String costAttribute;

    @TableField("`contract_number`")
    @ApiModelProperty(value = "合同编号")
    private String contractNumber;

    @TableField("`contract_start`")
    @ApiModelProperty(value = "合同起")
    private String contractStart;

    @TableField("`contract_end`")
    @ApiModelProperty(value = "合同止")
    private String contractEnd;

    @TableField("`purchaser`")
    @ApiModelProperty(value = "采购员")
    private String purchaser;

    @TableField("`supplier_code`")
    @ApiModelProperty(value = "供应商代码")
    private String supplierCode;

    @TableField("`supplier_name`")
    @ApiModelProperty(value = "供应商名称")
    private String supplierName;

    @TableField("`business_content`")
    @ApiModelProperty(value = "业务内容")
    private String businessContent;

    @TableField("`cost_center`")
    @ApiModelProperty(value = "成本中心")
    private String costCenter;

    @TableField("`account_code`")
    @ApiModelProperty(value = "科目代码")
    private String accountCode;

    @TableField("`account_description`")
    @ApiModelProperty(value = "科目描述")
    private String accountDescription;

    @TableField("`pr_number`")
    @ApiModelProperty(value = "PR号")
    private String prNumber;

    @TableField("`settlement_method`")
    @ApiModelProperty(value = "结算方式")
    private String settlementMethod;

    @TableField("`contract_type`")
    @ApiModelProperty(value = "合同类别")
    private String contractType;

    @TableField("`delay_status`")
    @ApiModelProperty(value = "是否延期Y/N")
    private String delayStatus;

    public void copy(ContractMainData source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public ContractMainDataVo toVo(){
        ContractMainDataVo contractMainDataVo = new ContractMainDataVo();
        BeanUtil.copyProperties(this, contractMainDataVo);
        return contractMainDataVo;
    }
}
