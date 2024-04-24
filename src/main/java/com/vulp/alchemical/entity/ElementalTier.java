package com.vulp.alchemical.entity;

public enum ElementalTier {
    ELEMENTAL(1),
    FUSION(2),
    PRIMAL(3),
    ARCANE(4);

    private final int value;
    ElementalTier(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
