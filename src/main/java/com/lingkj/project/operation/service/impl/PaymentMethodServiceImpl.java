package com.lingkj.project.operation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.DateUtils;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.project.api.commodity.dto.ApiIntegralCommodityDto;
import com.lingkj.project.api.operation.dto.ApiOperatePaymentDto;
import com.lingkj.project.operation.entity.OperatePaymentMethod;
import com.lingkj.project.operation.mapper.PaymentMethodMapper;
import com.lingkj.project.operation.service.PaymentMethodService;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class PaymentMethodServiceImpl extends ServiceImpl<PaymentMethodMapper, OperatePaymentMethod> implements PaymentMethodService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MessageUtils messageUtils;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //查询总记录数
        int totalCount = this.count(new QueryWrapper<OperatePaymentMethod>().eq(true, "status", 0));
        //获得分页数据
        String curPage = (String) params.get("curPage");
        String pageSize = (String) params.get("pageSize");
        int limit = (Integer.valueOf(curPage) - 1) * Integer.valueOf(pageSize);
        List<OperatePaymentMethod> record = this.baseMapper.queryOperatePaymentMethodList(limit, Integer.valueOf(pageSize));
        for (OperatePaymentMethod methodEntity : record) {
            SysUserEntity entity = sysUserService.getById(methodEntity.getCreateSysUserId());
            methodEntity.setCreateSysUserName(entity.getUsername());
            if (methodEntity.getUpdateSysUserId() != null) {
                    entity = sysUserService.getById(methodEntity.getUpdateSysUserId());
                    methodEntity.setCreateSysUserName(entity.getUsername());
            }
        }
        return new PageUtils(record, totalCount, Integer.valueOf(pageSize), Integer.valueOf(curPage));
    }


    @Override
    public void saveOrUpdate(OperatePaymentMethod operatePaymentMethod, Long sysUserId, HttpServletRequest request) {
        Integer size = baseMapper.countByPaymentType(operatePaymentMethod.getPaymentType());
        if (size > 0) {
            throw new RRException(messageUtils.getMessage("manage.payment.method.error", request));
        }
        Date current = DateUtils.current();
        if (operatePaymentMethod.getId() == null) {
            operatePaymentMethod.setCreateSysUserId(sysUserId);
            operatePaymentMethod.setCreateTime(current);
            this.save(operatePaymentMethod);
        } else {
            operatePaymentMethod.setUpdateSysUserId(sysUserId);
            operatePaymentMethod.setUpdateTime(current);
            this.updateById(operatePaymentMethod);
        }

    }

    @Override
    public void disableEnable(Long id) {
        OperatePaymentMethod operatePaymentMethod = this.getById(id);
        if (operatePaymentMethod.getStatus() == 0) {
            operatePaymentMethod.setStatus(1);
        } else if (operatePaymentMethod.getStatus() == 1) {
            operatePaymentMethod.setStatus(0);
        }
        this.updateById(operatePaymentMethod);
    }

    @Override
    public void updateStatusByIds(List<Long> asList) {
        baseMapper.updateStatusByIds(asList);
    }

    @Override
    public OperatePaymentMethod selectByType(Integer type) {
        return baseMapper.selectByType(type);
    }


    @Override
    public List<ApiOperatePaymentDto> selectApiList() {
        return baseMapper.selectApiList();
    }

    @Override
    public OperatePaymentMethod queryInfo(Long id) {

        return baseMapper.queryInfo(id);
    }

    @Override
    public void updateInfo(OperatePaymentMethod operatePaymentMethod) {
        baseMapper.updateInfo(operatePaymentMethod);
    }

}
