package com.lingkj.project.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.Query;
import com.lingkj.common.utils.RedisUtils;
import com.lingkj.common.utils.i18n.MessageUtils;
import com.lingkj.common.validator.Assert;
import com.lingkj.project.sys.Dto.UpdateLogisticsSmsNumRespDto;
import com.lingkj.project.sys.dao.LogisticsSmsNumMapper;
import com.lingkj.project.sys.entity.LogisticsSmsNum;
import com.lingkj.project.sys.service.LogisticsSmsNumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-03 09:17:56
 */
@Service
public class LogisticsSmsNumServiceImpl extends ServiceImpl<LogisticsSmsNumMapper, LogisticsSmsNum> implements LogisticsSmsNumService {

    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private MessageUtils messageUtils;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogisticsSmsNum> page = this.page(
                new Query<LogisticsSmsNum>(params).getPage(),
                new QueryWrapper<LogisticsSmsNum>()
        );

        for (LogisticsSmsNum logisticsSmsNum:page.getRecords()){
            String redisKey="logistics_sms_num_"+logisticsSmsNum.getId();
            if(redisUtils.exists(redisKey)){
                String remainingNumber=redisUtils.get(redisKey);
                logisticsSmsNum.setRemainingNumber(Integer.valueOf(remainingNumber));
            }
        }

        return new PageUtils(page);
    }

    @Override
    public void updateLogisticsSmsNum(UpdateLogisticsSmsNumRespDto updateLogisticsSmsNumRespDto, HttpServletRequest request) {

        //查询记录
        LogisticsSmsNum logisticsSmsNum=this.getById(updateLogisticsSmsNumRespDto.getId());
        Assert.isNull(logisticsSmsNum, messageUtils.getMessage("manage.logistics.sms.num.error", request));
        logisticsSmsNum.setNum(logisticsSmsNum.getNum()+updateLogisticsSmsNumRespDto.getNum());

        String redisKey="logistics_sms_num_"+logisticsSmsNum.getId();
        if(redisUtils.exists(redisKey)){
            redisUtils.incr(redisKey,updateLogisticsSmsNumRespDto.getNum().longValue());
        }else{
            logisticsSmsNum.setNum(updateLogisticsSmsNumRespDto.getNum());
            redisUtils.set(redisKey,updateLogisticsSmsNumRespDto.getNum());
        }
        this.updateById(logisticsSmsNum);





    }

}
