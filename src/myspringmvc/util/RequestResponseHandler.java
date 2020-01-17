package myspringmvc.util;

import myspringmvc.annotation.Param;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class RequestResponseHandler {

    private static volatile RequestResponseHandler requestResponseHandler;

    public static RequestResponseHandler getRequestResponseHandler() {
        if(requestResponseHandler == null) {
            synchronized(RequestResponseHandler.class) {
                if(requestResponseHandler == null) requestResponseHandler = new RequestResponseHandler();
            }
        }
        return requestResponseHandler;
    }

    public void requestHandler(HttpServletRequest request, HttpServletResponse response) throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = getRequestClass(request.getRequestURI());
        Method method = getRequestMethod(request.getRequestURI(), request.getMethod());
        method.invoke(clazz.newInstance(), getArgumentsArray(request, response, method));
    }

    private Class getRequestClass(String requestPath) {
        return LoadClassAndMethod.getClass(requestPath);
    }

    private Method getRequestMethod(String requestPath, String requestMethod) {
        return LoadClassAndMethod.getMethod(requestPath, requestMethod);
    }

    private Object[] getArgumentsArray(HttpServletRequest request, HttpServletResponse response, Method method) {
        List<Object> argumentsValue = new ArrayList<>();
            Parameter[] parameters = method.getParameters();
        for(Parameter parameter : parameters) {
            String parameterType = parameter.getType().toString();
            if(parameterType.endsWith(".http.HttpServletRequest")) {
                argumentsValue.add(request);
            }else if(parameterType.endsWith(".http.HttpServletResponse")) {
                argumentsValue.add(response);
            }
            Param param = parameter.getAnnotation(Param.class);
            if(param == null) {
                continue;
            }else {
                argumentsValue.add(request.getParameter(param.value()));
            }
        }
        return argumentsValue.toArray();
    }
}
