package view;

import controller.EntityController;
import model.Company;
import model.Developer;
import model.Project;

public class CompanyView extends EntityView<Company> {
    private CreateView createView;
    private UpdateView updateView;

    public CompanyView(EntityController<Company> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "company", "companies");
        createView = new CreateView(terminalHelper);
        updateView = new UpdateView(terminalHelper);
    }

    @Override
    protected void createEntity() {
        Company company = new Company();
        company.setName(terminalHelper.readStringFromInput("Enter company name"));
        createView.setCompany(company);
        createView.execute();
        controller.write(company);
    }

    @Override
    protected void updateEntity() {
        updateEntity(updateView, updateView::setCompany);
    }

    private class CreateView extends View {
        static final int ADD_DEVELOPERS_KEY = 1;
        static final int ADD_PROJECTS_KEY = 2;
        static final int COMPLETE_CREATION_KEY = 0;

        Company company;

        private CreateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            System.out.printf("Press %d to complete\n", COMPLETE_CREATION_KEY);
            System.out.printf("Press %d to add developers to company\n", ADD_DEVELOPERS_KEY);
            System.out.printf("Press %d to add projects to company,\n", ADD_PROJECTS_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_DEVELOPERS_KEY:
                    addDevelopers();
                    break;
                case ADD_PROJECTS_KEY:
                    addProjects();
                    break;
                case COMPLETE_CREATION_KEY:
                    break;
                default:
                    System.out.printf("There is no action for %d\n", enteredAction);
                    selectMenuAction();
                    break;
            }
        }

        void addDevelopers() {
            company.setDevelopers(readRelationalEntitiesFromInput(Developer.class));
        }

        void addProjects() {
            company.setProjects(readRelationalEntitiesFromInput(Project.class));
        }

        public void setCompany(Company company) {
            this.company = company;
        }
    }

    private class UpdateView extends CreateView {

        private static final int CHANGE_NAME_KEY = 3;

        private UpdateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            super.printMenu();
            System.out.printf("Press %d to change company name\n", CHANGE_NAME_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_DEVELOPERS_KEY:
                    printAlertUpdateMessage("developers");
                    addDevelopers();
                    break;
                case ADD_PROJECTS_KEY:
                    printAlertUpdateMessage("projects");
                    addProjects();
                    break;
                case COMPLETE_CREATION_KEY:
                    break;
                case CHANGE_NAME_KEY:
                    company.setName(terminalHelper.readStringFromInput("Enter new name"));
                    System.out.println("Successfully changed.");
                    break;
                default:
                    System.out.printf("There is no action for %d\n", enteredAction);
                    selectMenuAction();
                    break;
            }
        }
    }
}
