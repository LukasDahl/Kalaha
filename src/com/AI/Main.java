package com.AI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int DEPTH = 14;
    static int bestChoice = 0;
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String[] ANSI_TURN = {"\u001B[32m", "\u001B[34m"};
    public static void main(String[] args) {
        int[] initSide = {6,6,6,6,6,6};

        State initState = new State(initSide, initSide.clone(), 0, 0 , 1, 0, 0);
        State currentState = initState, tempState, workingState;
        printBoard(initState);
        Scanner input = new Scanner(System.in);
        int choice;
        boolean gameover;
        List<State> frontier = new ArrayList<>();
        List<Integer> childResults;


        while(true){


            if (currentState.getTurn() == 1) {
                while (true) {
                    try {
                        choice = input.nextInt() - 1;
                    } catch (InputMismatchException e) {
                        choice = 6;
                        input = new Scanner(System.in);
                    }
                    if (choice >= 0 && choice <= 5){
                        if (currentState.getSide1()[choice] == 0){
                            System.out.printf("%sChoose a pocket with balls in%s\n", ANSI_RED, ANSI_RESET);
                            continue;
                        }
                        break;
                    }
                    System.out.printf("%sChoose a pocket between 1 and 6%s\n", ANSI_RED, ANSI_RESET);

                }
                currentState = turnOutcome(currentState, choice);
            }
            else{
                //AI HERE PLS
                System.out.printf("%sPlease wait while I calculate%s\n", ANSI_TURN[1], ANSI_RESET);
                workingState = new State(
                        currentState.getSide1().clone(),
                        currentState.getSide2().clone(),
                        currentState.getGoal1(),
                        currentState.getGoal2(),
                        currentState.getTurn(),
                        currentState.getDepth(),
                        currentState.getValue()
                );
                workingState.setDepth(0);
                minimax(workingState, -72, 72);

                System.out.printf("%sChoosing %d as this is the freaking best%s\n", ANSI_TURN[1], (bestChoice + 1), ANSI_RESET);
                currentState = turnOutcome(currentState, bestChoice + 7);


            }

            printBoard(currentState);


            gameover = true;
            for (int i: currentState.getSide1()){
                if(i > 0){
                    gameover = false;
                }
            }
            if (gameover){
                break;
            }

            gameover = true;
            for (int i: currentState.getSide2()){
                if(i > 0){
                    gameover = false;
                }
            }
            if (gameover){
                break;
            }

        }

    }

    public static void printBoard(State state){
        System.out.printf(" ");
        for (int i = 5; i >= 0; i-- ) {
            System.out.printf("%3d", state.getSide2()[i]);
        }
        System.out.printf("\n%2d                 %2d\n ", state.getGoal2(), state.getGoal1());
        for (int i: state.getSide1()) {
            System.out.printf("%3d", i);
        }
        System.out.printf("\n%sIt is now player %d's turn%s\n", ANSI_TURN[state.getTurn()-1], state.getTurn(), ANSI_RESET);

    }

    public static State turnOutcome(State state, int pocket){
        int hand;
        int[] side1 = state.getSide1().clone();
        int[] side2 = state.getSide2().clone();
        int goal1 = state.getGoal1(), goal2 = state.getGoal2();
        int handPosition;
        if (pocket < 6){

            hand = side1[pocket];
            side1[pocket] = 0;
            handPosition = pocket;
        }
        else {
            hand = side2[pocket - 7];
            side2[pocket - 7] = 0;
            handPosition = pocket;
        }

        while(hand > 0){
            handPosition++;
            handPosition = handPosition % 14;

            //On player 1's side
            if (handPosition < 6){
                side1[handPosition] += 1;
            }

            //On player 1's goal
            else if (handPosition < 7){
                //Player 1's turn
                if (state.getTurn() == 1){
                    goal1 += 1;
                }
                //Player 2's turn
                else{
                    hand++;
                }
            }

            //On player 2's side
            else if (handPosition < 13){
                side2[handPosition - 7] += 1;
            }

            //On player 2's goal
            else {
                //Player 2's turn
                if (state.getTurn() == 2){
                    goal2 += 1;
                }
                //Player 1's turn
                else{
                    hand++;
                }
            }


            hand--;
        }

        int nextTurn = state.getTurn();

        if (handPosition < 6){
            if (side1[handPosition] == 1){
                if (state.getTurn() == 1){
                    goal1 += side1[handPosition] + side2[5-handPosition];
                    side1[handPosition] = 0;
                    side2[5-handPosition] = 0;
                }
            }
            nextTurn = changeTurn(state.getTurn());
        }
        else if (handPosition < 7){

        }
        else if (handPosition < 13){
            if (side2[handPosition-7] == 1){
                if (state.getTurn() == 2){
                    goal2 += side2[handPosition-7] + side1[5-(handPosition-7)];
                    side2[handPosition-7] = 0;
                    side1[5-(handPosition-7)] = 0;
                }
            }
            nextTurn = changeTurn(state.getTurn());

        }
        else {

        }

        //DEFINE A STATE
        State tempState = new State(side1.clone(), side2.clone(), goal1, goal2, nextTurn, state.getDepth() + 1, goal2 - goal1);

        boolean gameover = true;
        for (int i: tempState.getSide1()){
            if(i > 0){
                gameover = false;
            }
        }
        if (gameover){
            for (int i = 0; i < tempState.getSide2().length; i++) {
                if (state.getTurn() == 1){
                    tempState.setGoal1(tempState.getGoal1() + tempState.getSide2()[i]);
                }
                else{
                    tempState.setGoal2(tempState.getGoal2() + tempState.getSide2()[i]);
                }
                tempState.getSide2()[i] = 0;
            }
            tempState.setValue(tempState.getGoal2()-tempState.getGoal1());
        }

        gameover = true;
        for (int i: tempState.getSide2()){
            if(i > 0){
                gameover = false;
            }
        }
        if (gameover){
            for (int i = 0; i < tempState.getSide1().length; i++) {
                if (state.getTurn() == 1){
                    tempState.setGoal1(tempState.getGoal1() + tempState.getSide1()[i]);
                }
                else{
                    tempState.setGoal2(tempState.getGoal2() + tempState.getSide1()[i]);
                }
                tempState.getSide1()[i] = 0;
            }
            tempState.setValue(tempState.getGoal2()-tempState.getGoal1());
        }

        return tempState;
    }

    public static int changeTurn(int turn){
        turn = 1 + (1 - (turn - 1));
        return  turn;
    }

    public static int minimax(State workingState, int α, int β){
        State tempState;
        List<Integer> childResults = new ArrayList<>();
        int bestPocket = 0;

        if (workingState.getDepth() == DEPTH){
            return workingState.getValue();
        }

        int value;
        int tempValue;
        int possibleMoves = 0;

        if (workingState.getTurn() == 1){
            value = 72;

            for (int pocket = 0; pocket< workingState.getSide2().length; pocket++){

                if (workingState.getSide2()[pocket] == 0) {
                    continue;
                }

                possibleMoves++;

                tempState = turnOutcome(workingState, pocket);

                tempValue = minimax(tempState, α, β);

                if (value > tempValue) {
                    value = tempValue;
                    bestPocket = pocket;
                }

                if (β > value)
                    β = value;

                if (α >= β)
                    break; // α cutoff

            }

        }
        else{
            value = -72;
            for (int pocket = 0; pocket< workingState.getSide2().length; pocket++){

                if (workingState.getSide2()[pocket] == 0) {
                    continue;
                }

                possibleMoves++;

                tempState = turnOutcome(workingState, pocket + 7);

                tempValue = minimax(tempState, α, β);

                if (value < tempValue) {
                    value = tempValue;
                    bestPocket = pocket;
                }

                if (α < value)
                    α = value;

                if (α >= β)
                    break; // β cutoff

            }
        }
        if (possibleMoves == 0){
            System.out.println("HERE"+ workingState.getValue());
            return workingState.getValue();
        }
        bestChoice = bestPocket;
        return value;
    }


}
