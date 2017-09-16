package view;

import controller.EntityController;
import model.Customer;

public class CustomerView extends EntityView<Customer> {

    public CustomerView(MainView mainView, EntityController<Customer> controller, TerminalHelper terminalHelper) {
        super(mainView, controller, terminalHelper);
    }

    @Override
    protected void printMenu() {

    }

    @Override
    protected void selectMenuAction() {

    }

    @Override
    protected void printAll() {

    }
}
