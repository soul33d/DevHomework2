package view;

import controller.EntityController;
import model.Project;
import org.jetbrains.annotations.NotNull;

public class ProjectView extends EntityView<Project> {

    public ProjectView(EntityController<Project> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
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
        return "project";
    }

    @NotNull
    @Override
    protected String pluralEntityName() {
        return "projects";
    }
}
