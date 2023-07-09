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
package com.sgmw.contractAccrued.service.params;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sgmw.contractAccrued.domain.ContractMainData;
import lombok.Data;
import me.zhengjie.annotation.Query;

import java.util.List;

/**
* @website https://eladmin.vip
* @author monster
* @date 2023-07-08
**/
@Data
public class ContractMainDataQueryCriteria {

    private List<String> base;

    private List<String> costAttribute;

    private String contractNumber;

    private String contractStart;

    private String contractEnd;

    private String costCenter;

    private String accountCode;

    private String prNumber;

    public QueryWrapper<ContractMainData> getQueryWrapper(){
        QueryWrapper<ContractMainData> queryWrapper = new QueryWrapper<>();
        if (this.base != null && this.base.size() > 0) {
            queryWrapper.in("base", this.base);
        }
        if (this.costAttribute != null && this.costAttribute.size() > 0) {
            queryWrapper.in("cost_attribute", this.costAttribute);
        }
        if (this.contractNumber != null && !"".equals(this.contractNumber)) {
            queryWrapper.eq("contract_number", this.contractNumber);
        }
        if (this.contractStart != null && !"".equals(this.contractStart)) {
            queryWrapper.ge("contract_start", this.contractStart);
        }
        if (this.contractEnd != null && !"".equals(this.contractEnd)) {
            queryWrapper.le("contract_end", this.contractEnd);
        }
        if (this.costCenter != null && !"".equals(this.costCenter)) {
            queryWrapper.eq("cost_center", this.costCenter);
        }
        if (this.accountCode != null && !"".equals(this.accountCode)) {
            queryWrapper.eq("account_code", this.accountCode);
        }
        if (this.prNumber != null && !"".equals(this.prNumber)) {
            queryWrapper.eq("pr_number", this.prNumber);
        }
        return queryWrapper;
    }

}