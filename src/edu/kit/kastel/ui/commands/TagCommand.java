package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.ListNotFoundException;
import edu.kit.kastel.exception.TagAlreadyUsedException;
import edu.kit.kastel.exception.TaskNotFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcrastinotCommand;

/**
 * Command to tag a task or list with keywords.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TagCommand extends ProcrastinotCommand {

    private static final int ID_OR_LIST_INDEX = 0;
    private static final int TAG_INDEX = 1;
    private static final String COMMAND_NAME = "tag";
    private static final String TAG_SUCCESS_FORMAT = "tagged %s with %s%n";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 2;

    /**
     * Instantiates a new tag command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the procrastinot platform
     */
    public TagCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {
        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        if (args[TAG_INDEX].matches(TAG_REGEX)) {
            String tag = args[TAG_INDEX];
            if (!(args[ID_OR_LIST_INDEX].matches(ID_REGEX) || args[ID_OR_LIST_INDEX].matches(LIST_NAME_REGEX))) {
                System.err.println(INVALID_ARGUMENTS_ERROR);
                return;
            }
            if (args[ID_OR_LIST_INDEX].matches(ID_REGEX)) {
                int taskId = Integer.parseInt(args[ID_OR_LIST_INDEX]);
                String taskName;
                try {
                    taskName = procrastinot.getTask(taskId).getName();
                    procrastinot.addTag(taskId, tag);
                } catch (TaskNotFoundException | TagAlreadyUsedException e) {
                    System.err.println(createError(e.getMessage()));
                    return;
                }
                System.out.printf(TAG_SUCCESS_FORMAT, taskName, tag);
                return;
            }

            String list = args[ID_OR_LIST_INDEX];
            try {
                procrastinot.addListTag(list, tag);
            } catch (ListNotFoundException | TagAlreadyUsedException e) {
                System.err.println(createError(e.getMessage()));
                return;
            }

            System.out.printf(TAG_SUCCESS_FORMAT, list, tag);
            return;
        }
        System.err.println(INVALID_ARGUMENTS_ERROR);
    }
}
