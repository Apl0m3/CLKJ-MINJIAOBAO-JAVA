package com.lingkj.project.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.SupplierCommissionDto;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.entity.UserSupplierApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 用户供应商 申请  表
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@Mapper
public interface UserSupplierApplicationMapper extends BaseMapper<UserSupplierApplication> {

    /**
     * 通过id 查询产品type_ids
     * @param  id
     */
    String getProIds(@Param("id") long id);

    /**
     * 通过产品type_ids 封装CommissionDto实体 没有带Commission字段
     */
    List<CommissionDto> getTypeList(List<Integer> ids);

//    void insertCommission(List<CommissionDto> commissionDto);
    /**
     * 通过产品type_ids 封装CommissionDto实体 带Commission字段
     */
    List<CommissionDto> getTypeListCommission(List<Integer> ids);


    UserBank querySupplyBankByApplicationIdAndUserId(@Param("userId") Long uid, @Param("applicationId") Long aid);

}
