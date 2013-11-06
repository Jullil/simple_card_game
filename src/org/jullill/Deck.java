package org.jullill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final static String[] CARD_SUITS = {"Hearts", "Diamonds", "Clubs", "Peak"};
    private final List<Card> cardList = new ArrayList<Card>();

    public Deck() {
        for (CardSuit suite : CardSuit.values()) {
            for (CardValue value : CardValue.values()) {
                cardList.add(new Card(suite, value));
            }
        }
    }

    public Card getCard() {
        return cardList.remove(0);
    }

    public void shuffle() {
        Collections.shuffle(cardList);
    }
}
