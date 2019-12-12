package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.api.transaction.dto.ApiTransactionServiceAndFileDto;
import com.lingkj.project.transaction.entity.TransactionServiceApplication;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 售后  申请表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
public interface TransactionServiceApplicationService extends IService<TransactionServiceApplication> {

    PageUtils queryPage(Map<String, Object> params);


    /**
     * 查询实体详细信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/21 14:30
     * @param id
     * @return com.lingkj.project.transaction.entity.TransactionServiceApplication
     */
    TransactionServiceApplication getById(Long id);

    TransactionServiceApplication getByTrCommodityId(Long TrCommodityId);

    TransactionServiceApplication getByTrCommodityIdAndUserId(Long userId,Long TrCommodityId);

    /**
     *
     * @author XXX <XXX@163.com>
     * @date 2019/10/21 16:18
     * @param
     * @return
     */
    void removeById(Long id);

    void saveService(ApiTransactionServiceAndFileDto apiTransactionServiceAndFileDto,Long userId,HttpServletRequest request);

    void updateInfo(TransactionServiceApplication entity, HttpServletRequest request);


}

