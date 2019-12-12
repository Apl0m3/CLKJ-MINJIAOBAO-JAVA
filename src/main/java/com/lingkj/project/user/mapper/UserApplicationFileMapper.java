package com.lingkj.project.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lingkj.project.user.entity.UserApplicationFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 设计师 申请 上传 案例url
 *
 * @author chenyongsong
 * @date 2019-09-12 11:54:40
 */
@Mapper
public interface UserApplicationFileMapper extends BaseMapper<UserApplicationFile> {
    List<UserApplicationFile> getUserApplicationFile (@Param("userId") Long userId, @Param("start")Integer start, @Param("end") Integer end);
     Integer queryUserApplicationFileCount(@Param("userId") Long userId);
     void deleteUserFile(@Param("applicationId") Long applicationId,@Param("type") Integer type);
}
