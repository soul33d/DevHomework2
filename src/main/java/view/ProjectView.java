package view;

import controller.EntityController;
import model.Project;

public class ProjectView extends EntityView<Project> {

    public ProjectView(MainView mainView, EntityController<Project> controller, TerminalHelper terminalHelper) {
        super(mainView, controller, terminalHelper);
    }

    @Override
    protected void printMenu() {

    }

    @Override
    protected void selectMenuAction() {

    }

    @Override
    protected void printAll() {

    }
}
