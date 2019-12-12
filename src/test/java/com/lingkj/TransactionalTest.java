package com.lingkj;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lingkj.project.sys.entity.SysUserEntity;
import com.lingkj.project.sys.service.SysUserService;
import com.lingkj.service.DataSourceTestService;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 事务回滚测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionalTest {
    @Autowired
    private SysUserService sysUserService;

    @Test
    public void test(){
        //数据源1
        SysUserEntity user = new SysUserEntity();
        user.setUserId(1L);
        user.setEmail("111@00.com");
        sysUserService.update(new QueryWrapper<>());
    }


}
