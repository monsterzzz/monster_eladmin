<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="ID">
            <el-input v-model="form.id" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="总部">
            <el-input v-model="form.headquarters" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="费用属性">
            <el-input v-model="form.costAttribute" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="合同编号">
            <el-input v-model="form.contractNumber" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="合同起">
            <el-input v-model="form.contractStart" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="合同止">
            <el-input v-model="form.contractEnd" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="采购员">
            <el-input v-model="form.purchaser" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商代码">
            <el-input v-model="form.supplierCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="供应商名称">
            <el-input v-model="form.supplierName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="业务内容">
            <el-input v-model="form.businessContent" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="成本中心">
            <el-input v-model="form.costCenter" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="科目代码">
            <el-input v-model="form.accountCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="科目描述">
            <el-input v-model="form.accountDescription" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="PR号">
            <el-input v-model="form.prNumber" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="结算方式">
            <el-input v-model="form.settlementMethod" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="合同类别">
            <el-input v-model="form.contractType" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="是否延期Y/N">
            <el-input v-model="form.delayStatus" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.status.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column prop="id" label="ID" />
        <el-table-column prop="headquarters" label="总部" />
        <el-table-column prop="costAttribute" label="费用属性" />
        <el-table-column prop="contractNumber" label="合同编号" />
        <el-table-column prop="contractStart" label="合同起" />
        <el-table-column prop="contractEnd" label="合同止" />
        <el-table-column prop="purchaser" label="采购员" />
        <el-table-column prop="supplierCode" label="供应商代码" />
        <el-table-column prop="supplierName" label="供应商名称" />
        <el-table-column prop="businessContent" label="业务内容" />
        <el-table-column prop="costCenter" label="成本中心" />
        <el-table-column prop="accountCode" label="科目代码" />
        <el-table-column prop="accountDescription" label="科目描述" />
        <el-table-column prop="prNumber" label="PR号" />
        <el-table-column prop="settlementMethod" label="结算方式" />
        <el-table-column prop="contractType" label="合同类别" />
        <el-table-column prop="delayStatus" label="是否延期Y/N" />
        <el-table-column v-if="checkPer(['admin','oaContractAccruedContractsMainData:edit','oaContractAccruedContractsMainData:del'])" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation
              :data="scope.row"
              :permission="permission"
            />
          </template>
        </el-table-column>
      </el>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudOaContractAccruedContractsMainData from '@/api/oaContractAccruedContractsMainData'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

const defaultForm = { id: null, headquarters: null, costAttribute: null, contractNumber: null, contractStart: null, contractEnd: null, purchaser: null, supplierCode: null, supplierName: null, businessContent: null, costCenter: null, accountCode: null, accountDescription: null, prNumber: null, settlementMethod: null, contractType: null, delayStatus: null }
export default {
  name: 'OaContractAccruedContractsMainData',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(), header(), form(defaultForm), crud()],
  cruds() {
    return CRUD({ title: '合同主数据', url: 'api/oaContractAccruedContractsMainData', idField: 'id', sort: 'id,desc', crudMethod: { ...crudOaContractAccruedContractsMainData }})
  },
  data() {
    return {
      permission: {
        add: ['admin', 'oaContractAccruedContractsMainData:add'],
        edit: ['admin', 'oaContractAccruedContractsMainData:edit'],
        del: ['admin', 'oaContractAccruedContractsMainData:del']
      },
      rules: {
      }    }
  },
  methods: {
    // 钩子：在获取表格数据之前执行，false 则代表不获取数据
    [CRUD.HOOK.beforeRefresh]() {
      return true
    }
  }
}
</script>

<style scoped>

</style>
