package edu.kit.kastel.ui;

import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.commands.AddCommand;
import edu.kit.kastel.ui.commands.AddListCommand;
import edu.kit.kastel.ui.commands.AssignCommand;
import edu.kit.kastel.ui.commands.BeforeCommand;
import edu.kit.kastel.ui.commands.BetweenCommand;
import edu.kit.kastel.ui.commands.ChangeDateCommand;
import edu.kit.kastel.ui.commands.ChangePriorityCommand;
import edu.kit.kastel.ui.commands.DeleteCommand;
import edu.kit.kastel.ui.commands.DuplicatesCommand;
import edu.kit.kastel.ui.commands.FindCommand;
import edu.kit.kastel.ui.commands.ListCommand;
import edu.kit.kastel.ui.commands.RestoreCommand;
import edu.kit.kastel.ui.commands.ShowCommand;
import edu.kit.kastel.ui.commands.TagCommand;
import edu.kit.kastel.ui.commands.TaggedWithCommand;
import edu.kit.kastel.ui.commands.TodoCommand;
import edu.kit.kastel.ui.commands.ToggleCommand;
import edu.kit.kastel.ui.commands.UpcomingCommand;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/*
    -- META DISCLAIMER --
    This version of the CommandHandler is a simplified version of the provided CommandHandler. As most patterns for
    commands use spaces exclusively as delimiter (ignoring spaces in names for simplification), it is sufficient to
    split the command string by spaces here and let the commands handle their arguments themselves.
 */

/**
 * The command handler responsible for maintaining all neccessary commands and user interaction.
 *
 * @author uyzlh
 * @version 1.0
 */
public final class CommandHandler {

    private static final String COMMAND_SEPARATOR_REGEX = "\\s+";
    private static final String COMMAND_NOT_FOUND = "ERROR: Command '%s' not found%n";
    private final Procrastinot procrastinot;
    private final Map<String, Command> commands;
    private boolean running = false;

    /**
     * Instantiates a new command handler.
     *
     * @param procrastinot the procrastinot platform
     */
    public CommandHandler(Procrastinot procrastinot) {
        this.procrastinot = Objects.requireNonNull(procrastinot);
        this.commands = new HashMap<>();
        this.initCommands();
    }

    /**
     * Handles the user input.
     */
    public void handleUserInput() {
        this.running = true;

        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                //
                executeCommand(scanner.nextLine());
            }
        }
    }

    /**
     * Quits the user input handling.
     */
    void quit() {
        this.running = false;
    }

    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);

        if (!commands.containsKey(commandName)) {
            System.err.printf(COMMAND_NOT_FOUND, commandName);
            return;
        }

        commands.get(commandName).execute(commandArguments);
    }

    private void initCommands() {
        this.addCommand(new QuitCommand(this));
        this.addCommand(new AddCommand(this, procrastinot));
        this.addCommand(new AddListCommand(this, procrastinot));
        this.addCommand(new TagCommand(this, procrastinot));
        this.addCommand(new AssignCommand(this, procrastinot));
        this.addCommand(new ChangeDateCommand(this, procrastinot));
        this.addCommand(new ChangePriorityCommand(this, procrastinot));
        this.addCommand(new DeleteCommand(this, procrastinot));
        this.addCommand(new ShowCommand(this, procrastinot));
        this.addCommand(new RestoreCommand(this, procrastinot));
        this.addCommand(new ToggleCommand(this, procrastinot));
        this.addCommand(new ListCommand(this, procrastinot));
        this.addCommand(new TodoCommand(this, procrastinot));
        this.addCommand(new FindCommand(this, procrastinot));
        this.addCommand(new UpcomingCommand(this, procrastinot));
        this.addCommand(new BetweenCommand(this, procrastinot));
        this.addCommand(new BeforeCommand(this, procrastinot));
        this.addCommand(new DuplicatesCommand(this, procrastinot));
        this.addCommand(new TaggedWithCommand(this, procrastinot));
    }

    private void addCommand(Command command) {
        this.commands.put(command.getCommandName(), command);
    }
}
