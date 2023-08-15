package edu.kit.kastel.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * The procrastinot platform.
 *
 * @author uyzlh
 * @version 1.0
 */
public final class Procrastinot {
    /**
     * Error message if the ID does not correspond to a task
     */
    protected static final String ID_NOT_FOUND_ERROR = "Cannot find task with given task ID.";
    /**
     * Error message if the list name does not exist
     */
    protected static final String LIST_NOT_FOUND_ERROR = "Cannot find list with given list name.";
    /**
     * Error message if the operation is illegal, for example: sign a parent task to its subtask
     */
    protected static final String ILLEGAL_ASSIGN_OPERATION_ERROR = "Cannot assign given parent task to a subtask.";
    /**
     * Error message if the given task is already deleted
     */
    protected static final String TASK_DELETED_ERROR = "Given task is deleted.";
    private final List<Task> defaultTasks = new ArrayList<>();
    private final List<TaskList> lists = new ArrayList<>();

    /**
     * Returns a list of the default tasks for the procrastinot platform.
     *
     * @return a list of the default tasks for the procrastinot platform
     */
    public List<Task> getDefaultTasks() {
        return defaultTasks;
    }

    /**
     * Adds the given TaskList object to the list of task lists.
     *
     * @param list the TaskList object to add
     */
    public void addList(TaskList list) {
        lists.add(list);
    }

    /**
     * Retrieves the task with the given ID from the default tasks list.
     *
     * @param id the unique ID of the task to retrieve
     * @return the Task object with the given ID
     * @throws IllegalArgumentException if the ID is not found in the default tasks list
     */
    public Task getTask(int id) {
        if (id <= defaultTasks.size()) {
            return defaultTasks.get(id - 1);
        }
        throw new IllegalArgumentException(ID_NOT_FOUND_ERROR);
    }

    /**
     * Retrieves the task list with the given name from the list of task lists.
     *
     * @param name the name of the task list to retrieve
     * @return the TaskList object with the given name
     * @throws IllegalArgumentException if the name is not found in the list of task lists
     */
    public TaskList getTaskListByName(String name) {
        for (TaskList list : lists) {
            if (list.getListName().equals(name)) {
                return list;
            }
        }
        throw new IllegalArgumentException(LIST_NOT_FOUND_ERROR);
    }

    /**
     * Adds the given tag to the task with the given ID in the default tasks list.
     *
     * @param id the id of the task to add the tag to
     * @param tag the tag to add to the task
     * @throws IllegalArgumentException if the ID is not found in the default tasks list
     */
    public void addTag(int id, String tag) {
        Task task = getTask(id);
        if (task != null) {
            task.addTag(tag);
        } else {
            throw new IllegalArgumentException(ID_NOT_FOUND_ERROR);
        }
    }

    /**
     * Adds the given tag to the task list with the given name in the list of task lists.
     *
     * @param listName the name of the task list to add the tag to
     * @param tag the tag to add to the task list
     * @throws IllegalArgumentException if the name is not found in the list of task lists
     */
    public void addListTag(String listName, String tag) {
        TaskList list = getTaskListByName(listName);
        if (list != null) {
            list.add(tag);
        } else {
            throw new IllegalArgumentException(LIST_NOT_FOUND_ERROR);
        }
    }

    /**
     * Deletes the task with the given ID from the default tasks list.
     *
     * @param id the id of the task to delete
     * @return the Task object that was deleted
     */
    public Task deleteTask(int id) {
        Task task = getTask(id);
        task.delete();
        return task;
    }

    /**
     * Restores the task with the given ID from the default tasks list by setting its status to "visible".
     *
     * @param id the id of the task to restore
     * @return the Task object that was restored
     */
    public Task restoreTask(int id) {
        Task task = getTask(id);
        task.restore();
        return task;
    }

    /**
     * toggle the task with the given ID from the default tasks list.
     *
     * @param id the ID of the task to toggle 
     * @return the Task object that was toggled
     */
    public Task toggleTask(int id) {
        Task task = getTask(id);
        task.toggle(!task.isCompleted());
        return task;
    }

    /**
     * Assigns the task with the given subtask ID as a subtask of the task with the given parent task ID.
     *
     * @param subtaskId the ID of the subtask to assign
     * @param parentTaskId the ID of the parent task to assign the subtask to
     * @throws IllegalArgumentException if either the subtask or parent task is deleted 
     *                                  or if the subtask already contains the parent task
     */
    public void assignTaskForTask(int subtaskId, int parentTaskId) {

        Task subTask = getTask(subtaskId);
        Task parentTask = getTask(parentTaskId);

        if (!(subTask.isVisible()) || !(parentTask.isVisible())) {
            throw new IllegalArgumentException(TASK_DELETED_ERROR);
        }
        if (subTask.contains(parentTask)) {
            throw new IllegalArgumentException(ILLEGAL_ASSIGN_OPERATION_ERROR);
        }
        if (subTask.getParentTask() != null) {
            subTask.getParentTask().removeSubTask(subTask);
        }
        parentTask.addSubTask(subTask);
        subTask.setParentTask(parentTask);
    }

    /**
     * Prints the given task and its subtasks (if any) to the console with the specified indentation.
     *
     * @param task the task to print
     * @param indentation the number of spaces to indent the task and its subtasks
     * @throws IllegalArgumentException if the task is not visible
     */
    public void printTask(Task task, int indentation) {
        if (!task.isVisible()) {
            throw new IllegalArgumentException();
        }
        String s = buildString(" ", indentation);
        List<Task> subTasksCopy = new ArrayList<>(task.getSubTasks());
        Collections.sort(subTasksCopy);
        System.out.println(s + task.print());
        if (!subTasksCopy.isEmpty()) {
            for (Task subTask : subTasksCopy) {
                if (subTask.isVisible()) {
                    printTask(subTask, indentation + 2);
                }
            }
        }
    }

    /**
     * Prints the given task and its subtasks (if any) with indentation, if predicate true.
     *
     * @param predicate the predicate to test the task against
     * @param task the task to print
     * @param indentation the number of spaces to indent the task and its subtasks
     * @throws IllegalArgumentException if the task is not visible
     */
    public void printTaskConditional(Predicate<Task> predicate, Task task, int indentation) {
        String s = buildString(" ", indentation);
        List<Task> subTasksCopy = new ArrayList<>(task.getSubTasks());
        Collections.sort(subTasksCopy);

        System.out.println(s + task.print());
        if (!subTasksCopy.isEmpty()) {
            for (Task subTask : subTasksCopy) {
                if (subTask.isVisible() && predicate.test(subTask)) {
                    printTaskConditional(predicate, subTask, indentation + 2);
                }
            }
        }
    }

    /**
     * Builds a string consisting of the given substring repeated a specified number of times.
     *
     * @param substring the substring to repeat
     * @param indentation the number of times to repeat the substring
     * @return a string consisting of the given substring repeated a specified number of times
     */
    public String buildString(String substring, int indentation) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentation; i++) {
            sb.append(substring);
        }
        return sb.toString();
    }

    /**
     * Prints all visible tasks in the task list with the given name to the console with the specified indentation.
     *
     * @param name the name of the task list to print
     * @throws IllegalArgumentException if the task list with the given name does not exist
     */
    public void printList(String name) {
        TaskList list = getTaskListByName(name);
        for (Task task : list.getListCopy()) {
            if (task.isVisible()) {
                printTask(task,  0);
            }
        }
    }

    /**
    * Prints all visible todo tasks in the default tasks list to the console with the specified indentation.
    */
    public void printTodoTasks() {
        for (Task task : defaultTasks) {
            if (task.isVisible() && !task.hasParent() && (task.hasUndoneChild() || !task.isCompleted())) {
                printTaskConditional(((subTask) -> (subTask.hasUndoneChild() || !subTask.isCompleted())), task, 0);
            }
        }
    }

   /**
     * Prints all visible tasks in the default tasks list that have the given tag to the console with the specified indentation.
     *
     * @param tag the tag to filter tasks by
     * @throws IllegalArgumentException if the tag is null or empty
     */
    public void printTasksWithTag(String tag) {
        printFilteredTasks((task) -> task.hasTag(tag), this.defaultTasks);
    }

    /**
     * Prints all visible tasks in the default tasks list that contain the given name to the console with the specified indentation.
     *
     * @param name the name to filter tasks by
     * @throws IllegalArgumentException if the name is null or empty
     */
    public void findTasksWithName(String name)  {
        printFilteredTasks((task) -> task.getName().contains(name), defaultTasks);
    }

    /**
     * Prints all visible tasks that are due within the next seven days with the specified indentation.
     *
     * @param date the date to filter tasks by
     * @throws IllegalArgumentException if the date is null or in the past
     */
    public void upcomingDue(LocalDate date) {
        printFilteredTasks((task) -> {
            LocalDate dueDate = task.getDate();
            if (dueDate == null) {
                return false;
            }
            return !dueDate.isBefore(date) && !dueDate.isAfter(date.plusDays(6));
        }, this.defaultTasks);
    }

    /**
     * Prints all visible tasks in the default tasks list that are due before the given date to the console with the specified indentation.
     *
     * @param date the date to filter tasks by
     * @throws IllegalArgumentException if the date is null or in the past
     */
    public void printTasksBefore(LocalDate date) {
        printFilteredTasks((task) -> {
            LocalDate dueDate = task.getDate();
            if (dueDate == null) {
                return false;
            }
            return !dueDate.isAfter(date);
        }, this.defaultTasks);
    }

    /**
     * Prints all visible tasks in the default tasks list that are due between the given
     * start and end dates (inclusive) to the console with the specified indentation.
     *
     * @param date01 the start date to filter tasks by
     * @param date02 the end date to filter tasks by
     * @throws IllegalArgumentException if either date is null or if date02 is before date01
     */
    public void printTasksBetween(LocalDate date01, LocalDate date02) {
        printFilteredTasks((task) -> {
            LocalDate dueDate = task.getDate();
            if (dueDate == null) {
                return false;
            }
            return ((!dueDate.isBefore(date01) && !dueDate.isAfter(date02)) || (!dueDate.isBefore(date02) && !dueDate.isAfter(date01)));
        }, this.defaultTasks);
    }

    /**
     * Prints all tasks in the given list that are visible, satisfy the given predicate,
     * and have no parent task to the console with the specified indentation.
     *
     * @param predicate the predicate to test tasks against
     * @param list the list of tasks to print
     * @throws IllegalArgumentException if the predicate is null or if the list is null
     */
    public void printFilteredTasks(Predicate<Task> predicate, List<Task> list) {
        List<Task> subTasksCopy = new ArrayList<>(list);
        Collections.sort(subTasksCopy);
        List<Task> filteredList = subTasksCopy.stream().filter(task -> !task.hasParent()).collect(Collectors.toList());
        printFilteredTaskRecursion(predicate, filteredList);
    }

    /**
     * Recursively prints all tasks in the given list that are visible, satisfy the given
     * predicate, and have no parent task to the console with the specified indentation.
     *
     * @param predicate the predicate to test tasks against
     * @param list the list of tasks to print
     * @return true if at least one task was printed, false otherwise
     * @throws IllegalArgumentException if the predicate is null or if the list is null
     */
    private boolean printFilteredTaskRecursion(Predicate<Task> predicate, List<Task> list) {
        List<Task> subTasksCopy = new ArrayList<>(list);
        Collections.sort(subTasksCopy);
        boolean hasTask = false;
        for (Task task : subTasksCopy) {
            if (task.isVisible()) {
                if (predicate.test(task)) {
                    hasTask = true;
                    printTask(task, 0);
                } else {
                    hasTask = hasTask ? true : printFilteredTaskRecursion(predicate, task.getSubTasks());
                }
            }
        }
        return hasTask;
    }

    /**
     * Returns a list of all task IDs that have duplicate names in the default tasks list.
     *
     * @return a list of all task IDs that have duplicate names in the default tasks list
     */
    public List<Integer> getDuplicates() {
        List<Integer> resultWDuplicates = new ArrayList<>();
        HashMap<String, Integer> taskMap = new HashMap<>();
        for (Task t: this.defaultTasks) {
            if (taskMap.containsKey(t.getName())) {
                resultWDuplicates.add(taskMap.get(t.getName()));
                resultWDuplicates.add(t.getId());
            } else {
                taskMap.put(t.getName(), t.getId());
            }
        }
        //remove duplicates IDs from list
        List<Integer> result = new ArrayList<>(new LinkedHashSet<>(resultWDuplicates));
        Collections.sort(result);
        return result;
    }
}
