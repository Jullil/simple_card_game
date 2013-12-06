package org.jullill;

import java.util.ArrayList;
import java.util.List;

public final class CardGame {
    private static final int MIN_PLAYER_CARD = 6;
    private static final int MAX_CHIPPED_CARD = 6;

    private final GameContext gameContext = new GameContext();
    private final List<Player> playerList = new ArrayList<Player>();
    private final List<Card> playedCardList = new ArrayList<Card>();
    private final List<Card> gameCardList = new ArrayList<Card>();
    private final Deck deck = new Deck();

    private int beginPlayerKey = 0;

    public static void main(String[] args) {
        //for (int i = 0; i < 1000; i++) {
            //System.out.println("00000000000000000000000000000000000000000000000000000000000000000000000000000000000---------------------------" + i);
            CardGame game = new CardGame();
            game.setUpGame(6);
            game.startGame();
        //}
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
        Card lowTrumpCard = null;
        for (Player player : playerList) {
            final Card playerLowTrumpCard = player.getLowTrumpCard();
            System.out.println("player " + player + " trump card " + playerLowTrumpCard + "+++++++++");
            if (playerLowTrumpCard != null && (lowTrumpCard == null || playerLowTrumpCard.compareTo(lowTrumpCard) < 0)) {
                lowTrumpCard = playerLowTrumpCard;
                beginPlayer = player;
            }
        }
        System.out.println("Begin player " + beginPlayer + "+++++++++");
        if (beginPlayer != null) {
            beginPlayerKey = playerList.indexOf(beginPlayer);
        }
    }

    public void startGame() {
        boolean continueGame = true;
        while (continueGame){
            continueGame = newRound();
        }
        System.out.println("Played card count " + playedCardList.size());
        System.out.println("Game card count " + gameCardList.size());
        for (Player player : playerList) {
            System.out.println("Player " + player + " LOSE with " + player.getCardCount() + " cards on hand!!!!");
        }
    }

    private boolean newRound() {
        resetPlayersState();

        final int currentPlayerKey = (beginPlayerKey + 1) % playerList.size();
        final Player currentPlayer = playerList.get(currentPlayerKey);

        int deckCardCount = deck.getCardCount();
        int i = 0;
        int playerPassCount = 0;
        int chippedCardCount = 0;
        while (currentPlayer.getCardCount() != 0 && playerPassCount < playerList.size() - 1 && chippedCardCount <= MAX_CHIPPED_CARD) {
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

            if (playerCard != null) {
                gameCardList.add(playerCard);
                System.out.println(player.getName() + " card = " + playerCard);
                Card currentPlayerCard = currentPlayer.struggle(playerCard);

                if (currentPlayerCard != null) {
                    gameCardList.add(currentPlayerCard);
                    chippedCardCount++;
                }
                System.out.println("Current player " + currentPlayer.getName() + " card = " + currentPlayerCard);
                System.out.println("--------------------------------");
            } else {
                playerPassCount++;
            }
            if (deckCardCount == 0) {
                if (player.getCardCount() == 0) {
                    playerList.remove(player);
                }
                if (currentPlayer.getCardCount() == 0) {
                    playerList.remove(currentPlayer);
                }
                if (playerList.size() <= 1) {
                    return false;
                }
            }
        }
        if (currentPlayer.isPass()) {
            currentPlayer.addCard(gameCardList);
        } else {
            playedCardList.addAll(gameCardList);
        }
        gameCardList.clear();
        dealCards();

        beginPlayerKey = currentPlayer.isPass() ? beginPlayerKey = (currentPlayerKey + 1) % playerList.size() : currentPlayerKey;

        return true;
    }

    /**
     * Раздать карты
     */
    public void dealCards() {
        if (deck.getCardCount() > 0) {
            final int playerCount = playerList.size();
            final int currentPlayerKey = (beginPlayerKey + 1) % playerCount;
            for (int i = 0; i <= playerList.size(); i++) {
                int playerKey;
                if (i != playerCount) {
                    playerKey = (beginPlayerKey + i) % playerList.size();
                    if (playerKey == currentPlayerKey) {
                        continue;
                    }
                } else {
                    playerKey = currentPlayerKey;
                }
                Player player = playerList.get(playerKey);
                System.out.println("Player " + player + " get card");

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
