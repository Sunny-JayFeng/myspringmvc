package myspringmvc.util;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ParseURL {

    private static List<String> getPaths(String requestURIPath) {
        List<String> allPaths = new ArrayList<>();
        if(!requestURIPath.contains("/")) return allPaths;
        allPaths.add(requestURIPath);
        int lastIndex = requestURIPath.lastIndexOf("/");
        String newPath = requestURIPath.substring(0, lastIndex);
        allPaths.addAll(getPaths(newPath));
        return allPaths;
    }

    public static List<String> parseURI(String requestURIPath) {
        List<String> allPaths = getPaths(requestURIPath);
        allPaths.add(requestURIPath.substring(requestURIPath.lastIndexOf("/")));
        allPaths.add("/");
        allPaths.add("");
        return allPaths;
    }

    public static List<String> getArgumentsValue(HttpServletRequest request) {
        Enumeration<String> parameters = request.getParameterNames();
        List<String> argumentsValue = new ArrayList<>();
        if(parameters == null) return argumentsValue;
        while(parameters.hasMoreElements()) {
            argumentsValue.add(request.getParameter(parameters.nextElement()));
        }
        return argumentsValue;
    }
}
