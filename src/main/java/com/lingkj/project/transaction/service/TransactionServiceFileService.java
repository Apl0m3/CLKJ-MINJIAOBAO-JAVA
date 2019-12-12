package com.lingkj.project.transaction.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.lingkj.common.utils.PageUtils;

import com.lingkj.project.transaction.entity.TransactionServiceFile;

import java.util.Map;

/**
 * 
 *
 * @author chenyongsong
 * @date 2019-10-21 11:47:26
 */
public interface TransactionServiceFileService extends IService<TransactionServiceFile> {

    PageUtils queryPage(Map<String, Object> params);
}

