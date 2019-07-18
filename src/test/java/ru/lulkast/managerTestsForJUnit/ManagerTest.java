package ru.lulkast.managerTestsForJUnit;

import ru.lulkast.Manager.City;
import ru.lulkast.Manager.Manager;
import org.junit.Assert;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import ru.lulkast.Manager.Street;
import static ru.lulkast.Manager.Manager.getFromBase;

public class ManagerTest {
    @Test
    public void getFromBaseTest() throws IOException {
        Set<City> cities;
        cities = getFromBase("C:\\practice\\firstMaven\\src\\main\\resources\\base.txt");
        assertThat(cities.size(), is(2));
        assertThat(cities, hasItem(new City("Yaroslavl")));
        for (City c:cities) {
            assertThat(c.getStreets().size(), is(3));
            assertThat(c.getParks().size(), is(2));
        }
    }

    @Test
    public void paramsToLocatedInTheCityReflectivelyTest() throws Exception {
        Street street = new Street(UUID.fromString("1ac301e9-c16a-4873-805c-a890baa93970"), "St.Perersburg st", 10, 1000);
        street.setCityName("Dresden");
        List<String> list = List.of("CityName:Dresden", "Class:ru.lulkast.Manager.Street", "Id:1ac301e9-c16a-4873-805c-a890baa93970", "Name:St.Perersburg st", "Length:10", "Width:1000");
        Street street1 = (Street) Manager.paramsToLocatedInTheCityReflectively(list);
        System.out.println(street.toString());
        System.out.println(street1.toString());
        Assert.assertEquals(street, street1);
    }

    @Test
    public void buildCityReflectivelyTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        City dresden = Manager.buildCityReflectively("Dresden");
        Assert.assertEquals(dresden, new City("Dresden"));
    }
}
