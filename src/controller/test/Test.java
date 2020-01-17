package controller.test;

import myspringmvc.annotation.RequestMapping;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = Class.forName("controller.mytest.MyTest");
        Method method = clazz.getMethod("test", String.class, String.class);
        List<String> argList = new ArrayList<>();
        argList.add("100");
        argList.add("200");
        method.invoke(clazz.newInstance(), argList.toArray());
    }

}
