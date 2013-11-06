package org.jullill;

public enum CardSuit {
    HEARTS("Hearts"), DIAMONDS("Diamonds"), CLUBS("Clubs"), PEAK("Peak");

    private final String name;

    private CardSuit(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
