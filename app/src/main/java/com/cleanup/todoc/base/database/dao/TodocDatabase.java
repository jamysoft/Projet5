package com.cleanup.todoc.base.database.dao;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import java.util.concurrent.Executors;

@Database(entities = {Project.class, Task.class}, version = 1, exportSchema = false)

public abstract class TodocDatabase extends RoomDatabase {

    // --- SINGLETON ---

    private static volatile TodocDatabase INSTANCE;

    // --- DAO ---

    public abstract ProjectDao ProjectDao();

    public abstract TaskDao TaskDao();

    // --- INSTANCE ---

    public static TodocDatabase getInstance(Context context, String nameDatabase) {

        if (INSTANCE == null) {

            synchronized (TodocDatabase.class) {

                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),

                            TodocDatabase.class, nameDatabase)

                            .addCallback(prepopulateDatabase())

                            .build();
                }

            }

        }

        return INSTANCE;

    }

    private static Callback prepopulateDatabase() {

        return new Callback() {

            @Override

            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                prepopulateFakeProject();
            }
        };
    }
    //prepopulate the projects of the application
  public static void prepopulateFakeProject() {
  Executors.newSingleThreadExecutor().execute(() -> INSTANCE.ProjectDao().
               createProject(new Project(1L,"Projet Tartampion", 0xFFEADAD1)));

                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.ProjectDao().
                        createProject(new Project(2L, "Projet Lucidia", 0xFFB4CDBA)));

                Executors.newSingleThreadExecutor().execute(() -> INSTANCE.ProjectDao().
                        createProject((new Project(3L, "Projet Circus", 0xFFA3CED2))));
    }

}
