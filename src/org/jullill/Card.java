package org.jullill;

public class Card {
    private String suite;
    private String value;

    public Card(String suit, String value) {
        this.suite = suit;
        this.value = value;
    }

    public String getSuite() {
        return suite;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return value + " " + suite;
    }

    public int compare(Card card) {
        return 0;
    }
}
