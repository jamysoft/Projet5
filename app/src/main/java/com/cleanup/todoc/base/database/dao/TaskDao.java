package com.cleanup.todoc.base.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cleanup.todoc.model.Task;

import java.util.List;

@Dao
public interface TaskDao {
        @Insert
        long insertTask(Task task);
        @Update
        int updateTask(Task task);

        @Query("SELECT * FROM Task WHERE projectId = :projectId")
        LiveData<List<Task>> getTasksByProjectId(long projectId);

        @Query("SELECT * FROM Task ")
        LiveData<List<Task>> getAllTasks();

        @Query("DELETE FROM Task WHERE id = :taskId")
        int deleteTask(long taskId);
        @Query("DELETE FROM Task")
        void deleteAllTask();

        @Query("SELECT * FROM Task WHERE id = :taskId")
        LiveData<Task> getTaskById(long taskId);

        @Query("SELECT * FROM Task order by UPPER(name) ASC")
        LiveData<List<Task>> getAllTasksSortAZ();

        @Query("SELECT * FROM Task order by UPPER(name) DESC")
        LiveData<List<Task>> getAllTasksSortZA();

        @Query("SELECT * FROM Task order by creationTimestamp DESC")
        LiveData<List<Task>> getAllTasksSortRecentFirst();

        @Query("SELECT * FROM Task order by creationTimestamp ASC")
        LiveData<List<Task>> getAllTasksSortOldFirst();



}
