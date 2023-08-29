package edu.kit.kastel.model;

import edu.kit.kastel.exception.IllegalAddListException;
import edu.kit.kastel.exception.IllegalAssignException;
import edu.kit.kastel.exception.ListNotFoundException;
import edu.kit.kastel.exception.NoTaskFoundException;
import edu.kit.kastel.exception.TagAlreadyUsedException;
import edu.kit.kastel.exception.TaskDeletedException;
import edu.kit.kastel.exception.TaskNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
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
    private static final String SUBSTRING = " ";
    private static final int DATES_TO_ADD = 6;
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
     * @throws IllegalAddListException if the list to add is already appeared in lists
     */
    public void addList(TaskList list) throws IllegalAddListException {
        for (TaskList taskList : lists) {
            if (taskList.getListName().equals(list.getListName())) {
                throw new IllegalAddListException(list.getListName());
            }
        }
        lists.add(list);
    }

    /**
     * Retrieves the task with the given ID from the default tasks list.
     *
     * @param id the unique ID of the task to retrieve
     * @return the Task object with the given ID
     * @throws TaskNotFoundException if the ID is not found in the default tasks list
     */
    public Task getTask(int id) throws TaskNotFoundException {
        return defaultTasks.stream().filter(t -> t.getId() == id).findFirst().orElseThrow(() -> new TaskNotFoundException(id));
    }

    /**
     * Retrieves the task list with the given name from the list of task lists.
     *
     * @param name the name of the task list to retrieve
     * @return the TaskList object with the given name
     * @throws ListNotFoundException if the name is not found in the list of task lists
     */
    public TaskList getTaskListByName(String name) throws ListNotFoundException {
        for (TaskList list : lists) {
            if (list.getListName().equals(name)) {
                return list;
            }
        }
        throw new ListNotFoundException(name);
    }

    /**
     * Adds the given tag to the task with the given ID in the default tasks list.
     *
     * @param id the id of the task to add the tag to
     * @param tag the tag to add to the task
     * @throws TaskNotFoundException if the ID is not found in the default tasks list
     * @throws TagAlreadyUsedException if the tag is already used
     */
    public void addTag(int id, String tag) throws TaskNotFoundException, TagAlreadyUsedException {
        Task task = getTask(id);
        if (task != null) {
            task.addTag(tag);
        } else {
            throw new TaskNotFoundException(id);
        }
    }

    /**
     * Adds the given tag to the task list with the given name in the list of task lists.
     *
     * @param listName the name of the task list to add the tag to
     * @param tag the tag to add to the task list
     * @throws ListNotFoundException if the name is not found in the list of task lists
     * @throws TagAlreadyUsedException if the tag is already used
     */
    public void addListTag(String listName, String tag) throws ListNotFoundException, TagAlreadyUsedException {
        TaskList list = getTaskListByName(listName);
        if (list != null) {
            list.add(tag);
        } else {
            throw new ListNotFoundException(listName);
        }
    }

    /**
     * Assigns the task with the given subtask ID as a subtask of the task with the given parent task ID.
     *
     * @param subtaskId the ID of the subtask to assign
     * @param parentTaskId the ID of the parent task to assign the subtask to
     * @throws TaskNotFoundException if the ID is not found in the default tasks list
     * @throws IllegalAssignException if the assign is illegal
     * @throws TaskDeletedException if either the subtask or parent task is deleted or if the subtask already contains the parent task
     */
    public void assignTaskForTask(int subtaskId, int parentTaskId) throws TaskNotFoundException,
            IllegalAssignException, TaskDeletedException {
        Task subTask = getTask(subtaskId);
        Task parentTask = getTask(parentTaskId);

        if (!(subTask.isVisible()) || !(parentTask.isVisible())) {
            throw new TaskDeletedException();
        }
        if (subTask.contains(parentTask) || parentTask.contains(subTask)) {
            throw new IllegalAssignException();
        }
        if (subtaskId == parentTaskId) {
            throw new IllegalAssignException(subtaskId);
        }
        if (subTask.getParentTask() != null) {
            subTask.getParentTask().removeSubTask(subTask);
        }
        parentTask.getSubTasks().add(subTask);
        subTask.setParentTask(parentTask);
    }

    /**
     * Prints the given task and its subtasks (if any) to the console with the specified indentation.
     *
     * @param task the task to print
     * @param indentation the number of spaces to indent the task and its subtasks
     */
    public void printTask(Task task, int indentation) {
        String s = buildString(SUBSTRING, indentation);
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
        String s = buildString(SUBSTRING, indentation);
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
     * @return boolean whether something was printed
     * @throws ListNotFoundException if the task list with the given name does not exist
     */
    public boolean printList(String name) throws ListNotFoundException {
        TaskList list = getTaskListByName(name);
        boolean printed = false;
        for (Task task : list.getListCopy()) {
            if (task.isVisible() && (!list.getListCopy().contains(task.getParentTask())
                    || (list.getListCopy().contains(task.getParentTask())
                    && task.getParentTask() != null && !task.getParentTask().isVisible()))) {
                printTask(task,  0);
                printed = true;
            }
        }
        return printed;
    }

    /**
    * Prints all visible todo tasks in the default tasks list to the console with the specified indentation.
     *
     * @throws NoTaskFoundException if no tasks in the system or all tasks are completed
    */
    public void printTodoTasks() throws NoTaskFoundException {
        if (defaultTasks.isEmpty()) {
            throw new NoTaskFoundException();
        }
        boolean hasFound = false;
        List<Task> tasksCopy = new ArrayList<>(defaultTasks);
        Collections.sort(tasksCopy);
        for (Task task : tasksCopy) {
            if (task.isVisible() && (task.getParentTask() == null) && (task.hasUndoneChild() || !task.isCompleted())) {
                hasFound = true;
                printTaskConditional(((subTask) -> (subTask.hasUndoneChild() || !subTask.isCompleted())), task, 0);
            }
        }
        if (!hasFound) {
            throw new NoTaskFoundException();
        }
    }

    /**
     * Gets list of tasks contained within the parameter list that have the given tag with specified indentation.
     *
     * @param tag the tag to filter tasks by
     * @param list the list to search for matching tasks
     * @param top whether this is the top level recursion step
     * @return a list of tasks that have the specified tag
     * @throws NoTaskFoundException if the tag is null or empty
     */
    public List<Task> getTasksWithTag(String tag, List<Task> list, boolean top) throws NoTaskFoundException {
        if (list.stream().filter(Task::isVisible).toList().isEmpty() && top) {
            throw new NoTaskFoundException();
        }
        List<Task> result = new ArrayList<>();
        List<Task> tasksCopy = new ArrayList<>(list.stream().filter(Task::isVisible).toList());
        for (Task element: tasksCopy) {
            if (element.hasTag(tag)) {
                result.add(element);
            } else {
                result.addAll(this.getTasksWithTag(tag, element.getSubTasks(), false));
            }
        }
        return result;
    }

    /**
     * Prints all visible tasks in the default tasks list that contain the given name to the console with the specified indentation.
     *
     * @param name the name to filter tasks by
     * @throws NoTaskFoundException if the name is null or empty
     */
    public void findTasksWithName(String name) throws NoTaskFoundException {
        printFilteredTasks((task) -> task.getName().contains(name), defaultTasks);
    }

    /**
     * Prints all visible tasks that are due within the next seven days with the specified indentation.
     *
     * @param date the date to filter tasks by
     * @throws NoTaskFoundException if the date is null or in the past
     */
    public void upcomingDue(LocalDate date) throws NoTaskFoundException {
        printFilteredTasks((task) -> {
            LocalDate dueDate = task.getDate();
            if (dueDate == null) {
                return false;
            }
            return !dueDate.isBefore(date) && !dueDate.isAfter(date.plusDays(DATES_TO_ADD));
        }, this.defaultTasks);
    }

    /**
     * Prints all visible tasks in the default tasks list that are due before the given date to the console with the specified indentation.
     *
     * @param date the date to filter tasks by
     * @throws NoTaskFoundException if the date is null or in the past
     */
    public void printTasksBefore(LocalDate date) throws NoTaskFoundException {
        printFilteredTasks((task) -> {
            LocalDate dueDate = task.getDate();
            if (dueDate == null) {
                return false;
            }
            return !dueDate.isAfter(date);
        }, this.defaultTasks);
    }

    /**
     * Prints all visible tasks in the default tasks list that are due between the given start and end dates with indentation.
     *
     * @param date01 the start date to filter tasks by
     * @param date02 the end date to filter tasks by
     * @throws NoTaskFoundException if either date is null or if date02 is before date01
     */
    public void printTasksBetween(LocalDate date01, LocalDate date02) throws NoTaskFoundException {
        printFilteredTasks((task) -> {
            LocalDate dueDate = task.getDate();
            if (dueDate == null) {
                return false;
            }
            return ((!dueDate.isBefore(date01) && !dueDate.isAfter(date02)) || (!dueDate.isBefore(date02) && !dueDate.isAfter(date01)));
        }, this.defaultTasks);
    }

    /**
     * Prints all tasks in the given list that are visible, fulfil the predicate and have no parent task specified indentation.
     *
     * @param predicate the predicate to test tasks against
     * @param list the list of tasks to print
     * @throws NoTaskFoundException the same exception that was passed
     */
    public void printFilteredTasks(Predicate<Task> predicate, List<Task> list) throws NoTaskFoundException {
        if (list.stream().filter(Task::isVisible).toList().isEmpty()) {
            throw new NoTaskFoundException();
        }
        List<Task> subTasksCopy = new ArrayList<>(list);
        Collections.sort(subTasksCopy);
        List<Task> filteredList = subTasksCopy.stream().filter(task -> task.getParentTask() == null).collect(Collectors.toList());
        if (!printFilteredTaskRecursion(predicate, filteredList)) {
            throw new NoTaskFoundException();
        }
    }

    /**
     * Recursively prints all tasks in the given list that are visible, satisfy the predicate, and have no parent task with indentation.
     *
     * @param predicate the predicate to test tasks against
     * @param list the list of tasks to print
     * @return true if at least one task was printed, false otherwise
     */
    private boolean printFilteredTaskRecursion(Predicate<Task> predicate, List<Task> list) {
        List<Task> subTasksCopy = new ArrayList<>(list);
        Collections.sort(subTasksCopy);
        boolean hasTask = false;
        for (Task task : subTasksCopy) {
            if (predicate.test(task)) {
                hasTask = true;
                if (task.isVisible()) {
                    printTask(task, 0);
                }
            } else {
                hasTask = hasTask ? true : printFilteredTaskRecursion(predicate, task.getSubTasks());
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
        List<Integer> result = new ArrayList<>();
        List<Task> filteredDefault = this.defaultTasks.stream().filter((task) -> task.isVisible()).toList();
        List<Task> tasks;
        for (Task element : filteredDefault) {
            tasks = new ArrayList<>(filteredDefault);
            tasks.remove(element);
            for (Task nextTask : tasks) {
                if (element.getName().equals(nextTask.getName())) {
                    if (element.getDate() == null || nextTask.getDate() == null
                            || element.getDate().equals(nextTask.getDate())) {
                        result.add(element.getId());
                    }
                }
            }
        }
        return result;
    }
}
