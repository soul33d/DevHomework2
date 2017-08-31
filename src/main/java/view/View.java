package view;

public abstract class View {
    protected TerminalHelper terminalHelper;

    public View(TerminalHelper  terminalHelper) {
        this.terminalHelper = terminalHelper;
    }

    public void execute() {
        printMenu();
        selectMenuAction();
    }

    protected abstract void printMenu();

    protected abstract void selectMenuAction();
}
