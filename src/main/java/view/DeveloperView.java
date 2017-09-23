package view;

import controller.EntityController;
import model.Developer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DeveloperView extends EntityView<Developer> {

    public DeveloperView(EntityController<Developer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
        this.controller = controller;
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }

    @NotNull
    @Override
    protected String singularEntityName() {
        return "developer";
    }

    @NotNull
    @Override
    protected String pluralEntityName() {
        return "developers";
    }
}
