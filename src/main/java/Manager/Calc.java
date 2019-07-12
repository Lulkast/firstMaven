package Manager;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;

final public class Calc {

    private Calc (){}

    public static double getArea(Collection<? extends Areable>... areables) {
        return Stream.of(areables)
                .flatMap(Collection::stream)
                .mapToDouble(c -> ((Areable) c).getWidth() * ((Areable) c).getLength())
                .sum();
    }

    public static double getDamag(Collection<? extends Areable>... areables) {
        return Stream.of(areables)
                .flatMap(Collection::stream)
                .map(c -> ((Areable) c).getDamage())
                .flatMap(Collection::stream)
                .mapToDouble(d -> d.getDamageWidth() * d.getDamageLength())
                .sum();
    }

    public static double getLength(Collection<? extends Areable>... areables) {
        return Stream.of(areables)
                .flatMap(Collection::stream)
                .mapToDouble(c -> ((Areable) c).getLength())
                .sum();
    }
}
