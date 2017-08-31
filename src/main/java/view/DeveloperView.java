package view;

import controller.EntityController;
import model.Developer;

public class DeveloperView extends View {

    private EntityController<Developer> controller;

    public DeveloperView(TerminalHelper terminalHelper) {
        super(terminalHelper);
    }

    public DeveloperView(TerminalHelper terminalHelper, EntityController<Developer> controller) {
        super(terminalHelper);
        this.controller = controller;
    }

    @Override
    protected void printMenu() {

    }

    @Override
    protected void selectMenuAction() {

    }
}
