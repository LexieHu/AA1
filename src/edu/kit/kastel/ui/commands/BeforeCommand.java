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
public class BeforeCommand extends ProcrastinotCommand {

    private static final String COMMAND_NAME = "before";
    private static final int DATE_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public BeforeCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (!args[DATE_INDEX].matches(DATE_REGEX)) {
            System.err.println(INVALID_DATE_ERROR);
            return;
        }
        
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(args[DATE_INDEX]);
        } catch (DateTimeParseException e) {
            System.err.println(INVALID_DATE_ERROR);
            return;
        }
        try {
            procrastinot.printTasksBefore(localDate);
        } catch (NoTaskFoundException e) {
            System.out.println(NO_OUTPUT);
        }
    }
}
