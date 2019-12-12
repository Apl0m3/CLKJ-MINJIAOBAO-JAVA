package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.dto.CommissionDto;
import com.lingkj.project.user.dto.UserDesignerApplicationDto;
import com.lingkj.project.user.entity.UserDesignerApplication;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * @author chenyongsong
 * @date 2019-09-12 09:17:38
 */
public interface UserDesignerApplicationService extends IService<UserDesignerApplication> {

    PageUtils queryPage(Map<String, Object> params);

    void approval(UserDesignerApplication userDesignerApplication,HttpServletRequest request);

    UserDesignerApplication getUserDesignerApplicationOne(Long userId);

    /**
     * 获取商品分类
     */
    List<CommissionDto> getTypeList();
    List<CommissionDto> getCommissionDto();

    void insertCom(List<CommissionDto> commissionDto);

    Integer getUserDesignerApplicationCount(Long userId);


    /**
     * 查询信息
     * @author XXX <XXX@163.com>
     * @date 2019/10/24 14:47
     * @param id
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    Map<String,Object> queryInfo(Long id);

     void  saveDesigner(UserDesignerApplicationDto userDesignerApplicationDto, Long userId, HttpServletRequest request);
}

