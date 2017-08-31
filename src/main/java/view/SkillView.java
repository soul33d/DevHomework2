package view;

import controller.EntityController;
import model.Skill;

public class SkillView extends View {

    private EntityController<Skill> controller;

    public SkillView(TerminalHelper terminalHelper) {
        super(terminalHelper);
    }

    public SkillView(TerminalHelper terminalHelper, EntityController<Skill> controller) {
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
