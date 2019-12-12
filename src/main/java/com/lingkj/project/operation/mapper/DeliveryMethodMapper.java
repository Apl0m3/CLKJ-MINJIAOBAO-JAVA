package com.lingkj.project.operation.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.operation.entity.OperateDeliveryMethod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface DeliveryMethodMapper extends BaseMapper<OperateDeliveryMethod> {

    void updateStatusByIds(List<Long> asList);
}
