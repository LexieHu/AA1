package edu.kit.kastel.model;

import edu.kit.kastel.exception.IllegalRestoreException;
import edu.kit.kastel.exception.TagAlreadyUsedException;
import edu.kit.kastel.exception.TaskDeletedException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A ask of the procrastinot platform.
 *
 * @author uyzlh
 * @version 1.0
 */
public class Task implements Comparable<Task> {
    private final int id;
    private final String name;
    private final List<String> taskTags = new ArrayList<>();
    private final List<Task> subTasks = new ArrayList<>();
    private List<TaskList> holdingLists = new ArrayList<>();
    private boolean state;
    private boolean visible = true;
    private Task parentTask = null;
    private Priority priority;
    private LocalDate date;
   
    /**
     * Instantiates a new Task object with the given state, ID, name, priority, and due date.
     * @param state the state of the task (true for completed, false for incomplete)
     * @param id the ID of the task
     * @param name the name of the task
     * @param priority the priority of the task
     * @param date the due date of the task
     */
    public Task(boolean state, int id, String name, Priority priority, LocalDate date) {
        this.state = state;
        this.id = id;
        this.name = name;
        this.priority = priority;
        this.date = date;
    }

    /**
     * Returns the ID of this task.
     *
     * @return the ID of this task
     */
    public int getId() {
        return this.id;
    }

    /**
     * Returns the name of this task.
     *
     * @return the name of this task
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the parent task of this task.
     *
     * @return the parent task of this task, or null if this task has no parent
     */
    public Task getParentTask() {
        return this.parentTask;
    }

    /**
     * Returns a list of all subtasks of this task.
     *
     * @return a list of all subtasks of this task
     */
    public List<Task> getSubTasks() {
        return this.subTasks;
    }

    /**
     * Returns the due date of this task.
     *
     * @return the due date of this task
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Returns a list of all subtasks of this task.
     *
     * @return a list of all subtasks of this task
     */
    public List<Task> getSubtasks() {
        return this.subTasks;
    }

    /**
     * Returns true if this task is visible, false otherwise.
     *
     * @return true if this task is visible, false otherwise
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Returns true if this task has a parent task, false otherwise.
     *
     * @return true if this task has a parent task, false otherwise
     */
    public boolean hasParent() {
        if (this.parentTask != null) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if this task is completed, false otherwise.
     *
     * @return true if this task is completed, false otherwise
     */
    public boolean isCompleted() {
        return this.state;
    }

    /**
     * Sets the due date of this task to the given date.
     *
     * @param date the new due date of this task
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Sets the priority of this task to the given priority.
     *
     * @param priority the new priority of this task
     */
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    /**
     * Sets the parent task of this task to the given task.
     *
     * @param task the new parent task of this task
     */     
    public void setParentTask(Task task) {
        this.parentTask = task;
    }

    /**
     * Compares this task to the given task based on priority.
     *
     * @param other the task to compare to this task
     * @return a negative integer, zero, or a positive integer as this task is less than, equal to, or greater than the given task
     */
    @Override
    public int compareTo(Task other) {
        return this.priority.compareTo(other.priority);
    }

    /**
     * Adds the given tag to the list of tags for this task.
     *
     * @param tag the tag to add to the list of tags for this task
     * @throws TagAlreadyUsedException if the tag is already used for this task
     */
    public void addTag(String tag) throws TagAlreadyUsedException {
        if (taskTags.contains(tag)) {
            throw new TagAlreadyUsedException(tag);
        }
        taskTags.add(tag);
    }

    /**
     * Adds the given task as a subtask of this task.
     *
     * @param subTask the task to add as a subtask of this task
     */
    public void addSubTask(Task subTask) {
        subTasks.add(subTask);
    }

    /**
     * Removes the given task from the list of subtasks of this task.
     *
     * @param subTask the task to remove from the list of subtasks of this task
     */
    public void removeSubTask(Task subTask) {
        subTasks.remove(subTask);
    }

    /**
     * Toggles the state of this task and all its subtasks to the given state.
     *
     * @param b the new state of this task and its subtasks
     */
    public void toggle(boolean b) {
        this.state = b;
        for (Task task : subTasks) {
            task.toggle(b);
        }
    }

    /**
     * Marks this task as not visible and marks all its subtasks as not visible.
     *
     * @throws TaskDeletedException if the task is already deleted
     */
    public void delete() throws TaskDeletedException {
        if (!this.visible) {
            throw new TaskDeletedException();
        }
        this.visible = false;
        for (Task task : subTasks) {
            task.delete();
        }
    }

    /**
     * Marks this task as visible and marks all its subtasks as visible.
     * Also moves this task to the end of its parent's list of subtasks, if it has a parent.
     * Also adds this task to all the given task lists.
     *
     * @throws IllegalRestoreException if the task cannot be restored
     */
    public void restore() throws IllegalRestoreException {
        if (this.visible) {
            throw new IllegalRestoreException(this.id);
        }
        this.visible = true;
        List<Task> copySubtasks = new ArrayList<>(subTasks);
        for (Task task : copySubtasks) {
            task.restore();
        }
        if (hasParent()) {
            List<Task> taskList = parentTask.getSubTasks();
            taskList.remove(this);
            taskList.add(this);
        }
        for (TaskList list : this.holdingLists) {
            list.pushTaskToEndOfList(this);
        }
    }

    /**
     * Adds this task to the given task list.
     *
     * @param list the task list to add this task to
     */
    public void addThisToList(TaskList list) {
        this.holdingLists.add(list);
    }

    /**
     * Returns the number of subtasks of this task, including all nested subtasks.
     *
     * @return the number of subtasks of this task, including all nested subtasks
     */
    public int getNumberOfSubtasks() {
        int i = 0;
        for (Task task : subTasks) {
            i += task.getNumberOfSubtasks() + 1;
        }
        return i;
    }

    /**
     * Returns a string representation of this task, including its state, name, priority, tags, and due date.
     *
     * @return a string representation of this task, including its state, name, priority, tags, and due date
     */
    public String print() {
        StringBuilder s = new StringBuilder();
        s.append("- ");
        if (state) {
            s.append("[x] ");
        } else {
            s.append("[ ] ");
        }
        s.append(this.name);

        if (this.priority != Priority.NONE) {
            s.append(" [" + this.priority.toString() + "]");
        }
        if (!taskTags.isEmpty() || date != null) {
            s.append(":");
        }

        if (!taskTags.isEmpty()) {
            s.append(" (");
            for (String tag : taskTags) {
                s.append(tag);
                s.append(", ");
            }
            s.deleteCharAt(s.length() - 1);
            s.deleteCharAt(s.length() - 1);
            s.append(")");
        }

        if (date != null) {
            s.append(" --> " + this.date.toString());
        }

        return s.toString();
    }

    /**
     * Returns true if this task contains the given task as a subtask, false otherwise.
     *
     * @param task the task to check for containment
     * @return true if this task contains the given task as a subtask, false otherwise
     */
    public boolean contains(Task task) {
        if (this.subTasks.contains(task)) {
            return true;
        }
        for (Task subTask : subTasks) {
            if (subTask.contains(task)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true if this task has the given tag, false otherwise.
     *
     * @param tag the tag to check for
     * @return true if this task has the given tag, false otherwise
     */
    public boolean hasTag(String tag) {
        for (String t : taskTags) {
            if (Objects.equals(t, tag)) {
                return true;
            }
        }
        return false;
    }
  
    /**
     * Returns true if this task has an undone child task, false otherwise.
     *
     * @return true if this task has an undone child task, false otherwise
     */
    public boolean hasUndoneChild() {
        for (Task task: subTasks) {
            if (!task.isCompleted()) {
                return true;
            }
            if (task.hasUndoneChild()) {
                return true;
            }
        }
        return false;
    }
}
