package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.TaskNotFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.model.TaskList;
import edu.kit.kastel.ui.CommandHandler;
import edu.kit.kastel.ui.ProcrastinotCommand;

/**
 * Command to assign a task to a list or a task.
 *
 * @author uyzlh
 * @version 1.0
 */
public class AssignCommand extends ProcrastinotCommand {

    private static final int SUBTASK_INDEX = 0;
    private static final int PARENT_LIST_OR_TASK_INDEX = 1;
    private static final String COMMAND_NAME = "assign";
    private static final String TASK_SUCCESS_FORMAT = "assigned %s to %s%n";
    private static final int EXPECTED_ARGUMENTS_LENGTH = 2;

    /**
     * Instantiates a new assign command.
     *
     * @param commandHandler the command handler
     * @param procrastinot   the procrastinot platform
     */
    public AssignCommand(CommandHandler commandHandler, Procrastinot procrastinot) {
        super(COMMAND_NAME, commandHandler, procrastinot);
    }

    @Override
    protected void executeProcrastinotCommand(String[] args) {

        int argsLength = args.length;
        if (!compareArgsLength(argsLength, EXPECTED_ARGUMENTS_LENGTH)) {
            return;
        }
        //task
        if (args[SUBTASK_INDEX].matches(ID_REGEX)) {
            int subTaskId = Integer.parseInt(args[SUBTASK_INDEX]);
            Task subtask;
            String subTaskName;
            try {
                subtask = procrastinot.getTask(subTaskId);
                subTaskName = subtask.getName();
            } catch (TaskNotFoundException e) {
                System.err.println(createError(e.getMessage()));
                return;
            }

            if (args[PARENT_LIST_OR_TASK_INDEX].matches(ID_REGEX)) {
                int parentTaskId = Integer.parseInt(args[PARENT_LIST_OR_TASK_INDEX]);
                Task parentTask;
                String parentTaskName;

                try {
                    parentTask = procrastinot.getTask(parentTaskId);
                    procrastinot.assignTaskForTask(subTaskId, parentTaskId);
                    parentTaskName = parentTask.getName();
                } catch (TaskNotFoundException | IllegalArgumentException e) {
                    System.err.println(createError(e.getMessage()));
                    return;
                }

                System.out.printf(TASK_SUCCESS_FORMAT, subTaskName, parentTaskName);
                return;
            }
            //list
            if (args[PARENT_LIST_OR_TASK_INDEX].matches(LIST_NAME_REGEX)) {
                String listName = args[PARENT_LIST_OR_TASK_INDEX];
                TaskList parentList;
                try {
                    parentList = procrastinot.getTaskListByName(args[PARENT_LIST_OR_TASK_INDEX]);
                    parentList.assignTaskForList(subtask);
                } catch (IllegalArgumentException e) {
                    System.err.println(createError(e.getMessage()));
                    return;
                }

                System.out.printf(TASK_SUCCESS_FORMAT, subTaskName, listName);
                return;
            }

            System.out.println(INVALID_ARGUMENTS_ERROR);
            return;
        }

        System.out.println(INVALID_ARGUMENTS_ERROR);
    }
}
