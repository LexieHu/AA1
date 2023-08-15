package edu.kit.kastel.model;

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

    private static final String TAG_ALREADY_USED_ERROR = "Given tag is already used.";
    private static final String TASK_ALREADY_ASSIGNED_ERROR = "Given task is already assigned in list.";
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
     * @throws IllegalArgumentException if the tag is already used for this task list
     */
    public void add(String tag) {
        if (listTags.contains(tag)) {
            throw new IllegalArgumentException(TAG_ALREADY_USED_ERROR);
        }
        listTags.add(tag);
    }

    /**
     * Assigns the given task to this task list.
     *
     * @param task the task to assign to this task list
     * @throws IllegalArgumentException if the task is already assigned to this task list
     */
    public void assignTaskForList(Task task) {
        if (list.contains(task)) {
            throw new IllegalArgumentException(TASK_ALREADY_ASSIGNED_ERROR);
        }
        task.addThisToList(this);
        list.add(task);
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
