package org.jullill;

import java.util.ArrayList;
import java.util.List;

public final class CardGame {
    private static final int MAX_HAND = 6;

    private final List<Player> playerList = new ArrayList<Player>();
    private final Deck deck = new Deck();
    private int currentPlayerKey;
    private final List<Card> cardList = new ArrayList<Card>();

    public static void main(String[] args) {
        CardGame game = new CardGame();
        game.setUpGame(2);
        //game.startPlaying();
    }

    private void setUpGame(int playerCount) {
        deck.shuffle();
        for (int i = 0; i < playerCount; i++) {
            Player player = new Player("Player" + (i + 1));
            System.out.println(player.getName());
            for (int j = 0; j < MAX_HAND; j++) {
                Card card = deck.getCard();
                player.addCard(card);
                System.out.println(card.getName());
            }
            playerList.add(player);
        }
        currentPlayerKey = 0;
    }

    private void startPlaying() {
        final int beginPlayerKey = currentPlayerKey != 0 ? currentPlayerKey - 1: playerList.size();
        final Player beginPlayer = playerList.get(beginPlayerKey);
        Card card = beginPlayer.run();
        Player currentPlayer = playerList.get(currentPlayerKey);
        Card newCard = currentPlayer.run(card);
    }
}
