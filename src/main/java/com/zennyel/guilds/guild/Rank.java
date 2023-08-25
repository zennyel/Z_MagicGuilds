package com.zennyel.guilds.guild;

public enum Rank {
    F(1), E(2), D(3), C(4), B(5), A(6), S(7);
    Rank(int value) {
        this.value = value;
    }
    private int value;
    public int getValue() {
        return value;
    }



}
