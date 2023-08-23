package edu.kit.kastel.ui.commands;

import edu.kit.kastel.exception.NoTaskFoundException;
import edu.kit.kastel.model.Procrastinot;
import edu.kit.kastel.model.Task;
import edu.kit.kastel.ui.ProcrastinotCommand;
import edu.kit.kastel.ui.CommandHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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
        List<Task> list = procrastinot.getDefaultTasks().stream().filter((task) -> task.getParentTask() == null).toList();
        List<Task> result = new ArrayList<>();
        try {
            result = procrastinot.getTasksWithTag(tag, list, true);
        } catch (NoTaskFoundException e) {
            System.out.println(NO_OUTPUT);
        }

        Comparator<Task> taskComparator = new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                int priorityComparison = task1.getPriority().compareTo(task2.getPriority());
                if (priorityComparison != 0) {
                    return priorityComparison;
                }
                if (task1.getParentTask() != null && task2.getParentTask() != null) {
                    int parentComparison = Integer.compare(task1.getParentTask().getId(), task2.getParentTask().getId());
                    if (parentComparison != 0) {
                        return parentComparison;
                    }
                }
                return 0;
            }
        };
        result.sort(taskComparator);
        for (Task t : result) {
            procrastinot.printTask(t, 0);
        }
    }
}
