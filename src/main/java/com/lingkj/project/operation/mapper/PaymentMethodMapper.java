package com.lingkj.project.operation.mapper;

import com.lingkj.project.api.operation.dto.ApiOperatePaymentDto;
import com.lingkj.project.operation.entity.OperatePaymentMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author chenyongsong
 * @date 2019-06-26 16:10:26
 */
@Mapper
public interface PaymentMethodMapper extends BaseMapper<OperatePaymentMethod> {
    /**
     * 判断payment type 重复
     *
     * @param paymentType
     * @return
     */
    Integer countByPaymentType(Integer paymentType);

    List<OperatePaymentMethod> queryOperatePaymentMethodList(@Param("limit") Integer limit,@Param("pageSize") Integer pageSize );
    void updateStatusByIds(List<Long> asList);

    OperatePaymentMethod selectByType(Integer type);
    /**
     * api 支付方式
     *
     * @return
     */
    List<ApiOperatePaymentDto> selectApiList();


    /**
     * 查询信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/17 9:24
     * @param id
     * @return com.lingkj.project.operation.entity.OperatePaymentMethod
     */
    OperatePaymentMethod queryInfo(@Param("id") Long id);

    /**
     * 更新信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/17 9:26
     * @param [operatePaymentMethod]
     * @return void
     */
    void updateInfo(OperatePaymentMethod operatePaymentMethod);


}
