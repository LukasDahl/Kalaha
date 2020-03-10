package com.AI;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {

    static int DEPTH = 10;
    static int bestChoice = 0;

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

                    if (choice >= 0 && choice <= 5)
                        break;
                }
                currentState = turnOutcome(currentState, choice);
            }
            else{
                //AI HERE PLS
                workingState = new State(
                        currentState.getSide1().clone(),
                        currentState.getSide2().clone(),
                        currentState.getGoal1(),
                        currentState.getGoal2(),
                        currentState.getTurn(),
                        currentState.getDepth(),
                        currentState.getValue()
                );

                minimax(workingState);

                System.out.printf("Choosing %d as this is the freaking best\n", (bestChoice + 1));
                currentState = turnOutcome(currentState, bestChoice + 7);


            }

            printBoard(currentState);
            DEPTH++;


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
        System.out.printf("\nIt is now player %d's turn\n", state.getTurn());

    }

    public static State turnOutcome(State state, int pocket){
        int hand;
        int[] side1 = state.getSide1().clone();
        int[] side2 = state.getSide2().clone();
        int goal1 = state.getGoal1(), goal2 = state.getGoal2();
        int handPosition;
      //  System.out.println(state.getTurn() + "");
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


        return tempState;
    }

    public static int changeTurn(int turn){
        turn = 1 + (1 - (turn - 1));
        return  turn;
    }

    public static int minimax(State workingState){
        State tempState;
        List<Integer> childResults = new ArrayList<>();

        if (workingState.getDepth() == DEPTH){
            return workingState.getValue();
        }
        for (int pocket = 0; pocket< workingState.getSide2().length; pocket++){
            if (workingState.getSide2()[pocket] == 0) {
                childResults.add(null);
                continue;
            }
            if (workingState.getTurn() == 1){
                tempState = turnOutcome(workingState, pocket);
                childResults.add(minimax(tempState));
            }
            else {
                tempState = turnOutcome(workingState, pocket + 7);
                childResults.add(minimax(tempState));
            }
            workingState.getChildren().add(tempState);
        }


        for (State state: workingState.getChildren()){

        }
        int result;
        if (workingState.getTurn() == 1){
            result = 72;
            for (int i = 0; i < childResults.size(); i++){
                if (childResults.get(i) != null) {
                    if (childResults.get(i) < result) {
                        result = childResults.get(i);
                        bestChoice = i;
                    }
                }
            }
        }
        else{
            result = -72;
            for (int i = 0; i < childResults.size(); i++){
                if (childResults.get(i) != null) {
                    if (childResults.get(i) > result) {
                        result = childResults.get(i);
                        bestChoice = i;
                    }
                }
            }
        }
        return result;
    }


}
