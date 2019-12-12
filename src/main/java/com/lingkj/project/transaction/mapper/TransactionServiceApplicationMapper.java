package com.lingkj.project.transaction.mapper;

import com.lingkj.project.transaction.entity.TransactionServiceApplication;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.transaction.entity.TransactionServiceFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 售后  申请表
 *
 * @author chenyongsong
 * @date 2019-09-20 14:02:25
 */
@Mapper
public interface TransactionServiceApplicationMapper extends BaseMapper<TransactionServiceApplication> {

    /**
     * 查询退换货上传的图片url
     * @author XXX <XXX@163.com>
     * @date 2019/10/28 14:38
     * @param id 售后服务事件id
     * @return java.util.List<com.lingkj.project.transaction.entity.TransactionServiceFile>
     */
    List<TransactionServiceFile> queryFileList(Long id);

    /**
     * 查询
     * @param id
     * @return
     */
    TransactionServiceApplication selectByIdForUpdate(Long id);
}
