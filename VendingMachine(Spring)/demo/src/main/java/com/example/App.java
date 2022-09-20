package com.example;

import com.example.controller.VendingMachine_Controller;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws Exception {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachine_Controller controller = ctx.getBean("controller", VendingMachine_Controller.class);
        controller.run();
    }
}
