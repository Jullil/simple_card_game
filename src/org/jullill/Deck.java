package org.jullill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
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

    public int getCardCount() {
        return cardList.size();
    }

    /**
     * Перемешивает колоду и выдает козырь
     *
     * @return CardSuit
     */
    public CardSuit shuffle() {
        Collections.shuffle(cardList);

        int trumpSuiteKey = (int) Math.round(Math.random() * 10) % 4;
        return CardSuit.values()[trumpSuiteKey];
    }
}
