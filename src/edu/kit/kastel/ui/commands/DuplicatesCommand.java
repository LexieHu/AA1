package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;
import java.util.List;

/**
 * Exception thrown when a list with a given name is not found.
 *
 * @author uyzlh
 * @version 1.0
 */
public class DuplicatesCommand extends ProcrastinotCommand {

    private static final String COMMAND_NAME = "duplicates";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 0;
    private static final String TASK_SUCCESS_FORMAT = "Found %d duplicates: %s%n";

    /**
     * Instantiates a new list not found exception.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public DuplicatesCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }

        List<Integer> result = procrastinot.getDuplicates();
        int numberOfDuplicates = result.size();
        StringBuilder s = new StringBuilder();
        for (int value : result) {
            s.append(value);
            s.append(", ");
        }

        if (numberOfDuplicates > 0) {
            s.delete(s.length() - 2, s.length());
        }
        
        String id = s.toString();
        System.out.printf(TASK_SUCCESS_FORMAT, numberOfDuplicates, id);
    }
}
