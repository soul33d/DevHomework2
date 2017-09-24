package view;

import controller.EntityController;
import model.Customer;

public class CustomerView extends EntityView<Customer> {

    public CustomerView(EntityController<Customer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper, "customer", "customers");
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }
}
