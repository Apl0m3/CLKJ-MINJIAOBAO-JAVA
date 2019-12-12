/**
 * Copyright 2018 辰领科技 lingkj.com
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.lingkj.project.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.project.sys.dao.SysLogDao;
import com.lingkj.project.sys.entity.SysLogEntity;
import com.lingkj.project.sys.service.SysLogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogDao, SysLogEntity> implements SysLogService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) throws Exception{
        String key = (String)params.get("key");
        params.put("sidx","create_date");
        params.put("order","DESC");
        IPage<SysLogEntity> page;
        page = this.page(
            new Query<SysLogEntity>(params).getPage(),
            new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
        );
        if(page.getRecords()==null || page.getRecords().size()==0){
            params.put("page",1);
            page = this.page(
                    new Query<SysLogEntity>(params).getPage(),
                    new QueryWrapper<SysLogEntity>().like(StringUtils.isNotBlank(key),"username", key)
            );
        }
        return new PageUtils(page);
    }
}
