package com.rubbishman.rubbishcombat.helper;

import com.rubbishman.rubbishcombat.LimitedAttribute;

public class LimitedAttributeHelper {
    public float percentOfMax(LimitedAttribute attr) {
        return attr.maximum == 0 ? 0 : attr.current / attr.maximum;
    }
}
