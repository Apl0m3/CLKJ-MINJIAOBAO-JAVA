package com.lingkj.common.bean.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Page
 *
 * @author chen yongsong
 * @className Page
 * @date 2019/7/5 18:44
 */
@ApiModel("分页")
@Data
public class Page {
    @ApiModelProperty("当前页")
    private Integer page = 1;
    @ApiModelProperty("分页大小")
    private Integer pageSize = 10;

    private Integer limit;
    @ApiModelProperty("关键字")
    private String key;

    public Integer getLimit() {
        return (page - 1) * pageSize;
    }

    public Page() {
    }

    public Page(Integer page, Integer pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }
}
