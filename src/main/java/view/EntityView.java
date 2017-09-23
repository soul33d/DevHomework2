package view;

import controller.EntityController;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class EntityView<T> extends View {
    protected static final int PRINT_ALL_KEY = 1;
    protected static final int PRINT_ENTITY_KEY = 2;
    protected static final int CREATE_KEY = 3;
    protected static final int UPDATE_KEY = 4;
    protected static final int DELETE_KEY = 5;
    protected static final int DELETE_ALL_KEY = 6;
    protected static final int BACK_TO_MAIN_MENU_KEY = 7;

    protected EntityController<T> controller;
    protected boolean backToMainMenu = false;

    public EntityView(EntityController<T> controller, TerminalHelper terminalHelper) {
        super(terminalHelper);
        this.controller = controller;
    }

    @Override
    protected void printMenu() {
        System.out.printf("Press %d to print all %s\n", PRINT_ALL_KEY, pluralEntityName());
        System.out.printf("Press %d to print %s by id\n", PRINT_ENTITY_KEY, singularEntityName());
        System.out.printf("Press %d to create new %s\n", CREATE_KEY, singularEntityName());
        System.out.printf("Press %d to update %s by id\n", UPDATE_KEY, singularEntityName());
        System.out.printf("Press %d to delete %s by id\n", DELETE_KEY, singularEntityName());
        System.out.printf("Press %d to delete all %s\n", DELETE_ALL_KEY, pluralEntityName());
        System.out.printf("Press %d to back to main menu\n", BACK_TO_MAIN_MENU_KEY);
    }

    @Override
    protected void selectMenuAction() {
        int enteredInteger = terminalHelper.readIntFromInput();
        switch (enteredInteger) {
            case PRINT_ALL_KEY:
                printAll();
                break;
            case PRINT_ENTITY_KEY:
                printEntity();
                break;
            case CREATE_KEY:
                createEntity();
                break;
            case UPDATE_KEY:
                updateEntity();
                break;
            case DELETE_KEY:
                deleteEntity();
                break;
            case DELETE_ALL_KEY:
                deleteAll();
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

    protected void printAll() {
        List<T> tList = controller.readAll();
        if (tList != null) {
            tList.forEach(System.out::println);
            if (tList.isEmpty()) {
                System.out.printf("There is no %s in your database.", pluralEntityName());
            }
        }
    }

    protected void printEntity() {
        System.out.printf("Enter id to print %s details\n", singularEntityName());
        int enteredId = terminalHelper.readIntFromInput();
        T t = controller.read(enteredId);
        if (t != null) {
            System.out.println(t);
        } else System.out.printf("There is no %s with id %d\n", singularEntityName(), enteredId);
    }

    protected abstract void createEntity();

    protected abstract void updateEntity();

    protected void deleteEntity() {
        printAll();
        System.out.printf("Enter id to delete %s or '0' to complete.", singularEntityName());
        int enteredInteger = terminalHelper.readIntFromInput();
        if (enteredInteger != 0) {
            controller.delete(enteredInteger);
            deleteEntity();
        }
    }

    protected void deleteAll() {
        String answer = terminalHelper.readStringFromInput
                ("Are you sure you want to delete all " + pluralEntityName() +"? y/n");
        switch (answer) {
            case "y":
                if (controller.deleteAll()) {
                    System.out.println("All " + pluralEntityName() +" successfully deleted.");
                }
                break;
            case "n":
                break;
            default:
                System.out.printf("There is no action for %s\n", answer);
                deleteAll();
                break;
        }
    }

    @NotNull
    protected abstract String singularEntityName();

    @NotNull
    protected abstract String pluralEntityName();
}
