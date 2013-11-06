package org.jullill;

public enum CardValue {
    SIX("6"), SEVEN("7"), EIGHT("8"), NINE("9"), TEN("10"), JACK("J"), QUEEN("Q"), KING("K"), ACE("A");

    private final String name;

    private CardValue(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
