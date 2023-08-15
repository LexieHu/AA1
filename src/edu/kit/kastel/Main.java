package edu.kit.kastel;

import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.CommandHandler;


/**
 * Main class of the program.
 *
 * @author uyzlh
 * @version 1.0
 */
public final class Main {
    
    private Main() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    /**
     * Main entry point of the program.
     *
     * @param args ignored
     */
    public static void main(String[] args) {
        Procrastinot procrastinot = new Procrastinot();
        CommandHandler commandHandler = new CommandHandler(procrastinot);
        commandHandler.handleUserInput();
    }
}
