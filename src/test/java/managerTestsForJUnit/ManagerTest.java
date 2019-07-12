package managerTestsForJUnit;

import Manager.City;
import Manager.Manager;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import Manager.Street;
import static Manager.Manager.getFromBase;

public class ManagerTest {
    @Test
    public void getFromBaseTest() throws IOException {
        Set<City> cities;
        cities = getFromBase("C:\\practice\\firstMaven\\src\\main\\java\\Manager\\base.txt");
        assertThat(cities.size(), is(2));
        assertThat(cities.contains(new City("Yaroslavl")), is(true));
        cities.stream()
                .forEach(a -> assertThat(a.getStreets().size(), is(3)));
        cities.stream()
                .forEach(a -> assertThat(a.getParks().size(), is(2)));
    }

    @Test
    public void buildCityReflectivelyTest() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        City dresden = Manager.buildCityReflectively("Dresden");
        assertThat(dresden.equals(new City("Dresden")), is(true));
    }

    @Test
    public void paramsToLocatedInTheCityReflectivelyTest() throws InvocationTargetException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Street street = new Street(UUID.fromString("1ac301e9-c16a-4873-805c-a890baa93970"), "St.Perersburg st", 10, 1000);
        street.setCityName("Dresden");
        List<String> list = List.of("Dresden", "Manager.Street", "1ac301e9-c16a-4873-805c-a890baa93970", "St.Perersburg st", "10", "1000");
        Street street1 = (Street) Manager.paramsToLocatedInTheCityReflectively(list);
        assertThat(street.equals(street1), is(true));
    }
}
