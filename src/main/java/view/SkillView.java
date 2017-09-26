package view;

import controller.EntityController;
import model.Developer;
import model.Skill;

public class SkillView extends EntityView<Skill> {
    private CreateView createView;
    private UpdateView updateView;

    public SkillView(EntityController<Skill> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "skill", "skills");
        createView = new CreateView(terminalHelper);
        updateView = new UpdateView(terminalHelper);
    }

    @Override
    protected void createEntity() {
        Skill skill = new Skill();
        skill.setName(terminalHelper.readStringFromInput("Enter skill name"));
        createView.setSkill(skill);
        createView.execute();
        controller.write(skill);
    }

    @Override
    protected void updateEntity() {
        updateEntity(updateView, updateView::setSkill);
    }

    private class CreateView extends View {
        protected static final int COMPLETE_KEY = 0;
        protected static final int ADD_DEVELOPERS_KEY = 1;
        protected Skill skill;
        public CreateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            System.out.printf("Press %d to complete\n", COMPLETE_KEY);
            System.out.printf("Press %d to add developers\n", ADD_DEVELOPERS_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_DEVELOPERS_KEY:
                    addDevelopers();
                    break;
                case COMPLETE_KEY:
                    break;
                default:
                    printNoActionKeyMessage(enteredAction);
                    selectMenuAction();
                    break;
            }
        }

        void addDevelopers() {
            skill.setDevelopers(readRelationalEntitiesFromInput(Developer.class));
        }

        public void setSkill(Skill skill) {
            this.skill = skill;
        }
    }

    private class UpdateView extends CreateView {

        protected static final int CHANGE_NAME_KEY = 2;

        public UpdateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            super.printMenu();
            System.out.printf("Press %d to change name\n", CHANGE_NAME_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_DEVELOPERS_KEY:
                    printAlertUpdateMessage("developers");
                    addDevelopers();
                    break;
                case CHANGE_NAME_KEY:
                    skill.setName(terminalHelper.readStringFromInput("Enter new name"));
                    break;
                case COMPLETE_KEY:
                    break;
                default:
                    printNoActionKeyMessage(enteredAction);
                    selectMenuAction();
                    break;
            }
        }
    }
}
