package com.lingkj.project.manage.operation. controller ;

import com.lingkj.common.utils.PageUtils;
import com.lingkj.common.utils.R;
import com.lingkj.project.manage.sys.controller.AbstractController;
import com.lingkj.project.message.entity.Message;
import com.lingkj.project.message.service.MessageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;




/**
 *
 *
 * @author chenyongsong
 *
 * @date 2019-06-26 16:10:26
 */
@RestController
@RequestMapping("/manage/message")
public class OperateMessageController extends AbstractController {
    @Autowired
    private MessageService messageService;

    /**
     * 列表
     */
    @GetMapping("/list")
   @RequiresPermissions("manage:message:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = messageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
   @RequiresPermissions("manage:message:info")
    public R info(@PathVariable("id") Long id){
			Message message = messageService.getById(id);

        return R.ok().put("message", message);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @RequiresPermissions("manage:message:save")
    public R save(Message message){
			messageService.addOrUpdate(message,getSysUserId());

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
   @RequiresPermissions("manage:message:update")
    public R update(Message message){
			messageService.addOrUpdate(message,getSysUserId());

        return R.ok();
    }

    /**
     * 删除
     */
     @PostMapping("/delete")
   @RequiresPermissions("manage:message:delete")
     public R delete(@RequestParam("ids") Long[] ids) {
			messageService.updateStatusByIds(Arrays.asList(ids));

        return R.ok();
    }

}
