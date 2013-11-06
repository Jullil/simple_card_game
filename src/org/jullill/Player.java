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
     public Card go(List<Card> gameCardList) {
        for (Card gameCard : gameCardList) {
           for (Card card : cardList) {
               if (card.compareTo(gameCard) == 0) {
                   cardList.remove(card);
                   return card;
               }
           }
        }
        return cardList.remove(0);
    }

    /**
     * Отбить карту другого игрока
     *
     * @param currentCard
     *
     * @return Card
     */
    public Card struggle(Card currentCard) {
        Card resultCard = null;
        for (Card card : cardList) {
            if (currentCard.getSuite() == card.getSuite() && currentCard.compareTo(card) < 0 && card.compareTo(resultCard) < 0) {
                resultCard = card;
            }
        }
        return resultCard;
    }

    public String getName() {
        return name;
    }
}
