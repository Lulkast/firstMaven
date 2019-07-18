package ru.lulkast.Manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Manager {
    private Manager() {
    }
    static Map<Class, Function<String, ?>> classFunctionMap = new HashMap<>();
    static Set<City> cities = new HashSet<>();
    static {
        Function<String, UUID> stringToUUID = string -> UUID.fromString(string);
        Function<String, Double> stringToDouble = string -> Double.parseDouble(string);
        Function<String, String> stringToString = string -> string;
        classFunctionMap.put(UUID.class, stringToUUID);
        classFunctionMap.put(double.class, stringToDouble);
        classFunctionMap.put(String.class, stringToString);
    }

    private static final Function<String, ?> getFunctionsClass(Type type) {
        return Manager.classFunctionMap.get(type);
    }

    public static LocatedInTheCity paramsToLocatedInTheCityReflectively(List<String> list) throws Exception {
        Map<String, String> map = list.stream()
                .collect(Collectors.toMap(a -> a.substring(0, a.indexOf(":")), a -> a.substring(a.indexOf(":") + 1)));
        Class<?> clazz = Class.forName(map.get("Class"));
        Constructor<?> constructor = clazz.getConstructor(UUID.class, String.class, double.class, double.class);
        Object locatedInTheCity = constructor.newInstance(UUID.randomUUID(), "NULL", 1, 1);
        List<Method> methods = Arrays.asList(clazz.getDeclaredMethods());
        Map<Method, String> methodAndParam = methods.stream()
                .filter(method -> method.getName().startsWith("set"))
                .filter(method -> map.containsKey(method.getName().substring(3)))
                .collect(Collectors.toMap(method -> method, method -> map.get(method.getName().substring(3))));
        for (Map.Entry<Method, String> entry : methodAndParam.entrySet()) {
            Method method = entry.getKey();
            String param = entry.getValue();
            Type paramsType = method.getGenericParameterTypes()[0];
            Function function = Manager.getFunctionsClass(paramsType);
            method.invoke(locatedInTheCity, function.apply(param));
        }
        LocatedInTheCity someBilding = (LocatedInTheCity) locatedInTheCity;
        return someBilding;
    }

    public static City buildCityReflectively(String cityName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class<City> cityClass = City.class;
        Constructor<?>[] declaredConstructors = cityClass.getDeclaredConstructors();
        City newCity = (City) declaredConstructors[0].newInstance(cityName);
        return newCity;
    }

    private static LocatedInTheCity paramsToLocatedInTheCity(List<String> list) {
        LocatedInTheCity locatedInTheCity = null;
        if (list.get(1).equals("Manager.Street")) {
            locatedInTheCity = new Street(list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        } else if (list.get(1).equals("Manager.Park")) {
            locatedInTheCity = new Park(list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        }
        locatedInTheCity.setCityName(list.get(0));
        return locatedInTheCity;
    }

    private static List<String> parsingParamsFromText(String text) {
        return Arrays.asList(text.split(",")).stream()
                .map(param -> param.substring(param.indexOf(":") + 1))
                .collect(Collectors.toList());
    }

    public static Set<City> getFromBase(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        return reader.lines()
                .map(Manager::parsingParamsFromText)                                                                //тут мы получили лист стрингов с полями для каждого строения
                .map(Manager::paramsToLocatedInTheCity)                                                                            //этот метод сделал нужные строения и вернул их как Areable
                .collect(Collectors.groupingBy(LocatedInTheCity::getCityName))                                                    //тут мы сгруппировали Areable по городам, в которых они должны лежать, получили мапу
                .entrySet().stream()
                .map(a -> new City(a.getKey()).addAllBildings(a.getValue()))                                             //из каждой пары (название города, лист со строениями) делаем город с улицами, парками
                .collect(Collectors.toSet());
    }
}
