package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.dto.DiscountDto;
import com.lingkj.project.user.entity.UserMemberApplication;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 用户分销会员申请
 *
 * @author chenyongsong
 * @date 2019-08-16 09:31:05
 */
@Mapper
public interface UserMemberApplicationMapper extends BaseMapper<UserMemberApplication> {

    UserMemberApplication selectByUserId(Long userId);


    Map<String,String> operateType(@Param("typeId") int typeId);

    int selectCountById(@Param("userId") Long userId);
    List<DiscountDto> getTypeList();

    List<DiscountDto> getOwnDiscountList(@Param("userId") Long userId);

    void saveOwnDiscount(List<DiscountDto> discountDtoList);

    void updateDiscount(List<DiscountDto> discountDtoList);

}
