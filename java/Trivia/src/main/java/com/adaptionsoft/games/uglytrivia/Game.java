package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;


public class Game {

    private static final int MAX_ROUNDS = 50;
    public static final int LAST_PLACE = 11;
    private static final int MAX_PLAYERS = 6;


    private final ArrayList<Player> players = new ArrayList<>();
    //private final ArrayList<Integer> playersPositions = new ArrayList<>(MAX_PLAYERS);
    private final ArrayList<Integer> playersPurses = new ArrayList<>(MAX_PLAYERS);
    private final ArrayList<Boolean> inPenaltyBox = new ArrayList<>(MAX_PLAYERS);

    private final ArrayList<String> popQuestions = new ArrayList<>();
    private final ArrayList<String> scienceQuestions = new ArrayList<>();
    private final ArrayList<String> sportsQuestions = new ArrayList<>();
    private final ArrayList<String> rockQuestions = new ArrayList<>();


    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public Game() {
        for (int i = 0; i < MAX_ROUNDS; i++) {
            popQuestions.add("Pop Question " + i);
            scienceQuestions.add(("Science Question " + i));
            sportsQuestions.add(("Sports Question " + i));
            rockQuestions.add("Rock Question " + i);
        }
    }

    public boolean AddPlayer(String playerName) {


        players.add(new Player(playerName));
        playersPurses.add(0);
        inPenaltyBox.add(false);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public int howManyPlayers() {
        return players.size();
    }

    public void roll(int diceRoll) {
        System.out.println(players.get(currentPlayer).getName() + " is the current player");
        System.out.println("They have rolled a " + diceRoll);

        if (inPenaltyBox.get(currentPlayer)) {
            if (diceRoll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer).getName() + " is getting out of the penalty box");
                movePlayerAndAskQuestion(diceRoll);
                return;
            }
            System.out.println(players.get(currentPlayer).getName() + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
            return;
        }
        movePlayerAndAskQuestion(diceRoll);
    }

    private void movePlayerAndAskQuestion(int diceRoll) {
        players.get(currentPlayer).movePosition(diceRoll);
        System.out.println(players.get(currentPlayer).getName()
                + "'s new location is "
                + players.get(currentPlayer).getPosition());
        System.out.println("The category is " + currentCategory());
        askQuestion();
    }


    private void askQuestion() {

        switch (currentCategory()) {
            case "Pop" -> System.out.println(popQuestions.remove(0));
            case "Science" -> System.out.println(scienceQuestions.remove(0));
            case "Sports" -> System.out.println(sportsQuestions.remove(0));
            case "Rock" -> System.out.println(rockQuestions.remove(0));
        }
    }

    private String currentCategory() {
        return switch (players.get(currentPlayer).getPosition()) {
            case 0, 4, 8 -> "Pop";
            case 1, 5, 9 -> "Science";
            case 2, 6, 10 -> "Sports";
            default -> "Rock";
        };
    }


    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox.get(currentPlayer)) {
            if (isGettingOutOfPenaltyBox) {
                return earnCoinAndSwitchPlayerReturnIfWin();
            } else {
                switchPlayer();
                return true;
            }

        } else {
            return earnCoinAndSwitchPlayerReturnIfWin();
        }
    }

    private boolean earnCoinAndSwitchPlayerReturnIfWin() {
        System.out.println("Answer was correct!!!!");

        playersPurses.set(currentPlayer, playersPurses.get(currentPlayer) + 1);
        System.out.println(players.get(currentPlayer).getName()
                + " now has "
                + playersPurses.get(currentPlayer)
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        switchPlayer();

        return winner;
    }

    private void switchPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public boolean wasWronglyAnswered() {
        System.out.println("Question was incorrectly answered");
        sendPlayerInPenaltyBox();
        switchPlayer();
        return true;
    }

    private void sendPlayerInPenaltyBox() {
        System.out.println(players.get(currentPlayer).getName() + " was sent to the penalty box");
        inPenaltyBox.set(currentPlayer, true);
    }


    private boolean didPlayerWin() {
        return !(playersPurses.get(currentPlayer) == 6);
    }
}
