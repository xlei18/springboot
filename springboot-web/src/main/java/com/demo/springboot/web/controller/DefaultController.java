package com.demo.springboot.web.controller;
/**
 * Created by leo on 2016/10/25.
 */

import com.demo.springboot.web.annotation.FruitColor;
import com.demo.springboot.web.annotation.FruitName;
import com.demo.springboot.web.entity.AnnotationApple;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;

/**
 * DemoColtroller
 * 描 述：
 * 作 者：leo
 * 时 间：2016/10/25 15:19
 */
@RestController
public class DefaultController {

    @RequestMapping(value = "/demo", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String test(@ModelAttribute("apple") AnnotationApple apple) throws Exception {
        System.out.println("/demo" + apple.toString());


        Field[] fields = apple.getClass().getDeclaredFields();

        for (Field field : fields) {
            if(field.isAnnotationPresent(FruitName.class)==true){
                FruitName name = field.getAnnotation(FruitName.class);
                System.out.println("Fruit Name:"+name.value());
            }
            if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor color = field.getAnnotation(FruitColor.class);
                System.out.println("Fruit Color:"+color.fruitColor());
            }

        }
        return "SUCCESS";
    }

}
