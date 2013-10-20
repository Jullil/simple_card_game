package org.jullill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private final static String[] CARD_SUITS = {"Hearts", "Diamonds", "Clubs", "Peak"};
    private final static String[] CARD_VALUES = {"6", "7", "8", "9", "10", "J", "Q", "K", "A"};
    private final List<Card> cardList = new ArrayList<Card>();

    public Deck() {
        for (int i = 0; i < CARD_SUITS.length; i++) {
            for (int j = 0; j < CARD_VALUES.length; j++) {
                cardList.add(new Card(CARD_SUITS[i], CARD_VALUES[j]));
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
