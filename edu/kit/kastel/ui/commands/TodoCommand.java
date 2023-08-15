package edu.kit.kastel.ui.commands;

import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

/**
 * Command to show all tasks that have not yet been marked as completed.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TodoCommand extends ProcrastinotCommand {

    private static final String COMMAND_NAME = "todo";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 0;

    /**
     * Instantiates a new delete task command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the car procrastinot platform
     */
    public TodoCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        
        procrastinot.printTodoTasks();
    }
}
