package com.leo.sprintboot.demo.regular;
/**
 * Created by leo on 2016/11/7.
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * RegularTest
 * 描 述：
 * 作 者：leo
 * 时 间：2016/11/7 13:56
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class RegularTest {

    @Test
    public void matcherTest(){
        // 这个正则表达式有三个组: 整个\w(\d\d)(\w+) 是第0组 group(0) (\d\d)是第1组 group(1) (\w+)是第2组 group(2)
        // 我们看看和正则表达式匹配的一个字符串x99SuperJava，
        // group(0)是匹配整个表达式的字符串的那部分x99SuperJava
        // group(1)是第1组(\d\d)匹配的部分:99
        // group(2)是第二组(\w+)匹配的那部分SuperJava
//        String regex = "\\w(\\d\\d)(\\w+)";
//        String candidate = "x99SuperJava";
        String regex = "\\$\\{[^\\}]+\\}";
        String candidate = "2054683715";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(candidate);
        while (matcher.find()) {
            System.out.println(matcher.group());
        }

    }

}
