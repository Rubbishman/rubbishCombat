package com.rubbishman.rubbishcombat.actions.internalcombat;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class ArmorFatigueRegen {
    public final Identifier target;

    public ArmorFatigueRegen(Identifier target) {
        this.target = target;
    }
}
