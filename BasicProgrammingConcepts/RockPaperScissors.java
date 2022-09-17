package demo;

import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors {

    static private String initMsg = "Enter Number of rounds to play";
    static private String errorMsg = "Not a valid input. Program shutting down";
    static private String inputMsg = "Enter your choice from : Rock, Paper, Scissors";

    static private int player_score = 0;
    static private int cpu_score = 0;
    static private int draws = 0;

    public static void main(String[] args) {

        output(initMsg);
        String input = input();

        if (isInputValid(input)) {
            int rounds = Integer.parseInt(input);
            for (int i = 0; i < rounds; i++) {
                play();
            }
            findWinner();
        } else {

            output(errorMsg);
        }

    }

    private static void findWinner() {
        System.out.println(" ");
        System.out.println("YOU WON " + player_score + " times!!!");
        System.out.println(" ");
        System.out.println("THE CPU WON " + cpu_score + " times!!!");
        System.out.println(" ");
        System.out.println("YOU DREW " + draws + " times!!!");

        if (player_score > cpu_score) {
            System.out.println("Congratulations!! YOU WON!!");
        } else if (cpu_score > player_score) {
            System.out.println("Unlucky. The CPU won, better luck next time.");
        } else {
            System.out.println("THE GAME WAS A TIE!!");
        }
    }

    private static void play() {
        output(inputMsg);
        String in = input();
        Random rand = new Random();
        int cpu = rand.nextInt((3 - 1) + 1) + 1;
        System.out.println("CPU : " + cpu);

        if (isChoiceValid(in)) {
            if (in.equals("Rock") && cpu == 3) {
                System.out.println("YOU WIN!");
                player_score++;
            } else if (in.equals("Scissors") && cpu == 1) {
                System.out.println("YOU LOSE! CPU WINS!");
                cpu_score++;
            } else if (in.equals("Rock") && cpu == 2) {
                System.out.println("YOU LOSE! CPU WINS!");
                cpu_score++;
            } else if (in.equals("Paper") && cpu == 1) {
                System.out.println("YOU WIN!");
                player_score++;
            } else if (in.equals("Scissors") && cpu == 2) {
                System.out.println("YOU WIN!");
                player_score++;
            } else if (in.equals("Paper") && cpu == 3) {
                System.out.println("YOU LOSE! CPU WINS!");
                cpu_score++;
            } else {
                System.out.println("DRAW!!!");
                draws++;
            }
        }

        else {
            play();
        }
        // TODO Auto-generated method stub

    }

    private static boolean isChoiceValid(String input) {

        boolean result = false;
        if (input.equals("Rock") || input.equals("Paper") || input.equals("Scissors")) {
            result = true;
        }

        return result;
    }

    private static boolean isInputValid(String input) {

        boolean result = false;

        int tempInput;
        try {
            tempInput = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return result;
        }

        if ((1 <= tempInput) && (tempInput <= 10))
            result = true;

        return result;
        // TODO Auto-generated method stub

    }

    private static void output(String msg) {
        // TODO Auto-generated method stub
        System.out.println(msg);

    }

    private static String input() {
        // TODO Auto-generated method stub
        String input = "";
        Scanner sc = new Scanner(System.in); // System.in is a standard input stream

        input = sc.next();

        return input;
    }

}
