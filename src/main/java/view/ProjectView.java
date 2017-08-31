package view;

import controller.EntityController;
import model.Project;

public class ProjectView extends View {

    private EntityController<Project> controller;

    public ProjectView(TerminalHelper terminalHelper) {
        super(terminalHelper);
    }

    public ProjectView(TerminalHelper terminalHelper, EntityController<Project> controller) {
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
