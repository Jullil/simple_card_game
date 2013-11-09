package org.jullill;

public class Card implements Comparable<Card>{
    private CardSuit  suite;
    private CardValue value;

    public Card(CardSuit suit, CardValue value) {
        this.suite = suit;
        this.value = value;
    }

    public CardSuit getSuite() {
        return suite;
    }

    public CardValue getValue() {
        return value;
    }

    public String getName() {
        return value + " " + suite;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public int compareTo(Card card) {
        if (card == null) {
            return 1;
        }
        return value.ordinal() - card.value.ordinal();
    }
}
