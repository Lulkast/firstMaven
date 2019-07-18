package ru.lulkast.Manager;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@ToString
public class Street implements Areable, LocatedInTheCity {

    private  UUID id;
    private String name;
    private double length;
    private double width;
    private Set<Damage> damage;
    private String cityName;


    public Street(@NonNull String name, double length, double width) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.length = length;
        this.width = width;
        this.damage = new HashSet<>();
    }

    public Street(@NonNull UUID uuid, @NonNull String name, double length, double width) {    //второй конструктор, потому что в базу к нам поступают улицы с готовыми айди
        this.id = uuid;
        this.name = name;
        this.length = length;
        this.width = width;
        this.damage = new HashSet<>();
    }

    public void setDamage(@NonNull Damage... damag) {
        for (Damage d : damag
        ) {
            this.damage.add(d);
        }
    }

    @Override
    public double getLength() {
        return length;
    }
    @Override
    public double getWidth() {
        return width;
    }
    @Override
    public Set<Damage> getDamage() {
        return damage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Street street = (Street) o;
        return id.equals(street.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
