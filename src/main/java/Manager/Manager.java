package Manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;

public final class Manager {
    private Manager() {
    }

    static Set<City> cities = new HashSet<>();

    public static City buildCityReflectively (String cityName) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        Class <City> cityClass = City.class;
        Constructor <?> [] declaredConstructors = cityClass.getDeclaredConstructors();
        City newCity = (City) declaredConstructors [0].newInstance(cityName);
        return newCity;
    }

    public static LocatedInTheCity paramsToLocatedInTheCityReflectively (List <String> list) throws IllegalAccessException, InvocationTargetException, InstantiationException, ClassNotFoundException {
        LocatedInTheCity locatedInTheCity = null;
        Class <?> n = Class.forName(list.get(1));
        Constructor <?> [] declaredConstructors = n.getDeclaredConstructors();
        if (n.getSimpleName().equals("Street")) locatedInTheCity = (Street)declaredConstructors [1].newInstance(UUID.fromString(list.get(2)),list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        else if (n.getSimpleName().equals("Park")) locatedInTheCity = (Park)declaredConstructors [1].newInstance(UUID.fromString(list.get(2)),list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));

      /* было так:
        if (list.get(1).equals("Street")) {
            Class <Street> streetClass = Street.class;
            Constructor <?> [] declaredConstructors = streetClass.getDeclaredConstructors();
            locatedInTheCity = (Street) declaredConstructors [0].newInstance(list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        } else if (list.get(1).equals("Park")) {
            Class <Park> parkClass = Park.class;
            Constructor <?> [] declaredConstructors = parkClass.getDeclaredConstructors();
            locatedInTheCity = (Park) declaredConstructors [0].newInstance(list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        }*/
        locatedInTheCity.setCityName(list.get(0));
        return locatedInTheCity;
    }

    static LocatedInTheCity paramsToLocatedInTheCity(List<String> list) {
        LocatedInTheCity locatedInTheCity = null;
        if (list.get(1).equals("Manager.Street")) {
            locatedInTheCity = new Street(list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        } else if (list.get(1).equals("Manager.Park")) {
            locatedInTheCity = new Park(list.get(3), Double.parseDouble(list.get(4)), Double.parseDouble(list.get(5)));
        }
        locatedInTheCity.setCityName(list.get(0));
        return locatedInTheCity;
    }

    static List<String> parsingParamsFromText(String text) {
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
