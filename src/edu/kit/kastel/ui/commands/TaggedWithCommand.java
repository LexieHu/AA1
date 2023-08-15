package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.NoTaskFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to shows all tasks (including all direct and indirect subtasks)
 * that have been tagged with a specific tag.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TaggedWithCommand extends ProcrastinotCommand {

    private static final String COMMAND_NAME = "tagged-with";
    private static final int TAG_INDEX = 0;
    private static final int EXPECTED_ARGUMENTS_LENGTH = 1;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public TaggedWithCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (!args[TAG_INDEX].matches(TAG_REGEX)) {
            System.err.println(INVALID_TAG_ERROR);
            return;
        }
        
        String tag = args[TAG_INDEX];
        try {
            procrastinot.printTasksWithTag(tag);
        } catch (NoTaskFoundException e) {
            System.out.println(NO_OUTPUT);
        }
    }
}
