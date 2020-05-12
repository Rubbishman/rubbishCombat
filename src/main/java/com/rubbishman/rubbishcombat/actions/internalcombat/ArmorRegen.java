package com.rubbishman.rubbishcombat.actions.internalcombat;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class ArmorRegen {
    public final Identifier target;

    public ArmorRegen(Identifier target) {
        this.target = target;
    }
}
