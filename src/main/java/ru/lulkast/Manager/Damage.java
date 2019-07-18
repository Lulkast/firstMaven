package ru.lulkast.Manager;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class Damage  {
    final private double damageLength;
    final private double damageWidth;

    public Damage(@NonNull double damageLength, @NonNull double damageWidth) {
        this.damageLength = damageLength;
        this.damageWidth = damageWidth;
    }
}
