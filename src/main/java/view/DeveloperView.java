package view;

import controller.EntityController;
import model.Developer;

import java.util.List;

public class DeveloperView extends EntityView<Developer> {

    public DeveloperView(EntityController<Developer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
        this.controller = controller;
    }

    @Override
    protected void printMenu() {

    }

    @Override
    protected void selectMenuAction() {

    }

    @Override
    protected void printAll() {
        List<Developer> developers = controller.readAll();
        developers.forEach(System.out::println);
    }
}
