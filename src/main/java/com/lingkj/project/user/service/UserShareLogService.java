package com.lingkj.project.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.project.user.entity.UserShareLog;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 分享网站记录
 *
 * @author chenyongsong
 * @date 2019-09-23 16:03:51
 */
public interface UserShareLogService extends IService<UserShareLog> {

    PageUtils queryPage(Map<String, Object> params);

    UserShareLog selectByIdAndUrl(Long userId,String url);

    void  userShare(Long userId, String url, HttpServletRequest request);
}

