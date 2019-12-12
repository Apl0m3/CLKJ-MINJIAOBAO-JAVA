package com.lingkj.project.commodity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.exception.RRException;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.api.commodity.dto.ApiCommodityCommentAndFileDto;
import com.lingkj.project.api.commodity.dto.ApiCommodityCommentDto;
import com.lingkj.project.commodity.entity.Commodity;
import com.lingkj.project.commodity.entity.CommodityComment;
import com.lingkj.project.commodity.entity.CommodityCommentFile;
import com.lingkj.project.commodity.mapper.CommodityCommentMapper;
import com.lingkj.project.commodity.service.CommodityCommentFileService;
import com.lingkj.project.commodity.service.CommodityCommentService;
import com.lingkj.project.commodity.service.CommodityService;
import com.lingkj.project.transaction.entity.TransactionCommodity;
import com.lingkj.project.transaction.entity.TransactionCommodityStatusLog;
import com.lingkj.project.transaction.service.TransactionCommodityService;
import com.lingkj.project.transaction.service.TransactionCommodityStatusLogService;
import com.lingkj.project.transaction.service.TransactionRecordService;
import com.lingkj.project.user.entity.User;
import com.lingkj.project.user.service.AdminUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Administrator
 */
@Service
public class CommodityCommentServiceImpl extends ServiceImpl<CommodityCommentMapper, CommodityComment> implements CommodityCommentService {
    @Autowired
    private CommodityCommentFileService commodityCommentFileService;

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private CommodityService commodityService;
    @Autowired
    private TransactionCommodityService transactionCommodityService;
    @Autowired
    private TransactionCommodityStatusLogService transactionCommodityStatusLogService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        params.put("sidx", "create_time");
        params.put("order", "DESC");
        QueryWrapper wrapper = new QueryWrapper();
        String transactionId = (String) params.get("transactionId");
        if (StringUtils.isNotBlank(transactionId)) {
            wrapper.like("transaction_id", transactionId);
        }
        wrapper.orderByDesc("create_time");
        String userName = (String) params.get("userName");
        if (StringUtils.isNotBlank(userName)) {
            User user = adminUserService.queryByMobile(userName);
            if (user == null) {
                throw new RRException("请检查用户手机号，该用户不存在");
            }
            wrapper.eq("user_id", user.getId());
        }
        IPage<CommodityComment> page = this.page(
                new Query<CommodityComment>(params).getPage(),
                wrapper
        );
        for (CommodityComment commodityComment : page.getRecords()) {
            if(commodityComment.getUserId() != null){
                User user = adminUserService.getById(commodityComment.getUserId());
                if(user != null){
                    commodityComment.setUserName(user.getUserName());
                    Commodity commodity = commodityService.getById(commodityComment.getCommodityId());
                    commodityComment.setCommodityName(commodity.getName());
                }
            }
        }

        return new PageUtils(page);
    }


    @Override
    public PageUtils queryPageApi(com.lingkj.common.bean.entity.Page page, Long commodityId) {
        Integer totalCount = this.baseMapper.queryCommentCount(commodityId);
        List<ApiCommodityCommentDto> list = this.baseMapper.queryComment(page.getPageSize(), page.getLimit(), commodityId);
        for (ApiCommodityCommentDto commentDto : list) {
            List<String> files = this.commodityCommentFileService.selectImagesById(commentDto.getId());
            commentDto.setFiles(files);
        }
        return new PageUtils(list, totalCount, page.getPageSize(), page.getPage());
    }


    @Transactional
    @Override
    public void saveComment(ApiCommodityCommentAndFileDto commentAndFileDto, Long userId) {
            CommodityComment comment=new CommodityComment();
            comment.setCommodityId(commentAndFileDto.getCommodityId());
            comment.setTransactionId(commentAndFileDto.getTransactionId());
            comment.setUserId(userId);
            comment.setComment(commentAndFileDto.getComment());
            comment.setCreateTime(new Date());
            this.save(comment);
        List<CommodityCommentFile> list = commentAndFileDto.getList();
        if(list!=null||list.size()>0){
            for (CommodityCommentFile file :list) {
                file.setCommodityCommentId(comment.getId());
                file.setCreateTime(new Date());
                commodityCommentFileService.save(file);
            }
        }
        TransactionCommodity byId = transactionCommodityService.selectByIdForUpdate(commentAndFileDto.getTransactionCommodityId());
        byId.setStatus(9);
        transactionCommodityService.updateById(byId);
        transactionCommodityStatusLogService.saveStatusLog(userId,byId.getId(),byId.getRecordId(),commentAndFileDto.getUserType(),9,null);

    }


}
