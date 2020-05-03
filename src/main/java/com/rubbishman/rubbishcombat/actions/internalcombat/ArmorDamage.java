package com.rubbishman.rubbishcombat.actions.internalcombat;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class ArmorDamage {
    public final Identifier target;
    public final int damage;

    public ArmorDamage(Identifier target, int damage) {
        this.damage = damage;
        this.target = target;
    }
}
