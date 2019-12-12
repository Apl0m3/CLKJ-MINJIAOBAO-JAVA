package com.lingkj.project.user.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.entity.UserBank;
import com.lingkj.project.user.entity.UserDesignerApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
@Mapper
public interface UserDesignerApplicationMapper extends BaseMapper<UserDesignerApplication> {

    List<CommissionDto> getTypeList();
    List<CommissionDto> getCommissionDto();



   void insertCommission(List<CommissionDto> commissionDto);


   /**
    *  通过申请id 和用户id 查询开户行信息
    * @author XXX <XXX@163.com>
    * @date 2019/10/29 10:38
    * @param userId 用户id
    * @param applicationId 申请id
    * @return com.lingkj.project.user.entity.UserBank
    */
    UserBank queryUserBankByApplicationIdAndUserId(@Param("userId") Long userId, @Param("applicationId")Long applicationId);

}
