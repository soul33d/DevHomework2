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
        printAll();
        Customer customer;
        int enteredId = terminalHelper.readIntFromInput("Enter id of customer to update or '0' to complete");
        if (enteredId != 0) {
            customer = controller.read(enteredId);
            if (customer != null) {
                updateView.setCustomer(customer);
                updateView.execute();
                controller.update(customer);
            } else {
                System.out.printf("There is no customer with id %d\n", enteredId);
            }
            updateEntity();
        }
    }

    private class CreateView extends View {
        static final int ADD_PROJECTS_KEY = 1;
        static final int COMPLETE_KEY = 0;

        Customer customer;

        public CreateView(TerminalHelper terminalHelper) {
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
                    break;
                default:
                    System.out.printf("There is no action for %d\n", enteredAction);
                    selectMenuAction();
                    break;
            }
        }

        void addProjects() {
            customer.setProjects(readRelationalEntitiesFromInput(Project.class));
        }

        public void setCustomer(Customer customer) {
            this.customer = customer;
        }
    }

    private class UpdateView extends CreateView {
        public static final int CHANGE_FIRST_NAME_KEY = 2;
        public static final int CHANGE_LAST_NAME_KEY = 3;

        public UpdateView(TerminalHelper terminalHelper) {
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
                    System.out.println("After completing previous projects will be cleared!");
                    addProjects();
                    break;
                case CHANGE_FIRST_NAME_KEY:
                    customer.setFirstName(terminalHelper.readStringFromInput("Enter new first name"));
                    break;
                case CHANGE_LAST_NAME_KEY:
                    customer.setLastName("Enter new last name");
                    break;
                case COMPLETE_KEY:
                    break;
                default:
                    System.out.printf("There is no action for %d\n", enteredAction);
                    selectMenuAction();
                    break;
            }
        }
    }
}
