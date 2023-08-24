package edu.kit.kastel.model;

import edu.kit.kastel.exception.IllegalAssignException;
import edu.kit.kastel.exception.TagAlreadyUsedException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A list of tasks of the procrastinot platform.
 *
 * @author uyzlh
 * @version 1.0
 */
public class TaskList {

    private final List<String> listTags = new ArrayList<>();
    private final List<Task> list = new ArrayList<>();
    private final String name;

    /**
     * Initialize a new task list with the given name.
     *
     * @param name the name of the task list
     */
    public TaskList(String name) {
        this.name = name;
    }

    /**
     * Adds the given tag to the list of tags for this task list.
     *
     * @param tag the tag to add to the list of tags for this task list
     * @throws TagAlreadyUsedException if the tag is already used for this task list
     */
    public void add(String tag) throws TagAlreadyUsedException {
        if (listTags.contains(tag)) {
            throw new TagAlreadyUsedException(tag);
        }
        listTags.add(tag);
    }

    /**
     * Assigns the given task to this task list.
     *
     * @param task the task to assign to this task list
     * @throws IllegalAssignException if the task is already assigned to this task list
     */

    public void assignTaskForList(Task task) throws IllegalAssignException {
        if (hasTask(task)) {
            throw new IllegalAssignException(this.name);
        }
        task.addThisToList(this);
        for (Task t: task.getSubTasks()) {
            try {
                assignTaskForList(t);
            } catch (IllegalAssignException e) {
                //do nothing
            }
        }
        list.add(task);
    }

    /**
     * Returns weather this list has given task
     *
     * @param task the task to assign to this task list
     * @return true if this list contains the given task as a subtask, false otherwise
     */
    private boolean hasTask(Task task) {
        for (Task listTask : this.list) {
            if (listTask.contains(task) || listTask.getId() == task.getId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the name of this task list.
     *
     * @return the name of this task list
     */
    public String getListName() {
        return this.name;
    }

    /**
     * Returns a copy of the list of tasks in this task list, sorted by priority and due date.
     *
     * @return a copy of the list of tasks in this task list, sorted by priority and due date
     */
    public List<Task> getListCopy() {
        List<Task> listCopy = new ArrayList<>(list);
        Collections.sort(listCopy);
        return listCopy;
    }

    /**
     * Moves the given task to the end of this task list.
     *
     * @param task the task to move to the end of this task list
     */
    public void pushTaskToEndOfList(Task task) {
        this.list.remove(task);
        this.list.add(task);
    }
}
