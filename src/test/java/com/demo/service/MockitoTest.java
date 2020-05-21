package com.demo.service;

import com.demo.model.PageParam;
import com.demo.model.User;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import java.util.List;
import java.util.Map;
import static org.mockito.ArgumentMatchers.anyInt;

//注意1：@Test的测试方法必须是public的,否则：java.lang.Exception: Method XXX should be public
@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Before
    public void setup() {
        //这句话执行以后，aaaDao和bbbDao自动注入到abcService中。
        MockitoAnnotations.initMocks(this);
        //在这之后，你就可以放心大胆地使用when().then()、
        //Mockito.doNothing().when(obj).someMethod()、
        //doThrow(new RuntimeException()).when(obj).someMethod(Mockito.any());
        //等进行更详细的设置。
    }
    @After
    public void  clearMocks() {
        // 避免大量内存泄漏  2.25.0新增
        Mockito.framework().clearInlineMocks();
    }

    @Test // 没返回值的方法匹配
    public void test0(){
        List mockList = Mockito.mock(List.class);

        mockList.add(1); // 基础类型
        mockList.add(Lists.newArrayList("a")); // 引用类型

        Mockito.verify(mockList).add(1);
        Mockito.verify(mockList).add(Lists.newArrayList("a"));
    }

    @Test // 有返回值的方法匹配， 参数的三类匹配情况
    public void test1(){
        Map mockMap = Mockito.mock(Map.class);
        // 1.精确匹配
        Mockito.when(mockMap.get(11)).thenReturn(111); // 基础类型
        Mockito.when(mockMap.get(Lists.newArrayList("袁紫霞"))).thenReturn("白玉京"); // 引用类型
        TestCase.assertEquals(111, mockMap.get(11));
        TestCase.assertEquals("白玉京", mockMap.get(Lists.newArrayList("袁紫霞")));

        // 2.模糊匹配
        Mockito.when(mockMap.get(Mockito.endsWith("天"))).thenReturn("龙傲天"); // 字符串。eg:以天结尾的
        Mockito.when(mockMap.get(Mockito.anyLong())).thenReturn(999L); // 基础类型. eg:任何long类型
        Mockito.when(mockMap.get(Mockito.any(User.class))).thenReturn(new User()); // 引用类型
        TestCase.assertEquals("龙傲天", mockMap.get("星期天"));
        TestCase.assertEquals(999L, mockMap.get(1L));
        TestCase.assertEquals(new User(), mockMap.get(new User()));

        // 3.自定义匹配。 eg：定义只匹配PageParam的pageNo属性
        PageParam pageParam = PageParam.create(1, 20);
        ArgumentMatcher<PageParam> argPage = (page) -> page.getPageNo() == pageParam.getPageNo();
        Mockito.when(mockMap.get(Mockito.argThat(argPage))).thenReturn(Lists.newArrayList("袁紫霞", "白玉京"));
        TestCase.assertEquals(Lists.newArrayList("袁紫霞", "白玉京"), mockMap.get(PageParam.create()));
    }

    @Test // final类型方法或类的匹配
    public void test2(){
        /*
        * 正常情况下，final/private/equals()/hashCode() methods不能被拦截或验证
        * 支持模拟final class，enum和final method（自2.1.0起）
        * https://github.com/mockito/mockito/wiki/What%27s-new-in-Mockito-2#unmockable
        * 文件 src/test/resources/mockito-extensions/org.mockito.plugins.MockMaker 中添加：mock-maker-inline
         */
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.finalMethod()).thenReturn(false);
        TestCase.assertEquals(false, mockUser.finalMethod());

    }

    @Test // 自定义返回结果
    public void test3(){
        Map mockMap = Mockito.mock(Map.class);

        Answer answer = (invocation) -> {
            Object[] args = invocation.getArguments();
            return String.valueOf(args[0]) + 110;
        };
        Mockito.when(mockMap.get(anyInt())).then(answer);
        TestCase.assertEquals("120110", mockMap.get(120));
    }

    @Test // 设置抛出异常
    public void test4(){
        List mockList = Mockito.mock(List.class);

        // 1.无返回值方法 设置异常
        Mockito.doThrow(RuntimeException.class).when(mockList).add(1);
        try {
            mockList.add(1);
        }catch (RuntimeException e){
            System.out.println("doThrow(RuntimeException.class).when(mock).someVoidMethod();");
        }

        // 2.有返回值方法 设置异常
        Mockito.when(mockList.get(1000)).thenThrow(new IndexOutOfBoundsException("数组下标越界"));
        try {
            mockList.get(1000);
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
    }

    @Test //连续的方法调用设置不同的行为
    public void test5(){
        List mockList = Mockito.mock(List.class);
        Mockito.when(mockList.get(1000)).thenReturn(11,22).thenThrow(new IndexOutOfBoundsException("数组下标越界"));
        TestCase.assertEquals(11, mockList.get(1000)); // 第一次调用返回值：11
        TestCase.assertEquals(22, mockList.get(1000)); // 第二次调用返回值：22
        try {
            mockList.get(1000); // 第三次调用抛出异常
        }catch (IndexOutOfBoundsException e){
            System.out.println(e.getMessage());
        }
    }

    @Test // 测试方法的调用次数
    public void test6(){
        /*
        * times(n)：方法被调用n次。
        * never()：没有被调用。
        * atLeast(n)：至少被调用n次。
        * atLeastOnce()：至少被调用1次，相当于atLeast(1)。
        * atMost()：最多被调用n次。
         */
        List mockList = Mockito.mock(List.class);
        mockList.add("one times");
        mockList.add("2 times");
        mockList.add("2 times");
        Mockito.verify(mockList, Mockito.atMost(1)).add("one times");
        Mockito.verify(mockList, Mockito.times(2)).add("2 times");
    }

}