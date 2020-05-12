package com.rubbishman.rubbishcombat.actions.internalcombat;

import com.rubbishman.rubbishRedux.external.operational.store.Identifier;

public class DodgeDamageOTResolution {
    public final Identifier target;

    public DodgeDamageOTResolution(Identifier target) {
        this.target = target;
    }
}
