package managerTestsForJUnit;

import Manager.City;
import Manager.LocatedInTheCity;
import Manager.Park;
import Manager.Street;
import org.junit.Test;
import annotations.TestableClass;

import java.util.ArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CityTest{
    @Test
    public void addStreetToCityTest(){
        City city = new City("");
        Street street = new Street("RRR", 20, 20);
        city.addStreetToCity(street);
        assertThat(city.getStreets().size(), is(1));
        Street street1 = new Street("", 20, 20);
        city.addStreetToCity(street1);
        assertThat(city.getStreets().size(), is(1));
        Street street2 = new Street("aaa", 0, 20);
        city.addStreetToCity(street2);
        assertThat(city.getStreets().size(), is(1));
        Street street3 = new Street("fff", 20, 0);
        city.addStreetToCity(street3);
        assertThat(city.getStreets().size(), is(1));
        Street street4 = new Street("sss", 20, 20);
        street4 = null;
        try {
            city.addStreetToCity(street4);
        }
        catch (NullPointerException e){}
        assertThat(city.getStreets().size(), is(1));
    }
    @Test
    public void addParkToCityTest(){
        City city = new City("");
        Park park = new Park("RRR", 20, 20);
        city.addParkToCity(park);
        assertThat(city.getParks().size(), is(1));
        Park park1 = new Park("", 20, 20);
        city.addParkToCity(park1);
        assertThat(city.getParks().size(), is(1));
        Park park2 = new Park("aaa", 0, 20);
        city.addParkToCity(park2);
        assertThat(city.getParks().size(), is(1));
        Park park3 = new Park("fff", 20, 0);
        city.addParkToCity(park3);
        assertThat(city.getParks().size(), is(1));
        Park park4 = new Park("sss", 20, 20);
        park4 = null;
        try {
            city.addParkToCity(park4);
        }
        catch (NullPointerException e){}
        assertThat(city.getParks().size(), is(1));
    }
    @Test
    public void addAllBildingsTest (){
        City city = new City("gg");
        LocatedInTheCity bild1 = new Street("aa", 10, 10);
        LocatedInTheCity bild2 = new Park("ff", 10, 10);
        ArrayList <LocatedInTheCity> list = new ArrayList<>();
        list.add(bild1);
        list.add(bild2);
        city.addAllBildings(list);
        assertThat(city.getParks().size(), is(1));
        assertThat(city.getStreets().size(), is(1));
        assertThat(city.getAllBildings().size(), is(2));
    }
}
