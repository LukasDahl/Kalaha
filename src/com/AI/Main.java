package com.AI;

public class Main {

    public static void main(String[] args) {
        int[] hjælp = {0,0,0,0,0,0};

        State test = new State(hjælp, hjælp, 2, 3 , 1);
        printBoard(test);


        while(true){


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

    }

    public static State turnOutcome(State state, int cup){
        int hand;
        int[] side1 = state.getSide1().clone();
        int[] side2 = state.getSide2().clone();
        int goal1 = state.getGoal1(), goal2 = state.getGoal2();
        int handPosition;
        if (state.getTurn() == 1){
            hand = side1[cup];
            side1[cup] = 0;
            handPosition = cup + 1;
        }
        else {
            hand = side2[cup];
            side2[cup] = 0;
            handPosition = cup + 7 + 1;
        }

        while(hand > 0){
            handPosition = handPosition % 14;




            handPosition++;
            hand--;
        }

        int newTurn = changeTurn(state.getTurn());



        return null;
    }

    public static int changeTurn(int turn){
        turn = 1 + (1 - (turn - 1));
        return  turn;
    }

}
