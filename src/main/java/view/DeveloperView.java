package view;

import controller.EntityController;
import model.Company;
import model.Developer;
import model.Project;
import model.Skill;

import java.math.BigDecimal;

public class DeveloperView extends EntityView<Developer> {
    private CreateView createView;
    private UpdateView updateView;

    public DeveloperView(EntityController<Developer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "developer", "developers");
        this.controller = controller;
        createView = new CreateView(terminalHelper);
        updateView = new UpdateView(terminalHelper);
    }

    @Override
    protected void createEntity() {
        Developer developer = new Developer();
        developer.setFirstName(terminalHelper.readStringFromInput("Enter first name"));
        developer.setLastName(terminalHelper.readStringFromInput("Enter last name"));
        developer.setSalary(new BigDecimal(terminalHelper.readDoubleFromInput("Enter salary")));
        createView.setDeveloper(developer);
        createView.execute();
        controller.write(developer);
    }

    @Override
    protected void updateEntity() {
        updateEntity(updateView, updateView::setDeveloper);
    }

    private class CreateView extends View {
        protected static final int COMPLETE_KEY = 0;
        protected static final int ADD_SKILL_KEY = 1;
        protected static final int ADD_COMPANY_KEY = 2;
        protected static final int ADD_PROJECT_KEY = 3;

        protected Developer developer;

        public CreateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            System.out.printf("Press %d to complete\n", COMPLETE_KEY);
            System.out.printf("Press %d to add skills\n", ADD_SKILL_KEY);
            System.out.printf("Press %d to add company\n", ADD_COMPANY_KEY);
            System.out.printf("Press %d to add project\n", ADD_PROJECT_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_SKILL_KEY:
                    addSkills();
                    break;
                case ADD_COMPANY_KEY:
                    addCompanies();
                    break;
                case ADD_PROJECT_KEY:
                    addProjects();
                    break;
                case COMPLETE_KEY:
                    break;
                default:
                    System.out.printf("There is no action for %d", enteredAction);
                    selectMenuAction();
                    break;
            }
        }

        void addProjects() {
            developer.setProjects(readRelationalEntitiesFromInput(Project.class));
        }

        void addCompanies() {
            developer.setCompanies(readRelationalEntitiesFromInput(Company.class));
        }

        void addSkills() {
            developer.setSkills(readRelationalEntitiesFromInput(Skill.class));
        }

        public void setDeveloper(Developer developer) {
            this.developer = developer;
        }
    }

    private class UpdateView extends CreateView {

        public static final int CHANGE_FIRST_NAME_KEY = 4;
        public static final int CHANGE_LAST_NAME_KEY = 5;
        public static final int CHANGE_SALARY_KEY = 6;

        public UpdateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            super.printMenu();
            System.out.printf("Press %d to change first name", CHANGE_FIRST_NAME_KEY);
            System.out.printf("Press %d to change last name", CHANGE_LAST_NAME_KEY);
            System.out.printf("Press %d to change salary", CHANGE_SALARY_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_SKILL_KEY:
                    printAlertUpdateMessage("skills");
                    addSkills();
                    break;
                case ADD_COMPANY_KEY:
                    printAlertUpdateMessage("companies");
                    addCompanies();
                    break;
                case ADD_PROJECT_KEY:
                    printAlertUpdateMessage("projects");
                    break;
                case CHANGE_FIRST_NAME_KEY:
                    developer.setFirstName(terminalHelper.readStringFromInput("Enter new first name"));
                    break;
                case CHANGE_LAST_NAME_KEY:
                    developer.setLastName(terminalHelper.readStringFromInput("Enter new last name"));
                    break;
                case CHANGE_SALARY_KEY:
                    developer.setSalary(new BigDecimal(terminalHelper.readDoubleFromInput("Enter new salary")));
                    break;
                default:
                    System.out.printf("There is no action for %d", enteredAction);
                    selectMenuAction();
                    break;
            }
        }
    }
}
