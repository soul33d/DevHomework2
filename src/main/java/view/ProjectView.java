package view;

import controller.EntityController;
import model.Project;

public class ProjectView extends EntityView<Project> {

    public ProjectView(EntityController<Project> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "project", "projects");
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }
}
