package org.jullill;

import java.util.ArrayList;
import java.util.List;

public final class CardGame {
    private static final int MIN_PLAYER_CARD = 6;

    private final GameContext gameContext = new GameContext();
    private final List<Player> playerList = new ArrayList<Player>();
    private final List<Card> gameCardList = new ArrayList<Card>();
    private final Deck deck = new Deck();

    private int beginPlayerKey = 0;

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000000000---------------------------" + i);
            CardGame game = new CardGame();
            game.setUpGame(3);
            game.start();
        }
    }

    private void setUpGame(int playerCount) {
        gameContext.trumpSuit = deck.shuffle();
        gameContext.playerCount = playerCount;

        System.out.println("Trump " + gameContext.trumpSuit + "+++++++++");

        for (int i = 0; i < playerCount; i++) {
            final Player player = new Player(String.format("Player%d", i + 1), gameContext);
            playerList.add(player);
        }

        dealCards();

        Player beginPlayer = null;
        Card seniorTrumpCard = null;
        for (Player player : playerList) {
            final Card playerSeniorTrumpCard = player.getSeniorTrumpCard();
            System.out.println("player " + player + " trump card " + playerSeniorTrumpCard + "+++++++++");
            if (playerSeniorTrumpCard != null && playerSeniorTrumpCard.compareTo(seniorTrumpCard) > 0) {
                seniorTrumpCard = playerSeniorTrumpCard;
                beginPlayer = player;
            }
        }
        System.out.println("Begin player " + beginPlayer + "+++++++++");
        if (beginPlayer != null) {
            beginPlayerKey = playerList.indexOf(beginPlayer);
        }
    }

    public void start() {
        Player winner = null;
        while ( winner == null){
            winner = newRound();
        }

        System.out.println("Player " + winner + " WIN!!!!!!!!!!!!!!!!!!!");
    }

    private Player newRound() {
        resetPlayersState();

        final int currentPlayerKey = (beginPlayerKey + 1) % playerList.size();
        final Player currentPlayer = playerList.get(currentPlayerKey);

        int deckCardCount = deck.getCardCount();
        int i = 0;
        int playerPassCount = 0;
        while (currentPlayer.getCardCount() != 0 && playerPassCount < playerList.size() - 1) {
            System.out.println("Count pass " + playerPassCount + "====================");
            final int playerKey = (beginPlayerKey + i) % playerList.size();

            i = (i + 1) % playerList.size();
            if (i == 0) {
                playerPassCount = 0;
            }

            if (playerKey == currentPlayerKey) {
                continue;
            }

            final Player player = playerList.get(playerKey);

            Card playerCard = player.go(gameCardList);
            if (deckCardCount == 0 && player.getCardCount() == 0) {
                return player;
            }

            if (playerCard != null) {
                gameCardList.add(playerCard);
                System.out.println(player.getName() + " card = " + playerCard);
                Card currentPlayerCard = currentPlayer.struggle(playerCard);
                if (deckCardCount == 0 && currentPlayer.getCardCount() == 0) {
                    return currentPlayer;
                }
                if (currentPlayerCard != null) {
                    gameCardList.add(currentPlayerCard);
                }
                System.out.println("Current player " + currentPlayer.getName() + " card = " + currentPlayerCard);
                System.out.println("--------------------------------");
            } else {
                playerPassCount++;
            }
        }
        if (currentPlayer.isPass()) {
            currentPlayer.addCard(gameCardList);
        }
        gameCardList.clear();
        dealCards();

        beginPlayerKey = currentPlayer.isPass() ? beginPlayerKey = (currentPlayerKey + 1) % playerList.size() : currentPlayerKey;

        return null;
    }

    /**
     * Раздать карты
     */
    public void dealCards() {
        if (deck.getCardCount() > 0) {
            for (int i = 0; i < playerList.size(); i++) {
                Player player = playerList.get((beginPlayerKey + i) % playerList.size());

                for (int j = player.getCardCount(); j < MIN_PLAYER_CARD; j++) {
                    if (deck.getCardCount() == 0) {
                        return;
                    }
                    final Card card = deck.getCard();
                    player.addCard(card);
                    System.out.println(card.getName());
                }
            }
        }
    }

    /**
     * Сбросить текущее стостояние всех игроков
     */
    public void resetPlayersState() {
        for (Player player : playerList) {
            player.resetState();
        }
    }

    public final class GameContext {
        private CardSuit trumpSuit;
        private int playerCount;

        CardSuit getTrumpSuit() {
            return trumpSuit;
        }

        public int getPlayerCount() {
            return playerCount;
        }
    }
}
