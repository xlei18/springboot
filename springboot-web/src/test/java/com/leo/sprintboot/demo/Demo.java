package com.leo.sprintboot.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.demo.springboot.web.WebApplication;
import com.demo.springboot.web.entity.AnnotationApple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Demo
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/26 9:15
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = WebApplication.class)
@WebAppConfiguration
public class Demo {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationConnect;

    @Before
    public void setUp() throws JsonProcessingException {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationConnect).build();

    }

    @Test
    public void testmvc() throws Exception {
        String uri="/demo";
        AnnotationApple apple = new AnnotationApple();
        apple.setAppleName("2");
        MvcResult mvcResult=mvc.perform(MockMvcRequestBuilders.post(uri).flashAttr("apple",apple).accept(MediaType.APPLICATION_JSON)).andReturn();
        int status=mvcResult.getResponse().getStatus();
        String content=mvcResult.getResponse().getContentAsString();
        System.out.println("testmvc()->http返回状态=" + status);
        Assert.assertTrue("testmvc()->http返回状态不正确" + status, status == 200);
        Assert.assertTrue("testmvc()->post请求返回不正确" + content,"SUCCESS".equals(content));
    }
}
