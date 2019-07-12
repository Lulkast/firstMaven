package Manager;

import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Park implements Areable, LocatedInTheCity {
    private final UUID id;
    private String name;
    private double length;
    private double width;
    private Set<Damage> damages;
    private String cityName;

    public Park(@NonNull String name, double length, double width) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.length = length;
        this.width = width;
        this.damages = new HashSet<>();
    }

    public Park(@NonNull UUID uuid, @NonNull String name, double length, double width) {   //второй конструктор, потому что в базу к нам поступают парки с готовыми айди
        this.id = uuid;
        this.name = name;
        this.length = length;
        this.width = width;
        this.damages = new HashSet<>();
    }

    public void setDamag(@NonNull Damage... damag) {
        for (Damage d : damag
        ) {
            this.damages.add(d);
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
        return damages;
    }
}

