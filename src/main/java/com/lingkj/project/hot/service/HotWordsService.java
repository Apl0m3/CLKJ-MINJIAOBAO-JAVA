package com.lingkj.project.hot.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.lingkj.common.utils.PageUtils;

import com.lingkj.project.hot.entity.HotWords;

import java.util.Map;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-11-11 09:52:12
 */
public interface HotWordsService extends IService<HotWords> {

    PageUtils queryPage(Map<String, Object> params);

    HotWords  selectName(String name);
}

