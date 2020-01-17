package controller.mytest;

import myspringmvc.annotation.Param;
import myspringmvc.annotation.RequestMapping;
import myspringmvc.requestmethodenum.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping(value = "//////")
public class MyTest{

    @RequestMapping(value="/noParam")
    public void noParam() {

    }

    @RequestMapping(value="/test", method = RequestMethod.POST)
    public void test(@Param("name") String name, @Param("testParam") String testParam, HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("name: " + name);
        System.out.println("testParam: " + testParam);
        response.getWriter().write("SUCCESS");
        response.getWriter().flush();
    }

    @RequestMapping(value="/test", method = RequestMethod.GET)
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println(request.getRequestURL());
        response.getWriter().write("SUCCESS");
        response.getWriter().flush();
    }

//    @RequestMapping(value="/test", method = RequestMethod.GET)
//    public void test(@Param("name") String name, @Param("age") String age, HttpServletResponse response) throws IOException {
//        System.out.println("name: " + name);
//        System.out.println("age: " + age);
//        response.getWriter().write("SUCCESS");
//        response.getWriter().flush();
//    }

//    @RequestMapping(value="/test")
//    public void test(@Param("name") String name, @Param("age") String age) {
//        System.out.println("name: " + name);
//        System.out.println("age: " + age);
//    }

    @RequestMapping(value="/show")
    public void show(@Param("name") String name, @Param("password") String password, @Param("userName") String userName) {
        System.out.println("show");
    }

    public String toString() {
        return "Yes";
    }
}
