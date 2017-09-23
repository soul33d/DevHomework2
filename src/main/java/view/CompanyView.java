package view;

import controller.EntityController;
import model.Company;
import model.Developer;
import model.Project;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CompanyView extends EntityView<Company> {
    private CreateView createView;
    private UpdateView updateView;

    public CompanyView(EntityController<Company> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
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
        printAll();
        Company company;
        int enteredId = terminalHelper.readIntFromInput("Enter id of company to update or '0' to complete");
        if (enteredId != 0) {
            company = controller.read(enteredId);
            if (company != null) {
                updateView.setCompany(company);
                updateView.execute();
                controller.update(company);
            } else {
                System.out.printf("There is no company with id %d\n", enteredId);
            }
            updateEntity();
        }
    }

    @NotNull
    @Override
    protected String singularEntityName() {
        return "company";
    }

    @NotNull
    @Override
    protected String pluralEntityName() {
        return "companies";
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
