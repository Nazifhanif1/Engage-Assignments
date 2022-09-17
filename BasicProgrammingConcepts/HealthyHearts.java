package demo;

import java.util.Scanner;

public class HealthyHearts {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your age: ");
        int age = Integer.parseInt(sc.nextLine());

        int max_hr = 220 - age;
        System.out.println("Your maximum heart rate is: " + max_hr);
        System.out.println("Your target HR Zone is: " + (0.5 * max_hr) + " - " + (0.85 * max_hr));
    }
}
