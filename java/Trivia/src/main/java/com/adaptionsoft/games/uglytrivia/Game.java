package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;


public class Game {

    private static final int MAX_ROUNDS = 50;
    private static final int LAST_PLACE = 11;
    private static final int MAX_PLAYERS = 6;


    private final ArrayList<String> players = new ArrayList<>();
    private final ArrayList<Integer> playersPositions = new ArrayList<>(MAX_PLAYERS);
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

        players.add(playerName);
        playersPositions.add(0);
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
        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + diceRoll);

        if (inPenaltyBox.get(currentPlayer)) {
            if (diceRoll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;
                System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
                movePlayerAndAskQuestion(diceRoll);
                return;
            }
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
            return;
        }
        movePlayerAndAskQuestion(diceRoll);
    }

    private void movePlayerAndAskQuestion(int diceRoll) {
        playersPositions.set(currentPlayer, playersPositions.get(currentPlayer) + diceRoll);
        if (playersPositions.get(currentPlayer) > LAST_PLACE) {
            playersPositions.set(currentPlayer, playersPositions.get(currentPlayer) - (LAST_PLACE + 1));
        }
        System.out.println(players.get(currentPlayer)
                + "'s new location is "
                + playersPositions.get(currentPlayer));
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
        return switch (playersPositions.get(currentPlayer)) {
            case 0, 4, 8 -> "Pop";
            case 1, 5, 9 -> "Science";
            case 2, 6, 10 -> "Sports";
            default -> "Rock";
        };
    }


    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox.get(currentPlayer)) {
            if (isGettingOutOfPenaltyBox) {
                return nomALCon();
            } else {
                currentPlayer++;
                if (currentPlayer == players.size()) currentPlayer = 0;
                return true;
            }

        } else {
            return nomALCon();
        }
    }

    private boolean nomALCon() {
        System.out.println("Answer was correct!!!!");

        playersPurses.set(currentPlayer, playersPurses.get(currentPlayer) + 1);
        System.out.println(players.get(currentPlayer)
                + " now has "
                + playersPurses.get(currentPlayer)
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox.set(currentPlayer, true);

        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
        return true;
    }


    private boolean didPlayerWin() {
        return !(playersPurses.get(currentPlayer) == 6);
    }
}
