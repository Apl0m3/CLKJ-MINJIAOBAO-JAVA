package com.lingkj.project.operation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.operation.dto.ApiOperatePaymentDto;
import com.lingkj.project.operation.entity.OperatePaymentMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
public interface PaymentMethodService extends IService<OperatePaymentMethod> {

    PageUtils queryPage(Map<String, Object> params);

    void saveOrUpdate(OperatePaymentMethod operatePaymentMethod, Long sysUserId, HttpServletRequest request);

    void disableEnable(Long id);

    void updateStatusByIds(List<Long> asList);

    OperatePaymentMethod selectByType(Integer type);

    List<ApiOperatePaymentDto> selectApiList();

    /**
     * 查询信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/17 9:13
     * @param id
     * @return com.lingkj.project.operation.entity.OperatePaymentMethod
     */
    OperatePaymentMethod queryInfo(Long id);

    /**
     * 更新信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/17 9:23
     * @param operatePaymentMethod
     * @return void
     */
    void updateInfo(OperatePaymentMethod operatePaymentMethod);
}

