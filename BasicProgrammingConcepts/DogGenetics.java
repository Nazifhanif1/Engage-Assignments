package demo;

import java.util.Random;
import java.util.Scanner;

public class DogGenetics {

    static private String name = "";

    public static void main(String[] args) {
        name = name();
        while (genrandom() == false) {
            System.out.println("calculating...");

        }
    }

    private static String name() {
        // TODO Auto-generated method stub
        String input = "";
        Scanner sc = new Scanner(System.in); // System.in is a standard input stream
        System.out.println("Enter your dog's name: ");
        input = sc.next();

        return input;
    }

    private static boolean genrandom() {
        boolean result = false;
        int[] nums = { 0, 0, 0, 0, 0 };
        int total = 0;
        for (int i = 0; i < 5; i++) {
            Random rand = new Random();
            nums[i] = rand.nextInt(100);
        }
        for (int k : nums) {
            total = total + k;

        }
        if (total == 100) {
            displayResults(nums);
            result = true;
        }
        return result;

    }

    private static void displayResults(int[] nums) {
        System.out.println(" ");
        System.out.println("Your dog " + name + " is: ");
        System.out.println(" ");
        for (int i = 0; i < nums.length; i++) {
            switch (i) {
                case 0:
                    System.out.println(nums[i] + "%" + " St. Bernard");
                    break;

                case 1:
                    System.out.println(nums[i] + "%" + " Chihuahua");
                    break;

                case 2:
                    System.out.println(nums[i] + "%" + " Dramatic RedNosed Asian Pug");
                    break;

                case 3:
                    System.out.println(nums[i] + "%" + " Common Cur");
                    break;

                case 4:
                    System.out.println(nums[i] + "%" + " King Doberman");
                    break;
            }

        }
    }
}
