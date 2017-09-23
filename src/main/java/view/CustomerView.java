package view;

import controller.EntityController;
import model.Customer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomerView extends EntityView<Customer> {

    public CustomerView(EntityController<Customer> controller, TerminalHelper terminalHelper) {
        super(controller, terminalHelper);
    }

    @Override
    protected void createEntity() {

    }

    @Override
    protected void updateEntity() {

    }

    @Override
    protected void deleteEntity() {

    }

    @Override
    protected void deleteAll() {

    }

    @NotNull
    @Override
    protected String singularEntityName() {
        return "customer";
    }

    @NotNull
    @Override
    protected String pluralEntityName() {
        return "customers";
    }
}
