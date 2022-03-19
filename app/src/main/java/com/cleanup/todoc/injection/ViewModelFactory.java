package com.cleanup.todoc.injection;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cleanup.todoc.base.database.dao.TodocDatabase;
import com.cleanup.todoc.myviewmodel.TaskViewModel;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ProjectRepository projectDataSource;

    private final TaskRepository taskDataSource;

    private final Executor executor;

    private static ViewModelFactory factory;

    public static ViewModelFactory getInstance(Context context,String nameDatabase) {

        if (factory == null) {
            synchronized (ViewModelFactory.class) {

                if (factory == null) {
                    factory = new ViewModelFactory(context,nameDatabase);
                }
            }

        }

        return factory;

    }

    private ViewModelFactory(Context context,String nameDatabase) {

        TodocDatabase database = TodocDatabase.getInstance(context,nameDatabase);
        this.projectDataSource = new ProjectRepository(database.ProjectDao());
        this.taskDataSource = new TaskRepository(database.TaskDao());
        this.executor = Executors.newSingleThreadExecutor();
    }


    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
           //
            return (T) new TaskViewModel(projectDataSource, taskDataSource, executor);
        }

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

