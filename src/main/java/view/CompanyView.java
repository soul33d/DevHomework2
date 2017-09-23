package view;

import controller.EntityController;
import model.Company;
import model.Developer;
import model.Project;

import java.util.ArrayList;
import java.util.List;

public class CompanyView extends EntityView<Company> {
    private static final int PRINT_ALL_KEY = 1;
    private static final int PRINT_COMPANY_KEY = 2;
    private static final int CREATE_KEY = 3;
    private static final int UPDATE_KEY = 4;
    private static final int DELETE_KEY = 5;
    private static final int DELETE_ALL_KEY = 6;
    private static final int BACK_TO_MAIN_MENU_KEY = 7;

    private boolean backToMainMenu = false;
    private CreateView createView;
    private UpdateView updateView;

    public CompanyView(EntityController<Company> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
        createView = new CreateView(terminalHelper);
        updateView = new UpdateView(terminalHelper);
    }

    @Override
    protected void printMenu() {
        System.out.printf("Press %d to print all companies\n", PRINT_ALL_KEY);
        System.out.printf("Press %d to print company by id\n", PRINT_COMPANY_KEY);
        System.out.printf("Press %d to create new company\n", CREATE_KEY);
        System.out.printf("Press %d to update company by id\n", UPDATE_KEY);
        System.out.printf("Press %d to delete company by id\n", DELETE_KEY);
        System.out.printf("Press %d to delete all companies\n", DELETE_ALL_KEY);
        System.out.printf("Press %d to back to main menu\n", BACK_TO_MAIN_MENU_KEY);
    }

    @Override
    protected void selectMenuAction() {
        int enteredInteger = terminalHelper.readIntFromInput();
        switch (enteredInteger) {
            case PRINT_ALL_KEY:
                printAll();
                break;
            case PRINT_COMPANY_KEY:
                printCompany();
                break;
            case CREATE_KEY:
                createCompany();
                break;
            case UPDATE_KEY:
                updateCompany();
                break;
            case DELETE_KEY:
                deleteCompany();
                break;
            case DELETE_ALL_KEY:
                deleteAllCompanies();
                break;
            case BACK_TO_MAIN_MENU_KEY:
                backToMainMenu = true;
                break;
        }
        if (!backToMainMenu) {
            printMenu();
            selectMenuAction();
        }
        backToMainMenu = false;
    }

    @Override
    protected void printAll() {
        List<Company> companies = controller.readAll();
        if (companies != null) {
            companies.forEach(System.out::println);
        }
    }

    private void printCompany() {
        int enteredInteger = terminalHelper.readIntFromInput("Enter id to print company details");
        Company company = controller.read(enteredInteger);
        System.out.println(company);
    }

    private void createCompany() {
        Company company = new Company();
        company.setName(terminalHelper.readStringFromInput("Enter company name"));
        createView.setCompany(company);
        createView.execute();
        controller.write(company);
    }

    private void updateCompany() {
        printAll();
        Company company;
        int enteredId = terminalHelper.readIntFromInput("Enter id of company to update or '0' to finish");
        while (enteredId != 0) {
            company = controller.read(enteredId);
            if (company != null) {
                updateView.setCompany(company);
                updateView.execute();
                controller.update(company);
            }
            enteredId = terminalHelper.readIntFromInput();
        }
    }

    private void deleteCompany() {
        int enteredInteger = terminalHelper.readIntFromInput("Enter id to delete company");
        controller.delete(enteredInteger);
    }

    private void deleteAllCompanies() {
        String answer = terminalHelper.readStringFromInput
                ("Are you sure you want to delete all companies? y/n");
        switch (answer) {
            case "y":
                if (controller.deleteAll()) {
                    System.out.println("All companies successfully deleted.");
                }
                break;
            case "n":
                break;
            default:
                deleteAllCompanies();
                break;
        }
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
            EntityController<Developer> developerController = controller.getEntityController(Developer.class);
            developerController.readAll().forEach(System.out::println);
            int enteredId = terminalHelper.readIntFromInput("Enter id to add developer or enter '0' to complete");
            List<Developer> developers = new ArrayList<>();
            while (enteredId != 0) {
                Developer developer = developerController.read(enteredId);
                if (developer != null) {
                    developers.add(developer);
                    System.out.println(developer + "\n Successfully added. Press '0' to complete.");
                } else System.out.printf("There is no developer with id %d\n", enteredId);
                enteredId = terminalHelper.readIntFromInput();
            }
            company.setDevelopers(developers);
        }

        void addProjects() {
            EntityController<Project> projectController = controller.getEntityController(Project.class);
            projectController.readAll().forEach(System.out::println);
            int enteredId = terminalHelper.readIntFromInput("Enter id to add project or enter '0' to complete");
            List<Project> projects = new ArrayList<>();
            while (enteredId != 0) {
                Project project = projectController.read(enteredId);
                if (project != null) {
                    projects.add(project);
                    System.out.println(project + "\n Successfully added. Press '0' to complete.");
                } else System.out.printf("There is no project with id %d\n", enteredId);
                enteredId = terminalHelper.readIntFromInput();
            }
            company.setProjects(projects);
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
                    System.out.println("After completing previous developers will be cleared!");
                    addDevelopers();
                    break;
                case ADD_PROJECTS_KEY:
                    System.out.println("After completing previous projects will be cleared!");
                    addProjects();
                    break;
                case COMPLETE_CREATION_KEY:
                    break;
                case CHANGE_NAME_KEY:
                    company.setName(terminalHelper.readStringFromInput("Enter new name"));
                default:
                    System.out.printf("There is no action for %d\n", enteredAction);
                    selectMenuAction();
                    break;
            }
        }
    }
}
