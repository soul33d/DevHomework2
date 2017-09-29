package view;

import controller.EntityController;
import model.Customer;
import model.Project;

public class CustomerView extends EntityView<Customer> {
    private CreateView createView;
    private UpdateView updateView;

    public CustomerView(EntityController<Customer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "customer", "customers");
        createView = new CreateView(terminalHelper);
        updateView = new UpdateView(terminalHelper);
    }

    @Override
    protected void createEntity() {
        Customer customer = new Customer();
        customer.setFirstName(terminalHelper.readStringFromInput("Enter first name"));
        customer.setLastName(terminalHelper.readStringFromInput("Enter last name"));
        createView.setCustomer(customer);
        createView.execute();
        controller.write(customer);
    }

    @Override
    protected void updateEntity() {
        updateEntity(updateView, updateView::setCustomer);
    }

    private class CreateView extends View {
        static final int ADD_PROJECTS_KEY = 1;
        static final int COMPLETE_KEY = 0;

        Customer customer;

        CreateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            System.out.printf("Press %d to complete\n", COMPLETE_KEY);
            System.out.printf("Press %d to add projects for customer\n", ADD_PROJECTS_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_PROJECTS_KEY:
                    addProjects();
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

        void addProjects() {
            customer.setProjects(readRelationalEntitiesFromInput(Project.class));
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    }

    private class UpdateView extends CreateView {
        private static final int CHANGE_FIRST_NAME_KEY = 2;
        private static final int CHANGE_LAST_NAME_KEY = 3;

        private UpdateView(TerminalHelper terminalHelper) {
            super(terminalHelper);
        }

        @Override
        protected void printMenu() {
            super.printMenu();
            System.out.printf("Press %d to change first name\n", CHANGE_FIRST_NAME_KEY);
            System.out.printf("Press %d to change last name\n", CHANGE_LAST_NAME_KEY);
        }

        @Override
        protected void selectMenuAction() {
            int enteredAction = terminalHelper.readIntFromInput();
            switch (enteredAction) {
                case ADD_PROJECTS_KEY:
                    printAlertUpdateMessage("projects");
                    addProjects();
                    break;
                case CHANGE_FIRST_NAME_KEY:
                    customer.setFirstName(terminalHelper.readStringFromInput("Enter new first name"));
                    break;
                case CHANGE_LAST_NAME_KEY:
                    customer.setLastName(terminalHelper.readStringFromInput("Enter new last name"));
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
