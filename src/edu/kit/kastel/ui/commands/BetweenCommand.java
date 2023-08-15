package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.NoTaskFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Command to delete a task and all its direct and indirect subtasks in Procrastinot.
 *
 * @author uyzlh
 * @version 1.0
 */
public class BetweenCommand extends ProcrastinotCommand {

    private static final String COMMAND_NAME = "between";
    private static final int DATE01_INDEX = 0;
    private static final int DATE02_INDEX = 1;
    private static final int EXPECTED_ARGUMENTS_LENGTH = 2;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public BetweenCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (!args[DATE01_INDEX].matches(DATE_REGEX) || !args[DATE02_INDEX].matches(DATE_REGEX)) {
            System.err.println(INVALID_DATE_ERROR);
            return;
        }
        
        LocalDate localDate01;
        LocalDate localDate02;
        try {
            localDate01 = LocalDate.parse(args[DATE01_INDEX], java.time.format.DateTimeFormatter.ofPattern(DATE_PATTERN));
            localDate02 = LocalDate.parse(args[DATE02_INDEX], java.time.format.DateTimeFormatter.ofPattern(DATE_PATTERN));
        } catch (DateTimeParseException e) {
            System.err.println(INVALID_DATE_ERROR);
            return;
        }
        try {
            procrastinot.printTasksBetween(localDate01, localDate02);
        } catch (NoTaskFoundException e) {
            System.out.println(NO_OUTPUT);
        }
    }
}
