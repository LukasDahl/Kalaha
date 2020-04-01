package com.AI;

import java.util.ArrayList;
import java.util.List;

public class State {
    private int[] side1 = new int[6];
    private int[] side2 = new int[6];
    private int goal1 = 0, goal2 = 0;
    private int turn;
    private int depth;
    private int value;

    public State(int[] side1, int[] side2, int goal1, int goal2, int turn, int depth, int value) {
        this.side1 = side1;
        this.side2 = side2;
        this.goal1 = goal1;
        this.goal2 = goal2;
        this.turn = turn;
        this.depth = depth;
        this.value = value;
    }

    public int[] getSide1() {
        return side1;
    }

    public void setSide1(int[] side1) {
        this.side1 = side1;
    }

    public int[] getSide2() {
        return side2;
    }

    public void setSide2(int[] side2) {
        this.side2 = side2;
    }

    public int getGoal1() {
        return goal1;
    }

    public void setGoal1(int goal1) {
        this.goal1 = goal1;
    }

    public int getGoal2() {
        return goal2;
    }

    public void setGoal2(int goal2) {
        this.goal2 = goal2;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
