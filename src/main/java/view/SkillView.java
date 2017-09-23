package view;

import controller.EntityController;
import model.Skill;
import org.jetbrains.annotations.NotNull;

public class SkillView extends EntityView<Skill> {

    public SkillView(EntityController<Skill> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }

    @Override
    protected void deleteEntity() {

    }

    @Override
    protected void deleteAll() {

    }

    @NotNull
    @Override
    protected String singularEntityName() {
        return "skill";
    }

    @NotNull
    @Override
    protected String pluralEntityName() {
        return "skills";
    }
}
