package myspringmvc.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;

public class ScanPackage {


    // 深度优先搜索
    private static List<String> getAllClassName(String packageName, File file) {
        if(packageName == null || packageName.length() == 0 || file == null) return new ArrayList<>();
        List<String> classNameList = new ArrayList<>();
        File[] files = file.listFiles();
        if(files == null) classNameList.add(packageName + "." + file.getName().split("\\.")[0]);
        else {
            for(File theFile : files) {
                if(theFile.getName().contains(".class")) classNameList.addAll(getAllClassName(packageName, theFile));
                else classNameList.addAll(getAllClassName(packageName + "." + theFile.getName(), theFile));
            }
        }
        return classNameList;
    }

    private static URL getPackageURL() throws IOException {
        String basePackageName = ReadProperties.getPackageName();
        URL url = Thread.currentThread().getContextClassLoader().getResource(basePackageName);
        return url;
    }

    private static List<Class> getClasses(String baseFilePath, File baseFile) throws IOException, ClassNotFoundException {
        List<Class> classList = new ArrayList<>();
        List<String> classNameList = getAllClassName(baseFile.getName(), new File(baseFilePath));
        Iterator<String> it = classNameList.iterator();
        while(it.hasNext()) {
            Class clazz = Thread.currentThread().getContextClassLoader().loadClass(it.next());
            classList.add(clazz);
        }
        return classList;
    }

    public static List<Class> getClasses() throws IOException, ClassNotFoundException {
        URL url = getPackageURL();
        if(!"file".equals(url.getProtocol())) return new ArrayList<>();
        String baseFilePath = URLDecoder.decode(url.getPath(), "UTF-8");
        File baseFile = new File(baseFilePath);
        return getClasses(baseFilePath, baseFile);
    }


}
