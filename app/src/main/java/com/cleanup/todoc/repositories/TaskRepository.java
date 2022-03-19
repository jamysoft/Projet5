package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.base.database.dao.TaskDao;
import com.cleanup.todoc.model.Task;

import java.util.List;

public class TaskRepository {

    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    public TaskDao getTaskDao() {
        return taskDao;
    }
    // --- GET ---

    public LiveData<List<Task>> getTasksByProjectId(Long idProject){
        return taskDao.getTasksByProjectId(idProject);
    }
    public LiveData<List<Task>> getAllTasks(){
        return taskDao.getAllTasks();
    }

    // --- CREATE ---
   public void createTask(Task task){
        taskDao.insertTask(task);
   }

    // --- DELETE ---
    public void deleteTask(Task task){
            taskDao.deleteTask(task.getId());
    }

    // --- UPDATE ---

    public void updateTask(Task task){
        taskDao.updateTask(task);
    }


    public LiveData<List<Task>> getAllTasksSortAZ() {
       return taskDao.getAllTasksSortAZ();
    }
    public LiveData<List<Task>> getAllTasksSortZA() {
        return taskDao.getAllTasksSortZA();
    }
    public LiveData<List<Task>> getAllTasksSortOldFirst() {
        return taskDao.getAllTasksSortOldFirst();
    }
    public LiveData<List<Task>> getAllTasksSortRecentFirst() {
        return taskDao.getAllTasksSortRecentFirst();
    }

}
