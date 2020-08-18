package com.demo.service;

import com.demo.dao.JdbcQueryManager;
import com.demo.model.PageParam;
import com.demo.model.User;
import com.demo.model.UserDTO;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Optional;
import java.util.function.Function;

@RunWith(PowerMockRunner.class)
@PrepareForTest({User.class, UserServiceImpl.class}) //Static.class 是包含 static methods的类
public class UserServiceTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;
    @Mock
    private JdbcQueryManager jdbcQueryManager;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        // 可使用PowerMockito.spy（class）模拟特定方法: 包括私有方法和静态方法
        // mock User 类的静态方法
        PowerMockito.mockStatic(User.class);
    }

    @After
    public void clearMocks() {
        // 避免大量内存泄漏  2.25.0新增
        Mockito.framework().clearInlineMocks();
    }

    @Test // Mockito测试，实现类逻辑的测试是重点之一
    public void test10() {
        long id = 9527;
        User user = new User();
        user.setId(id);

        // 测试userServiceImpl.findById()方法中逻辑实现有没问题。
        Mockito.when(jdbcQueryManager.queryForObject(user, User.class)).thenReturn(Optional.of(user));
        TestCase.assertEquals(user, userServiceImpl.findById(id));
    }

    @Test // mock静态方法1 （使用PowerMockito.mockStatic(class) mock静态方法）
    public void test11() {
        User user = new User();
        user.setName("白玉京");
        UserDTO dto = new UserDTO();
        dto.setName("白玉京");

        //PowerMock 2.0.7 对下面方法的支持 也就到Mockito 2.26.X版本为止
        Mockito.when(User.convert(user)).thenReturn(dto);
        TestCase.assertEquals(dto, User.convert(user));
    }

    @Test // 函数类型参数匹配。 精确匹配我没找到该怎么匹配
    public void test12() {
        User user = new User();
        user.setId(9527L);

        Mockito.when(jdbcQueryManager.queryForList(user, User.class, User::convert)).thenReturn(Lists.newArrayList(new UserDTO()));
        TestCase.assertEquals(Lists.newArrayList(new UserDTO()), userServiceImpl.findByUser(user));
    }

    @Test // 函数类型参数匹配。 类型匹配
    public void test13() {
        User user = new User();
        user.setId(9527L);

        Mockito.when(jdbcQueryManager.queryForList(Mockito.eq(user), Mockito.eq(User.class), Mockito.any(Function.class)))
                .thenReturn(Lists.newArrayList(new UserDTO()));
        TestCase.assertEquals(Lists.newArrayList(new UserDTO()), userServiceImpl.findByUser(user));
    }

    @Test // 引用类型参数，未重写equals方法, 解决方法
    public void test14() {
        long pageNo = 1;
        long pageSize = 20;
        String name = "白玉京";
        User user = new User();
        user.setName(name);
        PageParam pageParam = PageParam.create(pageNo, pageSize);
        // PageParam未重写equals方法,参数匹配通不过
        Mockito.when(jdbcQueryManager.queryForPageList(user, User.class, pageParam)).thenReturn(Lists.newArrayList(user));

        // 解决方法: 1.重写equals方法；2.模糊匹配； 3.自定义匹配。
//        ArgumentMatcher<PageParam> argPage = (page) ->
//                page.getPageNo() == pageParam.getPageNo() && page.getPageSize() == pageParam.getPageSize();
//        Mockito.when(jdbcQueryManager.queryForPageList(Mockito.eq(user), Mockito.eq(User.class), Mockito.argThat(argPage)))
//                .thenReturn(Lists.newArrayList(user));
        TestCase.assertEquals(Lists.newArrayList(user), userServiceImpl.findByName(pageNo, pageSize, name));
    }

    @Test // mock私有方法（使用PowerMockito.spy（object）mock私有方法）
    public void test15() throws Exception {
        String str = "白玉京";
        UserServiceImpl spyObj = PowerMockito.spy(userServiceImpl);
        PowerMockito.when(spyObj, "privateMethod", str).thenReturn("35174");
        TestCase.assertEquals("35174", spyObj.testPrivateMethod(str));
    }

    @Test // mock静态方法2（使用PowerMockito.spy（class）mock静态方法）
    public void test17() throws Exception {
        String str = "白玉京";
        PowerMockito.spy(UserServiceImpl.class);
        PowerMockito.when(userServiceImpl, "staticMethod", str).thenReturn("35174");
        TestCase.assertEquals("35174", userServiceImpl.testStaticMethodMethod(str));
    }

}






