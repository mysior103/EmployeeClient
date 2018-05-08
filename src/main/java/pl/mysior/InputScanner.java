package pl.mysior;

import java.util.InputMismatchException;
import java.util.Scanner;

public class InputScanner {
    static Scanner s = null;
    public static String userInput() {
        s = new Scanner(System.in);
        return s.nextLine();
    }

    public static int userInputInt() {
        try {
            s = new Scanner(System.in);
        } catch (InputMismatchException e) {
            System.out.println(e.toString());
        }
        return s.nextInt();
    }
}
