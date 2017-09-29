package view;

import controller.EntityController;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class EntityView<T> extends View {
    protected static final int PRINT_ALL_KEY = 1;
    protected static final int PRINT_ENTITY_KEY = 2;
    protected static final int CREATE_KEY = 3;
    protected static final int UPDATE_KEY = 4;
    protected static final int DELETE_KEY = 5;
    protected static final int DELETE_ALL_KEY = 6;
    protected static final int BACK_TO_MAIN_MENU_KEY = 7;

    protected EntityController<T> controller;
    protected String singularEntityName;
    protected String pluralEntityName;

    public EntityView(EntityController<T> controller, TerminalHelper terminalHelper,
                      String singularEntityName, String pluralEntityName) {
        super(terminalHelper);
        this.controller = controller;
        this.singularEntityName = singularEntityName;
        this.pluralEntityName = pluralEntityName;
    }

    @Override
    protected void printMenu() {
        System.out.printf("Press %d to print all %s\n", PRINT_ALL_KEY, pluralEntityName);
        System.out.printf("Press %d to print %s by id\n", PRINT_ENTITY_KEY, singularEntityName);
        System.out.printf("Press %d to create new %s\n", CREATE_KEY, singularEntityName);
        System.out.printf("Press %d to update %s by id\n", UPDATE_KEY, singularEntityName);
        System.out.printf("Press %d to delete %s by id\n", DELETE_KEY, singularEntityName);
        System.out.printf("Press %d to delete all %s\n", DELETE_ALL_KEY, pluralEntityName);
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
                return;
            default:
                printNoActionKeyMessage(enteredInteger);
                break;
        }
        printMenu();
        selectMenuAction();
    }

    protected void printAll() {
        List<T> tList = controller.readAll();
        if (tList != null) {
            tList.forEach(System.out::println);
            if (tList.isEmpty()) {
                System.out.printf("There is no %s in your database.", pluralEntityName);
            }
        }
    }

    protected void printEntity() {
        System.out.printf("Enter id to print %s details\n", singularEntityName);
        int enteredId = terminalHelper.readIntFromInput();
        T t = controller.read(enteredId);
        if (t != null) {
            System.out.println(t);
        } else System.out.printf("There is no %s with id %d\n", singularEntityName, enteredId);
    }

    protected abstract void createEntity();

    protected abstract void updateEntity();

    protected void deleteEntity() {
        printAll();
        System.out.printf("Enter id to delete %s or '0' to complete.\n", singularEntityName);
        int enteredInteger = terminalHelper.readIntFromInput();
        if (enteredInteger != 0) {
            controller.delete(enteredInteger);
            deleteEntity();
        }
    }

    protected void deleteAll() {
        String answer = terminalHelper.readStringFromInput
                ("Are you sure you want to delete all " + pluralEntityName + "? y/n");
        switch (answer) {
            case "y":
                if (controller.deleteAll()) {
                    System.out.println("All " + pluralEntityName + " successfully deleted.");
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

    protected <E> List<E> readRelationalEntitiesFromInput(Class<E> clazz) {
        EntityController<E> entityController = controller.getEntityController(clazz);
        entityController.readAll().forEach(System.out::println);
        String singularName = clazz.getSimpleName().toLowerCase();
        int enteredId = terminalHelper.readIntFromInput("Enter id to add " + singularName + " or enter '0' to complete");
        List<E> entities = new ArrayList<>();
        while (enteredId != 0) {
            E e = entityController.read(enteredId);
            if (e != null) {
                entities.add(e);
                System.out.println(e + "\n Successfully added. Press '0' to complete.");
            } else System.out.printf("There is no " + singularName + " with id %d\n", enteredId);
            enteredId = terminalHelper.readIntFromInput();
        }
        return entities;
    }

    protected void updateEntity(View updateView, Consumer<T> consumer) {
        printAll();
        T t;
        int enteredId = terminalHelper.readIntFromInput("Enter id of " + singularEntityName + " to update or '0' to complete");
        if (enteredId != 0) {
            t = controller.read(enteredId);
            if (t != null) {
                consumer.accept(t);
                updateView.execute();
                controller.update(t);
            } else {
                System.out.printf("There is no " + singularEntityName + " with id %d\n", enteredId);
            }
            updateEntity();
        }
    }


    protected void printAlertUpdateMessage(String pluralName) {
        System.out.println("After completing previous " + pluralName + " will be cleared!");
    }

    protected void printNoActionKeyMessage(int action) {
        System.out.printf("There is no action for %d\n", action);
    }
}
