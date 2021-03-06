package view;

import controller.EntityController;
import model.Company;
import model.Customer;
import model.Developer;
import model.Project;

import java.math.BigDecimal;

public class ProjectView extends EntityView<Project> {
    private CreateView createView;
    private UpdateView updateView;

    public ProjectView(EntityController<Project> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "project", "projects");
        createView = new CreateView(terminalHelper);
        updateView = new UpdateView(terminalHelper);
    }

    @Override
    protected void createEntity() {
        Project project = new Project();
        project.setName(terminalHelper.readStringFromInput("Enter project name"));
        project.setCost(new BigDecimal(terminalHelper.readDoubleFromInput("Enter cost")));
        createView.setProject(project);
        createView.execute();
        controller.write(project);
    }

    @Override
    protected void updateEntity() {
        updateEntity(updateView, updateView::setProject);
    }

    private class CreateView extends View {
        static final int COMPLETE_KEY = 0;
        static final int ADD_COMPANIES_KEY = 1;
        static final int ADD_DEVELOPERS_KEY = 2;
        static final int ADD_CUSTOMERS_KEY = 3;

        protected Project project;

        private CreateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            System.out.printf("Press %d to complete\n", COMPLETE_KEY);
            System.out.printf("Press %d to add companies\n", ADD_COMPANIES_KEY);
            System.out.printf("Press %d to add developers\n", ADD_DEVELOPERS_KEY);
            System.out.printf("Press %d to add customers\n", ADD_CUSTOMERS_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_COMPANIES_KEY:
                    addCompanies();
                    break;
                case ADD_DEVELOPERS_KEY:
                    addDevelopers();
                    break;
                case ADD_CUSTOMERS_KEY:
                    addCustomers();
                    break;
                case COMPLETE_KEY:
                    return;
                default:
                    printNoActionKeyMessage(enteredAction);
                    selectMenuAction();
                    break;
            }
            printMenu();
            selectMenuAction();
        }

        void addCompanies() {
            project.setCompanies(readRelationalEntitiesFromInput(Company.class));
        }

        void addDevelopers() {
            project.setDevelopers(readRelationalEntitiesFromInput(Developer.class));
        }

        void addCustomers() {
            project.setCustomers(readRelationalEntitiesFromInput(Customer.class));
        }

        public void setProject(Project project) {
            this.project = project;
        }
    }

    private class UpdateView extends CreateView {
        private static final int CHANGE_NAME_KEY = 4;
        private static final int CHANGE_COST_KEY = 5;

        private UpdateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            super.printMenu();
            System.out.printf("Press %d to change name\n", CHANGE_NAME_KEY);
            System.out.printf("Press %d to change cost\n", CHANGE_COST_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_COMPANIES_KEY:
                    printAlertUpdateMessage("companies");
                    addCompanies();
                    break;
                case ADD_DEVELOPERS_KEY:
                    printAlertUpdateMessage("developers");
                    addDevelopers();
                    break;
                case ADD_CUSTOMERS_KEY:
                    printAlertUpdateMessage("customers");
                    addCustomers();
                    break;
                case CHANGE_NAME_KEY:
                    project.setName(terminalHelper.readStringFromInput("Enter new name"));
                    break;
                case CHANGE_COST_KEY:
                    project.setCost(new BigDecimal(terminalHelper.readDoubleFromInput("Enter new cost")));
                    break;
                case COMPLETE_KEY:
                    return;
                default:
                    printNoActionKeyMessage(enteredAction);
                    selectMenuAction();
                    break;
            }
            printMenu();
            selectMenuAction();
        }
    }
}
