package myspringmvc.util;

import myspringmvc.annotation.RequestMapping;
import myspringmvc.exception.MethodNotFoundException;
import myspringmvc.exception.PathNotFoundException;
import myspringmvc.requestmethodenum.RequestMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;

public class LoadClassAndMethod {

    public static final Map<String, Class> classBox = new HashMap();
    public static final Map<String, Method> methodBox = new HashMap<>();

    static {
        try {
            loadClassBox();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        loadMethodBox();
    }

    private static void loadClassBox() throws IOException, ClassNotFoundException {
        List<Class> classList = ScanPackage.getClasses();
        Iterator<Class> it = classList.iterator();
        while(it.hasNext()) {
            Class clazz = it.next();
            RequestMapping requestMapping = (RequestMapping) clazz.getAnnotation(RequestMapping.class);
            if(requestMapping == null) continue;
            String classURI = requestMapping.value();
            // 这里用循环，都是String截取，这样虽然性能不好，但是这并不影响，没有哪个用户真的去写RequestMapping(value="////////")
            while(classURI.length() != 0 && classURI.lastIndexOf("/") == classURI.length() - 1) classURI = classURI.substring(0, classURI.length() - 1);
            if(requestMapping != null) classBox.put(classURI, clazz);
        }
    }

    private static void loadMethodBox() {
        Set keySet = classBox.keySet();
        Iterator<String> it = keySet.iterator();
        while(it.hasNext()) {
            String key = it.next();
            Class clazz = classBox.get(key);
            Method[] methods = clazz.getMethods();
            for(Method method : methods) {
                RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
                if(requestMapping == null) continue;
                String value = requestMapping.value();
                if(requestMapping.method().length == 0) methodBox.put(value, method);
                for(RequestMethod requestMethod : requestMapping.method()) {
                    StringBuilder methodAnnCon = new StringBuilder(value);
                    if(methodAnnCon.indexOf("/") != 0) methodAnnCon = new StringBuilder("/").append(methodAnnCon);
                    methodBox.put(methodAnnCon.append("&requestMethod=").append(requestMethod).toString(), method);
                }
            }
        }
    }

    private static String getClassPath(String requestPath) {
        List<String> pathList = ParseURL.parseURI(requestPath);
        Iterator<String> it = pathList.iterator();
        while(it.hasNext()) {
            String path = it.next();
            if(classBox.get(path) != null) return path;
        }
        throw new PathNotFoundException("Status:FAIL, Message: this path not have a class to match");
    }

    private static String getMethodRequestMappingValue(String requestPath) {
        String classRequestMappingValue = getClassPath(requestPath);
        return requestPath.replace(classRequestMappingValue, "");
    }

    public static Class getClass(String requestPath) {
        String theClassPath = getClassPath(requestPath);
        return classBox.get(theClassPath);
    }

    public static Method getMethod(String requestPath, String requestMethod) {
        String noMethodValue = getMethodRequestMappingValue(requestPath);
        StringBuilder methodValue = new StringBuilder(noMethodValue);
        Method method = methodBox.get(methodValue.append("&requestMethod=").append(requestMethod).toString());
        if(method == null) method = methodBox.get(noMethodValue);
        if(method == null) throw new MethodNotFoundException("Status: FAIL, Message: method not found");
        return method;
    }

}
