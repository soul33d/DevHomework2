package view;

import java.util.Scanner;

public class TerminalHelper {
    private Scanner scanner;

    public TerminalHelper() {
        scanner = new Scanner(System.in);
    }

    public int readIntFromInput(String msg) {
        if (msg.length() > 0) System.out.println(msg);
        if (scanner.hasNextInt()) return scanner.nextInt();
        scanner.next();
        System.out.println("Incorrect input, please enter an integer.");
        return readIntFromInput(msg);
    }

    public String readStringFromInput(String msg) {
        System.out.println(msg);
        if (scanner.hasNext()) return scanner.next() + scanner.nextLine();
        return readStringFromInput(msg);
    }

    public double readDoubleFromInput(String msg) {
        System.out.println(msg);
        if (scanner.hasNextDouble()) return scanner.nextDouble();
        scanner.next();
        System.out.println("Incorrect input. Please enter double.");
        return readDoubleFromInput(msg);
    }
}
