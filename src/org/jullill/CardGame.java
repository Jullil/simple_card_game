package org.jullill;

import java.util.ArrayList;
import java.util.List;

public final class CardGame {
    private static final int MAX_HAND = 6;

    private final List<Player> playerList = new ArrayList<Player>();
    private final Deck deck = new Deck();
    private final List<Card> gameCardList = new ArrayList<Card>();

    private int currentPlayerKey;

    public static void main(String[] args) {
        CardGame game = new CardGame();
        game.setUpGame(5);
        game.startPlaying();
    }

    private void setUpGame(int playerCount) {
        deck.shuffle();

        for (int i = 0; i < playerCount; i++) {
            final Player player = new Player(String.format("Player%d", i + 1));
            System.out.println(player.getName());

            for (int j = 0; j < MAX_HAND; j++) {
                final Card card = deck.getCard();
                player.addCard(card);
                System.out.println(card.getName());
            }

            playerList.add(player);
        }
    }

    private void startPlaying() {
        final Player currentPlayer = playerList.get(currentPlayerKey);
        final int beginPlayerKey = currentPlayerKey == 0 ? playerList.size() - 1 : currentPlayerKey - 1;

        for (int i = 0; i < playerList.size(); i++) {
            final int playerKey = (beginPlayerKey + i) % playerList.size();

            if (playerKey == currentPlayerKey) {
               continue;
            }

            System.out.println("Current player " + playerKey);
            final Player player = playerList.get(playerKey);
            Card payerCard = player.go(gameCardList);
            gameCardList.add(payerCard);
            System.out.println("Player card " + payerCard);
            Card currentPlayerCard = currentPlayer.struggle(payerCard);
            gameCardList.add(currentPlayerCard);
            System.out.println("Current player card " + currentPlayerCard);
        }
    }
}
