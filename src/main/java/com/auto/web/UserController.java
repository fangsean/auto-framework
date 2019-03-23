package com.auto.web;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.auto.common.mertics.JProfile;
import com.auto.entity.User;
import com.auto.entity.enums.AgeEnum;
import com.auto.entity.enums.PhoneEnum;
import com.auto.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 代码生成器，参考源码测试用例：
 * <p>
 * /mybatis-plus/src/test/java/com/baomidou/mybatisplus/test/generator/MysqlGenerator.java
 */
@Scope(value ="prototype" )
@RestController
@RequestMapping("/user")
public class UserController /*extends ApiController*/ {

    @Autowired
    private IUserService userService;

    /**
     * <p>
     * 测试通用 Api Controller 逻辑
     * </p>
     * 测试地址：
     * http://localhost:8080/user/api
     * http://localhost:8080/user/api?test=mybatisplus
     */
    /*@GetMapping("/api")
    public ApiResult<String> testError(String test) {
        ApiAssert.notNull(ErrorCode.TEST, test);
        return success(test);
    }*/

    /**
     * http://localhost:8080/user/test
     */
    @GetMapping("/test")
    public Page<User> test() {
        JProfile.enter("/test");
        Page<User> userPage = userService.selectPage(new Page<User>(0, 12), null);
        JProfile.release();
        return userPage;
    }

    /**
     * AR 部分测试
     * http://localhost:8080/user/test1
     */
    @GetMapping("/test1")
    public Page<User> test1() {
        User user = new User("testAr", AgeEnum.ONE, 1);
        System.err.println("删除所有：" + userService.deleteAll());
        user.setRole(111L);
        user.setTestDate(new Date());
        user.setPhone(PhoneEnum.CMCC.getValue());
        userService.insert(user);
        System.err.println("查询插入结果：" + userService.selectById(user.getId()).toString());
        user.setName("mybatis-plus-ar");
        System.err.println("更新：" + userService.updateById(user));
        return userService.selectPage(new Page<User>(0, 12), null);
    }

    /**
     * 增删改查 CRUD
     * http://localhost:8080/user/test2
     */
    @GetMapping("/test2")
    public User test2() {
        JProfile.enter("/test2");
        System.err.println("删除一条数据：" + userService.deleteById(1L));
        System.err.println("deleteAll：" + userService.deleteAll());
        System.err.println("插入一条数据：" + userService.insert(new User(1L, "张三", AgeEnum.TWO, 1)));
        User user = new User("张三", AgeEnum.TWO, 1);
        boolean result = userService.insert(user);
        // 自动回写的ID
        Long id = user.getId();
        System.err.println("插入一条数据：" + result + ", 插入信息：" + user.toString());
        System.err.println("查询：" + userService.selectById(id).toString());
        System.err.println("更新一条数据：" + userService.updateById(new User(1L, "三毛", AgeEnum.ONE, 1)));
        for (int i = 0; i < 5; ++i) {
            userService.insert(new User(Long.valueOf(100 + i), "张三" + i, AgeEnum.ONE, 1));
        }
        Page<User> userListPage = userService.selectPage(new Page<User>(1, 5), new EntityWrapper<User>());
        System.err.println("total=" + userListPage.getTotal() + ", current list size=" + userListPage.getRecords().size());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        JProfile.release();
        return userService.selectById(1L);
    }

    /**
     * 插入 OR 修改
     * http://localhost:8080/user/test3
     */
    @GetMapping("/test3")
    public User test3() {
        User user = new User(1L, "王五", AgeEnum.ONE, 1);
        user.setPhone(PhoneEnum.CT.getValue());
        userService.insertOrUpdate(user);
        return userService.selectById(1L);
    }

    /**
     * http://localhost:8080/user/add
     */
    @GetMapping("/add")
    public Object addUser() {
        User user = new User("张三'特殊`符号", AgeEnum.TWO, 1);
        user.setPhone(PhoneEnum.CUCC.getValue());
        return userService.insert(user);
    }

    /**
     * http://localhost:8080/user/select_sql
     */
    @GetMapping("/select_sql")
    public Object getUserBySql() {
        return userService.selectListBySQL();
    }

    /**
     * <p>
     * 参数模式分页
     * </p>
     * <p>
     * 7、分页 size 一页显示数量  current 当前页码
     * 方式一：http://localhost:8080/user/page?size=1&current=1<br>
     * <p>
     * 集合模式，不进行分页直接返回所有结果集：
     * http://localhost:8080/user/page?listMode=true
     */
    @GetMapping("/page")
    public Page page(Page page, boolean listMode) {
        if (listMode) {
            // size 小于 0 不在查询 total 及分页，自动调整为列表模式。
            // 注意！！这个地方自己控制好！！
            page.setSize(-1);
        }
        return userService.selectPage(page, null);
    }

    /**
     * 测试事物
     * http://localhost:8080/user/test_transactional<br>
     * 访问如下并未发现插入数据说明事物可靠！！<br>
     * http://localhost:8080/user/test<br>
     * <br>
     * 启动  Application 加上 @EnableTransactionManagement 注解其实可无默认貌似就开启了<br>
     * 需要事物的方法加上 @Transactional 必须的哦！！
     */
    @Transactional(rollbackFor = Exception.class)
    @GetMapping("/test_transactional")
    public void testTransactional() {
        JProfile.enter("/test_transactional");
        User user = new User(1000L, "测试事物", AgeEnum.ONE, 3);
        userService.insert(user);
        System.out.println(" 这里手动抛出异常，自动回滚数据");
        JProfile.release();
        throw new RuntimeException();
    }
}
