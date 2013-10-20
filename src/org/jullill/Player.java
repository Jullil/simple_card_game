package org.jullill;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private final List<Card> cardList = new ArrayList<Card>();

    public Player(String name) {
        this.name = name;
    }

    public void addCard(Card card) {
        cardList.add(card);
    }

    /**
     * Сделать ход против другого игрока
     *
     * @return Card
     */
    public Card run() {
        return cardList.remove(0);
    }

    /**
     * Отбить карту другого игрока
     *
     * @param currentCard
     *
     * @return Card
     */
    public Card run(Card currentCard) {
        Card resultCard = null;
        for (Card card : cardList) {
            if (currentCard.compare(card) == -1 && card.compare(resultCard) == -1) {
                resultCard = card;
            }
        }
        return resultCard;
    }

    public String getName() {
        return name;
    }
}
