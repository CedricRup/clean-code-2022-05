package com.adaptionsoft.games.uglytrivia;

public class Player {
    private String name;
    private int position = 0;


    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getPosition() {
        return position;
    }

    public void movePosition(int diceRoll) {
        position += diceRoll;

        if (position > Game.LAST_PLACE) {
            position = position - (Game.LAST_PLACE + 1);
        }

    }
}
