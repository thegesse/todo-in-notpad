package services;

import java.util.List;
import java.util.ArrayList;


public class TaskService {

  private final List<Task> tasks = new ArrayList<>();
  private int currentId = 0;
  
  public Task addTask(Task task) {
  
    task.setId(currentId++);
    tasks.add(task);
    return task;
  }
  
  public void deleteTask(int id) {
  
    task.removeIf(task -> task.getId() == id);
  }
  
  public List<Task> getAllTasks() {
    return new ArrayList<>(tasks);
  }
}
