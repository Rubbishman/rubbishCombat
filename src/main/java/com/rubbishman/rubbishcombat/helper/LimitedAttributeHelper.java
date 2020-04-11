package com.rubbishman.rubbishcombat.helper;

import com.rubbishman.rubbishcombat.state.attribute.LimitedAttribute;

public class LimitedAttributeHelper {
    public float percentOfMax(LimitedAttribute attr) {
        return attr.maximum == 0 ? 0 : attr.current / attr.maximum;
    }
}
