package com.lingkj.project.manage.sys.controller;

import java.util.Arrays;
import java.util.Map;

import com.lingkj.common.utils.RedisUtils;
import com.lingkj.project.sys.Dto.UpdateLogisticsSmsNumRespDto;
import com.lingkj.project.sys.entity.LogisticsSmsNum;
import com.lingkj.project.sys.service.LogisticsSmsNumService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;

import javax.servlet.http.HttpServletRequest;


/**
 *
 *
 * @author chenyongsong
 * @date 2019-08-03 09:17:56
 */
@RestController
@RequestMapping("/manage/logisticssmsnum")
public class LogisticsSmsNumController {
    @Autowired
    private LogisticsSmsNumService logisticsSmsNumService;

    @Autowired
    private RedisUtils redisUtils;
    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("commodity:logisticssmsnum:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = logisticsSmsNumService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("commodity:logisticssmsnum:info")
    public R info(@PathVariable("id") Integer id){
			LogisticsSmsNum logisticsSmsNum = logisticsSmsNumService.getById(id) ;

        return R.ok().put("logisticsSmsNum", logisticsSmsNum);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("commodity:logisticssmsnum:save")
    public R save(@RequestBody LogisticsSmsNum logisticsSmsNum){
			logisticsSmsNumService.save(logisticsSmsNum) ;

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("commodity:logisticssmsnum:update")
    public R update(@RequestBody LogisticsSmsNum logisticsSmsNum){
			logisticsSmsNumService.updateById(logisticsSmsNum);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("commodity:logisticssmsnum:delete")
    public R delete(@RequestBody Integer[] ids){
			logisticsSmsNumService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }
    /**
     * 改变短信/物流数量
     */
    @PostMapping("/updateLogisticsSmsNum")
    @RequiresPermissions("commodity:logisticssmsnum:updateLogisticsSmsNum")
    public R updateLogisticsSmsNum(@RequestBody UpdateLogisticsSmsNumRespDto updateLogisticsSmsNumRespDto, HttpServletRequest request){
        logisticsSmsNumService.updateLogisticsSmsNum(updateLogisticsSmsNumRespDto,request);
        return R.ok();
    }

    @GetMapping("getLogisticsSmsNum")
    public R getLogisticsSmsNum(){
        String mobile="logistics_sms_num_1";
        Integer type=0;
        /*if(!redisUtils.exists(mobile)){
            type=1;
            return R.ok().put("type",type);
        }
        String sms="logistics_sms_num_2";
        if(!redisUtils.exists(sms)){
            type=2;
            return R.ok().put("type",type);
        }
        String mobileSum=redisUtils.get(mobile);
        String smsSum=redisUtils.get(sms);
        if(Integer.valueOf(smsSum)<50 || Integer.valueOf(mobileSum)<50 ){
            type=3;
            return R.ok().put("type",type);
        }*/
        return R.ok().put("type",type);
    }

}
