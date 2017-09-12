package view;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class TerminalHelper implements Closeable {
    private Scanner scanner;

    public TerminalHelper(InputStream stream) {
        scanner = new Scanner(stream);
    }

    public int readIntFromInput(String msg) {
        if (msg.length() > 0) System.out.println(msg);
        return readIntFromInput();
    }

    public int readIntFromInput() {
        if (scanner.hasNextInt()) return scanner.nextInt();
        scanner.next();
        System.out.println("Incorrect input, please enter an integer.");
        return readIntFromInput();
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

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
