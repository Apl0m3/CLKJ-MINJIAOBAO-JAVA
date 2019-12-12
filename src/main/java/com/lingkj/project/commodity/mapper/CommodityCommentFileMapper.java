package com.lingkj.project.commodity.mapper;

import com.lingkj.project.commodity.entity.CommodityCommentFile;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-07-04 09:50:39
 */
@Mapper
public interface CommodityCommentFileMapper extends BaseMapper<CommodityCommentFile> {

    List<String> selectImagesById(Long id);
}
