package com.cleanup.todoc.myviewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
import com.cleanup.todoc.ui.MainActivity;
import java.util.List;
import java.util.concurrent.Executor;

public class TaskViewModel extends ViewModel {

    // REPOSITORIES
    private final ProjectRepository projectDataSource;
    private final TaskRepository TaskDataSource;
    //Executor
    private final Executor executor;

    public TaskViewModel(ProjectRepository projectDataSource, TaskRepository taskDataSource, Executor executor) {
        this.projectDataSource = projectDataSource;
        TaskDataSource = taskDataSource;
        this.executor = executor;
    }

    // FOR Project
  public LiveData<Project> getProject(long id){
       return  projectDataSource.getProject(id);
  }
    public LiveData<List<Project>> getAllProject() {
        return projectDataSource.getAllProject();
    }

  public void createProject(long id, String name, int color) {
    executor.execute(() -> {
        projectDataSource.createProject(new Project(id,name,color));

    });
}

    //FOR TASKS
    // --- GET ---
    public LiveData<List<Task>> getTasksByProjectId(Long idProject){
        return TaskDataSource.getTasksByProjectId(idProject);
    }
    public LiveData<List<Task>> getAllTasks(){

        return TaskDataSource.getAllTasks();
    }

    // --- CREATE ---
        public void createTask(long projectId, String name, long creationTimestamp){
            executor.execute(() -> {
                TaskDataSource.createTask(new Task(projectId, name,creationTimestamp));

            });
    }

    public void deleteTask(Task task) {
        executor.execute(() -> TaskDataSource.deleteTask(task));
    }

    public void updateTask(Task task) {
        executor.execute(() -> TaskDataSource.updateTask(task));
    }
   public LiveData<List<Task>> getAllTasksSort(MainActivity.SortMethod sortMethod ) {
       LiveData<List<Task>> res;
        switch (sortMethod) {
            case ALPHABETICAL:
                res=TaskDataSource.getAllTasksSortAZ();
            break;
            case ALPHABETICAL_INVERTED:
               res=TaskDataSource.getAllTasksSortZA();
            break;
            case RECENT_FIRST:
                res= TaskDataSource.getAllTasksSortRecentFirst();
            break;
            case OLD_FIRST:
               res= TaskDataSource.getAllTasksSortOldFirst();
            break;
            default:
                res=TaskDataSource.getAllTasks();
        }
        return res;
    }

    public void deleteAllTask() {
        TaskDataSource.getAllTasks();
    }
}
