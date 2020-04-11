package com.rubbishman.rubbishcombat.state.attribute;

public class LimitedAttribute {
    public final String name;
    public final int current;
    public final int maximum;

    public LimitedAttribute(String name, int current, int maximum) {
        this.name = name;
        this.current = current;
        this.maximum = maximum;
    }
}
