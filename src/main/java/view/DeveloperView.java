package view;

import controller.EntityController;
import model.Developer;

public class DeveloperView extends EntityView<Developer> {

    public DeveloperView(EntityController<Developer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "developer", "developers");
        this.controller = controller;
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }
}
