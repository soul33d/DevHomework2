package view;

import controller.EntityController;
import model.Skill;

public class SkillView extends EntityView<Skill> {

    public SkillView(MainView mainView, EntityController<Skill> controller, TerminalHelper terminalHelper) {
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
