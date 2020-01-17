package myspringmvc.controllermanager;

import myspringmvc.util.RequestResponseHandler;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

public class ControllerManager extends HttpServlet {


    public void service(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html; charset=utf-8");
        if("/favicon.ico".equals(request.getRequestURI())) return;
        RequestResponseHandler requestResponseHandler = RequestResponseHandler.getRequestResponseHandler();
        try {
            requestResponseHandler.requestHandler(request, response);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
