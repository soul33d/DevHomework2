package view;

import controller.EntityController;
import model.Skill;

public class SkillView extends EntityView<Skill> {

    public SkillView(EntityController<Skill> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "skill", "skills");
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }
}
