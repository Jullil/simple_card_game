package org.jullill;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private final List<Card> cardList = new ArrayList<Card>();

    private String name;
    private boolean pass;
    private CardGame.GameContext gameContext;

    public Player(String name, CardGame.GameContext gameContext) {
        this.name = name;
        this.gameContext = gameContext;
    }

    public void addCard(Card card) {
        cardList.add(card);
    }

    public void addCard(List<Card> cardList) {
        this.cardList.addAll(cardList);
        System.out.println("Current player take cards ==============");
        System.out.println(cardList);
    }

    public int getCardCount() {
        return cardList.size();
    }

    /**
     * Сделать ход против другого игрока
     *
     * @return Card
     */
     public Card go(List<Card> gameCardList) {
        if (cardList.isEmpty()) {
            return null;
        }
        //Первый ход игрока
        if (gameCardList.isEmpty()) {
            Card minCard = cardList.get(0);
            for (Card card : cardList) {
                if (minCard.compareTo(card) > 0) {
                    minCard = card;
                }
            }
            cardList.remove(minCard);
            return minCard;
        }
        for (Card gameCard : gameCardList) {
           for (Card card : cardList) {
               if (card.compareTo(gameCard) == 0) {
                   cardList.remove(card);
                   return card;
               }
           }
        }
        return null;
    }

    /**
     * Отбить карту другого игрока
     *
     * @param currentCard
     *
     * @return Card
     */
    public Card struggle(Card currentCard) {
        if (pass || cardList.isEmpty()) {
            return null;
        }
        Card resultCard = null;
        Card trumpCard = null;
        CardSuit trumpSuit = gameContext.getTrumpSuit();
        for (Card card : cardList) {
            if (currentCard.getSuite() == card.getSuite() && currentCard.compareTo(card) < 0 && (resultCard == null || card.compareTo(resultCard) < 0)) {
                resultCard = card;
            }
            if (currentCard.getSuite() != trumpSuit && card.getSuite() == trumpSuit && (trumpCard == null || card.compareTo(trumpCard) < 0)) {
                trumpCard = card;
            }
        }
        resultCard = resultCard == null ? trumpCard : resultCard;
        if (resultCard == null) {
            pass = true;
        }
        cardList.remove(resultCard);
        return resultCard;
    }

    /**
     * Получить младшую козырную карту
     *
     * @return Card
     */
    public Card getLowTrumpCard() {
        Card lowTrump = null;
        for (Card card : cardList) {
            if (card.getSuite() == gameContext.getTrumpSuit() && (lowTrump == null || card.compareTo(lowTrump) < 0)) {
                lowTrump = card;
            }
        }
        return lowTrump;
    }

    /**
     * Сбросить состояние игрока
     */
    public void resetState() {
        pass = false;
    }

    public String getName() {
        return name;
    }

    public Boolean isPass() {
        return pass;
    }

    @Override
    public String toString() {
        return getName();
    }
}
