package managerTestsForJUnit;

import Manager.*;
import org.junit.Test;
import annotations.TestableClass;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class CalcTest {
    Street street1 = new Street("qq", 2, 2);
    Street street2 = new Street("ss", 3, 3);
    Park park = new Park("dd", 4, 4);
    ArrayList <? extends Areable> list = new ArrayList<>(Arrays.asList(street1, street2, park));

    @Test
    public void getAreaTest(){
        assertThat(Calc.getArea(list), is(29.0));
    }
    @Test
    public void getDamagTest(){
        ((Street) list.get(1)).setDamag(new Damage(1, 1));
        ((Park)list.get(2)).setDamag(new Damage(2, 2), new Damage(1, 1));
        assertThat(Calc.getDamag(list), is(6.0));
    }
    @Test
    public void getLengthTest(){
       assertThat(Calc.getLength(list), is(9.0));
    }
}
