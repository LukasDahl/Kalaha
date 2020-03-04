package com.AI;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        int[] initSide = {6,6,6,6,6,6};

        State initState = new State(initSide, initSide.clone(), 0, 0 , 1);
        State currentState = initState;
        printBoard(initState);
        Scanner input = new Scanner(System.in);
        int choice;
        while(true){
            while (true) {
                choice = input.nextInt();
                if (choice >= 0 && choice <= 5)
                    break;
            }
            if (currentState.getTurn() == 2){
                choice += 7;
            }
            currentState = turnOutcome(currentState, choice);
            printBoard(currentState);

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

    public static State turnOutcome(State state, int cup){
        int hand;
        int[] side1 = state.getSide1().clone();
        int[] side2 = state.getSide2().clone();
        int goal1 = state.getGoal1(), goal2 = state.getGoal2();
        int handPosition;
      //  System.out.println(state.getTurn() + "");
        if (cup < 6){

            hand = side1[cup];
            side1[cup] = 0;
            handPosition = cup;
        }
        else {
            hand = side2[cup - 7];
            side2[cup - 7] = 0;
            handPosition = cup;
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

        //DEFINE A STATE
        State tempState = new State(side1.clone(), side2.clone(), goal1, goal2, state.getTurn());

        if (handPosition < 6){
            if (side1[handPosition] > 1){
                tempState = turnOutcome(tempState, handPosition);
            }
            else tempState.setTurn(changeTurn(state.getTurn()));
        }
        else if (handPosition < 7){

        }
        else if (handPosition < 13){
            if (side2[handPosition-7] > 1){
                tempState = turnOutcome(tempState, handPosition);
            }
            else tempState.setTurn(changeTurn(state.getTurn()));

        }
        else {

        }

        return tempState;
    }

    public static int changeTurn(int turn){
        turn = 1 + (1 - (turn - 1));
        return  turn;
    }

}
