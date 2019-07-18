package ru.lulkast.Manager;

import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class Manager {
    private Manager() {
    }

    static final Map<Class, Function<String, ?>> classFunctionMap = new HashMap<>();
    static Set<City> cities = new HashSet<>();

    static {
        Function<String, UUID> stringToUUID = string -> UUID.fromString(string);
        Function<String, Double> stringToDouble = string -> Double.parseDouble(string);
        Function<String, String> stringToString = string -> string;
        classFunctionMap.put(UUID.class, stringToUUID);
        classFunctionMap.put(double.class, stringToDouble);
        classFunctionMap.put(String.class, stringToString);
    }

    public static LocatedInTheCity paramsToLocatedInTheCityReflectively(List<String> list) throws Exception {
        Map<String, String> params = getParamsFromBase(list);
        Class<?> clazz = Class.forName(params.get("Class"));
        Constructor<?> constructor = clazz.getConstructor(UUID.class, String.class, double.class, double.class);
        Object locatedInTheCity = constructor.newInstance(UUID.randomUUID(), "NULL", 1, 1);
        Map<Method, Class> settersTypeMap = getSettersType(clazz);
        Map<Method, Object> settersWithParams = getSettersWithParams(settersTypeMap, params);
        for (Map.Entry<Method, Object> entry : settersWithParams.entrySet()) {
            Method method = entry.getKey();
            Object param = entry.getValue();
            method.invoke(locatedInTheCity, param);
        }
        LocatedInTheCity someBilding = (LocatedInTheCity) locatedInTheCity;
        return someBilding;
    }

    private static Function<String, ?> getFunctionsClass(Type type) {
        return Manager.classFunctionMap.get(type);
    }

    private static Object convertStringByType(String string, Type type) {
        Function function = Manager.getFunctionsClass(type);
        return function.apply(string);
    }

    private static Map<String, String> getParamsFromBase(List<String> list) {
        return list.stream()
                .collect(Collectors.toMap(a -> a.substring(0, a.indexOf(":")), a -> a.substring(a.indexOf(":") + 1)));
    }

    private static Map<Method, Class> getSettersType(Class<?> clazz) {
        HashMap<Method, Class> setterTypeMap = new HashMap<>();
        List<Field> declaredFields = Arrays.asList(clazz.getDeclaredFields());
        String setterPrefix = "set";
        for (Field declaredField : declaredFields) {
            String fieldName = declaredField.getName();
            String setterName = setterPrefix + StringUtils.capitalize(fieldName);
            Class type = declaredField.getType();
            try {
                Method declaredMethod = clazz.getDeclaredMethod(setterName, type);
                setterTypeMap.put(declaredMethod, type);
            } catch (Exception e) {
            }
        }
        return setterTypeMap;
    }

    private static Map<Method, Object> getSettersWithParams(Map<Method, Class> setterTypeMap, Map<String, String> params) {
        Map<Method, Object> setterParamsMap = new HashMap<>();
        for (Map.Entry<Method, Class> entry : setterTypeMap.entrySet()) {
            Method method = entry.getKey();
            Class type = entry.getValue();
            String paramString = params.get(method.getName().substring(3));
            Object param = convertStringByType(paramString, type);
            setterParamsMap.put(method, param);
        }
        return setterParamsMap;
    }


    /*-----------------------------------------------------------------------------*/


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
