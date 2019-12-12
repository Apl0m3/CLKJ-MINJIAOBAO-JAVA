package com.lingkj.project.operation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.operation.entity.OperateDeliveryStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 配送员信息
 *
 * @author chenyongsong
 * @date 2019-07-19 11:59:54
 */
@Mapper
public interface OperateDeliveryStaffMapper extends BaseMapper<OperateDeliveryStaff> {

    void updateStatusBatchIds(@Param("asList") List<Long> asList);
}
