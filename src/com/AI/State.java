package com.AI;

public class State {
    private int[] side1 = new int[6];
    private int[] side2 = new int[6];
    private int goal1 = 0, goal2 = 0;
    private int turn;

    public State(int[] side1, int[] side2, int goal1, int goal2, int turn) {
        this.side1 = side1;
        this.side2 = side2;
        this.goal1 = goal1;
        this.goal2 = goal2;
        this.turn = turn;
    }

    public int[] getSide1() {
        return side1;
    }

    public int[] getSide2() {
        return side2;
    }

    public int getGoal1() {
        return goal1;
    }

    public int getGoal2() {
        return goal2;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
