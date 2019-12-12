package com.lingkj.project.operation.mapper;

import com.lingkj.project.api.operation.dto.WorkUsDto;
import com.lingkj.project.operation.entity.OperatePartner;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 合作伙伴
 *
 * @author chenyongsong
 * @date 2019-09-20 14:44:16
 */
@Mapper
public interface OperatePartnerMapper extends BaseMapper<OperatePartner> {

    /**
     * UI端接口
     * @return 所有合作伙伴dto
     */
    WorkUsDto queryPartnerList(@Param("name") String name);
}
