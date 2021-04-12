import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static Random rand = new Random();
    private static boolean doesNotWork = false;
    private static String previousMove = "";
    public static void main(String[] args) {

        //This program will solve the famous missionaries and cannibals problem.
        //In the problem, there are three missionaries and three cannibals on one side of a river.
        //The goal is to get all of them across the river using a boat that can take up to two people across the river at a time.
        //The problem is, if there are more cannibals than the missionaries on either side of the bank, the cannibals will eat the missionaries.
        //This program will try to solve the question using GOFAI techniques (Good Old-Fashion Artificial Intelligence - loops and conditional statements).
        while(true) {
            ArrayList<Integer> numMS1 = new ArrayList<Integer>();
            //Number of missionaries on Side 1.
            ArrayList<Integer> numCS1 = new ArrayList<Integer>();
            //Number of cannibals on Side 1.
            ArrayList<Integer> numMS2 = new ArrayList<Integer>();
            //Number of missionaries on Side 2.
            ArrayList<Integer> numCS2 = new ArrayList<Integer>();
            //Number of cannibals on Side 2.
            numMS1.add(3);
            numCS1.add(3);
            numMS2.add(0);
            numCS2.add(0);
            //initializing the situation
            int n = 0;
            //n represents the turn number
            while (!(numMS2.get(n) == 3 && numCS2.get(n) == 3) && !doesNotWork) {
                //while loop only runs if the problem is not complete.

                int MS1 = numMS1.get(n);
                int CS1 = numCS1.get(n);
                int MS2 = numMS2.get(n);
                int CS2 = numCS2.get(n);
                //setting the variables to the current configuration of people on each side of the river.

                //This will change as the code progresses.
                if (n % 2 == 0) {
                    //Meaning that when the turn number is even, the people will be traveling down the river from side 1 to side 2.
                    boolean[] moveDownCombo = new boolean[5];
                    Arrays.fill(moveDownCombo, false);
                    if ((MS1 - 2 >= CS1 || MS1 - 2 == 0) && MS2+2 >= CS2 && MS1 >= 2) {
                        //Checks if moving two missionaries down the river is possible.
                        moveDownCombo[0] = true;
                        //Meaning carrying 2 missionaries down the river is possible.
                    }
                    if (MS1 == CS1) {
                        //This is because if MS1 is equal to CS1, then MS2 is equals to CS2, and taking one from each MS1/CS1 and adding them to MS2/CS2 would work in any case.
                        //For other situation where MS1 does not equal CS1, MS1 would have to be bigger than CS1, or MS1 would have to be zero.
                        //If MS1 is zero, then both MS2 and CS2 would be three, meaning the problem is solved.
                        //If MS1 is bigger than CS1, than CS2 is bigger than MS2.
                        //In this case, if one from each MS1 and CS1 go to the other side, the MS2 will be outnumbered by CS2. Thus, this case only works for MS1 equals CS1.
                        moveDownCombo[1] = true;
                        //This means that 1 missionary and 1 carnival can go across the river.
                    }
                    if ((MS2 >= CS2 + 2 || MS2 == 0) && CS1 >= 2) {
                        //Checking if two cannibals can move down the river.
                        moveDownCombo[2] = true;
                        //In this case, two cannibals will move down the river.
                    }
                    if ((MS1-1 >= CS1 || MS1-1 == 0) && MS2+1 >= CS2 && MS1 >= 1) {
                        //Checking if one missionary can travel down the river.
                       moveDownCombo[3] = true;
                    }
                    if ((MS2 >= CS2+1 || MS2 == 0) && CS1 >= 1) {
                        //Checking if one cannibal can travel down the river.
                        moveDownCombo[4] = true;
                    }
                    ArrayList<String> moveChoices = new ArrayList<String>();
                    if (moveDownCombo[0]) {
                        moveChoices.add("M2C0");
                    }
                    if (moveDownCombo[1]) {
                        moveChoices.add("M1C1");
                    }
                    if (moveDownCombo[2]) {
                        moveChoices.add("M0C2");
                    }
                    if (moveDownCombo[3]) {
                        moveChoices.add("M1C0");
                    }
                    if (moveDownCombo[4]) {
                        moveChoices.add("M0C1");
                    }
                    //Add the possible move choices to the ArrayList named moveChoices.
                    if (moveChoices.isEmpty()) {
                        //No possible moves, restarts the program.
                        doesNotWork = true;
                    }
                    else {
                        //Randomly chooses a move.
                        String moveChoice = moveChoices.get(rand.nextInt(moveChoices.size()));
                        //!previousMove.equals(Move) prevents the AI from selecting the move from its previous turn, a move that would not change nothing.
                        if(moveChoice.equals("M2C0") && !previousMove.equals("M2C0")) {
                            MS1 -= 2;
                            MS2 += 2;
                            System.out.println("Down M2C0");
                        }
                        else if(moveChoice.equals("M1C1") && !previousMove.equals("M1C1")) {
                            MS1 -= 1;
                            CS1 -= 1;
                            MS2 += 1;
                            CS2 += 1;
                            System.out.println("Down M1C1");
                        }
                        else if(moveChoice.equals("M0C2") && !previousMove.equals("M0C2")){
                            CS1 -= 2;
                            CS2 += 2;
                            System.out.println("Down M0C2");
                        }
                        else if(moveChoice.equals("M1C0") && !previousMove.equals("M1C0")) {
                            MS1 -= 1;
                            MS2 += 1;
                        }
                        else if(moveChoice.equals("M0C1") && !previousMove.equals("M0C1")) {
                            CS1 -= 1;
                            CS2 += 1;
                        }
                        else {
                            //No possible moves, restarts the program.
                            doesNotWork = true;
                        }
                        previousMove = moveChoice;
                    }
                }
                else {
                    //For going up the river.
                    boolean[] moveUpCombo = new boolean[5];
                    Arrays.fill(moveUpCombo, false);
                    if((MS2-2 >= CS2 || MS2-2 == 0) && MS1+2 >= CS1 && MS2 >= 2) {
                        //Moving two missionaries up the river.
                        moveUpCombo[0] = true;
                    }
                    if(MS2 == CS2 && MS2 >= 1) {
                       //Moving one missionary and one cannibal up the river.
                        moveUpCombo[1] = true;
                    }
                    if((MS1 >= CS1+2 || MS1 == 0) && CS2 >= 2) {
                       //Moving two cannibals up the river.
                       moveUpCombo[2] = true;
                    }
                    if((MS2-1 >= CS2 || MS2-1 == 0) && MS1+1 >= CS1 && MS2 >= 1) {
                        //Moving one missionary up the river.
                        moveUpCombo[3] = true;
                    }
                    if((MS1 >= CS1+1 || MS1 == 0) && CS2 >= 1) {
                        //Moving one cannibal up the river.
                        moveUpCombo[4] = true;
                    }
                    ArrayList<String> moveChoices = new ArrayList<String>();
                    if (moveUpCombo[0]) {
                        moveChoices.add("M2C0");
                    }
                    if (moveUpCombo[1]) {
                        moveChoices.add("M1C1");
                    }
                    if (moveUpCombo[2]) {
                        moveChoices.add("M0C2");
                    }
                    if (moveUpCombo[3]) {
                        moveChoices.add("M1C0");
                    }
                    if (moveUpCombo[4]) {
                        moveChoices.add("M0C1");
                    }
                    //Add the possible move choices to the ArrayList named moveChoices.
                    if (moveChoices.isEmpty()) {
                        //No possible moves, restarts the program.
                        doesNotWork = true;
                    }
                    else {
                        //Randomly chooses a move.
                        String moveChoice = moveChoices.get(rand.nextInt(moveChoices.size()));
                        //!previousMove.equals(Move) prevents the AI from selecting the move from its previous turn, a move that would not change nothing.
                        if(moveChoice.equals("M2C0") && !previousMove.equals("M2C0")) {
                            MS2 -= 2;
                            MS1 += 2;
                            System.out.println("Up M2C0");
                        }
                        else if(moveChoice.equals("M1C1") && !previousMove.equals("M1C1")) {
                            MS2 -= 1;
                            MS1 += 1;
                            CS2 -= 1;
                            CS1 += 1;
                            System.out.println("Up M1C1");
                        }
                        else if(moveChoice.equals("M0C2") && !previousMove.equals("M0C2")){
                            CS2 -= 2;
                            CS1 += 2;
                            System.out.println("Up M0C2");
                        }
                        else if(moveChoice.equals("M1C0") && !previousMove.equals("M1C0")) {
                            MS2 -= 1;
                            MS1 += 1;
                            System.out.println("Up M1C0");
                        }
                        else if(moveChoice.equals("M0C1") && !previousMove.equals("M0C1")) {
                            CS2 -= 1;
                            CS1 += 1;
                            System.out.println("Up M0C1");
                        }
                        else {
                            //No possible moves, restarts the program.
                            doesNotWork = true;
                        }
                        previousMove = moveChoice;
                    }
                }
                n++;
                numMS1.add(MS1);
                numCS1.add(CS1);
                numMS2.add(MS2);
                numCS2.add(CS2);
            }
            if (doesNotWork) {
                doesNotWork = false;
            }
            else {
                //prints out the moves.
                for(int i = 0; i < numMS1.size(); i++) {
                    System.out.println();
                    System.out.println("==================================================");
                    System.out.println("Turn No." + (i+1) + ":");
                    for(int j = 0; j < numMS1.get(i); j++) {
                        System.out.print("M ");
                    }
                    if(numMS1.get(i) > 0 && numCS1.get(i) > 0) {
                        System.out.print(": ");
                    }
                    for(int j = 0; j < numCS1.get(i); j++) {
                        System.out.print("C ");
                    }
                    System.out.println();
                    System.out.println("------------");
                    for(int j = 0; j < numMS2.get(i); j++) {
                        System.out.print("M ");
                    }
                    if(numMS2.get(i) > 0 && numCS2.get(i) > 0) {
                        System.out.print(": ");
                    }
                    for(int j = 0; j < numCS2.get(i); j++) {
                        System.out.print("C ");
                    }
                    System.out.println();
                    System.out.println("==================================================");
                }
                break;
            }
        }
    }
}
