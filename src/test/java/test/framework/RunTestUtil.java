package test.framework;

import annotations.MyFrameworkTest;
import org.reflections.Reflections;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public final class RunTestUtil {
    RunTestUtil() {
    }

    private static   TreeMap <Integer, Class <?>> makeAnOrderArrayWithClasses(Set<Class<?>> set) {
        TreeMap <Integer, Class <?>> map = new TreeMap<>();
        for (Class<?> clazz : set) {
            if (clazz.isAnnotationPresent(MyFrameworkTest.class)) {
                MyFrameworkTest annotation = clazz.getAnnotation(MyFrameworkTest.class);
                int i = annotation.value();
                map.put(i, clazz);
            }
        }
        return map;
    }

    private static TreeMap <Integer, Method> makeAnOrderArrayWithMethods(Method[] methods) {
        TreeMap <Integer, Method> map = new TreeMap<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyFrameworkTest.class)) {
                MyFrameworkTest annotation = method.getAnnotation(MyFrameworkTest.class);
                int i = annotation.value();
                map.put(i, method);
            }
        }
        return map;
    }

    public static void runTestsForPackage(String packageAsString) throws Throwable {
        Reflections reflections = new Reflections(packageAsString);
        Set<Class<?>> allClasses = reflections.getTypesAnnotatedWith(MyFrameworkTest.class);
        TreeMap <Integer, Class <?>>  orderAllClasses = RunTestUtil.makeAnOrderArrayWithClasses(allClasses);
        for (Map.Entry <Integer, Class <?>> entry : orderAllClasses.entrySet()){
            Class <?> clazz = entry.getValue();
            Constructor constructor = clazz.getConstructor(null);
            Object possibleTestContainerObject = constructor.newInstance();
            TreeMap <Integer, Method> orderMethods = RunTestUtil.makeAnOrderArrayWithMethods(clazz.getMethods());
            for (Map.Entry  <Integer, Method> map : orderMethods.entrySet()){
                Method method = map.getValue();
                method.invoke(possibleTestContainerObject);
            }
        }
    }
}

