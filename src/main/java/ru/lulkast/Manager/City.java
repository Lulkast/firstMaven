package ru.lulkast.Manager;

import com.google.common.base.Strings;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
public class City {

    final private Set<Street> streets;
    final private Set<Park> parks;
    final private Set<Areable> allBildings;
    private String name;

    public City(String name) {
        this.name = name;
        this.streets = new HashSet<>();
        this.parks = new HashSet<>();
        this.allBildings = new HashSet<>();
    }

    public void addStreetToCity(@NonNull Street street) {
        if (!Strings.isNullOrEmpty(street.getName())
                && street.getLength() != 0
                && street.getWidth() != 0) {
            this.getStreets().add(street);
            this.getAllBildings().add(street);
        }
    }

    public void addParkToCity(@NonNull Park park) {
        if (!Strings.isNullOrEmpty(park.getName())
                && park.getLength() != 0
                && park.getWidth() != 0) {
            this.getParks().add(park);
            this.getAllBildings().add(park);
        }
    }

    public City addAllBildings(List<LocatedInTheCity> list) {
        list.stream()
                .forEach(a -> {
                    if (a.getClass() == Street.class) addStreetToCity((Street) a);
                    else if (a.getClass() == Park.class) addParkToCity((Park) a);
                });
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return name.equals(city.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}


