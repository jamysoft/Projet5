package com.cleanup.todoc;
import static org.junit.Assert.assertTrue;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import com.cleanup.todoc.base.database.dao.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private static long PROJECT_ID = 1L;
    private static long ID_TASK1 = 1;
    private static long ID_TASK2 = 2;
    private static long ID_TASK3 = 3;
    private static final Project PROJECT_DEMO = new Project(PROJECT_ID,"Projet Tartampion", 0xFFEADAD1);
    private static final Task TASK_DEMO1 = new Task(ID_TASK1,PROJECT_ID,"NAME_DEMO", new Date().getTime());
    private static final Task TASK_DEMO2 = new Task(ID_TASK2  ,PROJECT_ID,"NAME_DEMO_2", new Date().getTime());
    private static final Task TASK_DEMO3= new Task(ID_TASK3 ,PROJECT_ID,"NAME_DEMO_3", new Date().getTime());
    private TodocDatabase database;
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before

    public void initDb()   {

        this.database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),

                TodocDatabase.class)

                .allowMainThreadQueries()

                .build();
        // Adding a new Project
      this.database.ProjectDao().createProject((new Project(PROJECT_ID,"Projet Tartampion", 0xFFEADAD1)));


    }

    @After

    public void closeDb() throws Exception {

        database.close();
    }

    @Test
    public void insertAndGetProject() throws InterruptedException {
        // Adding a new Project
      this.database.ProjectDao().createProject(PROJECT_DEMO);

        // TEST
        Project project = LiveDataTestUtil.getValue(this.database.ProjectDao().getProject(PROJECT_ID));
        assertTrue(project.getName().equals(PROJECT_DEMO.getName()) &&
                project.getId() == PROJECT_DEMO.getId() &&
                project.getColor()== PROJECT_DEMO.getColor()
        );
    }
    @Test
    public void insertAndGetTasks() throws InterruptedException {
        // Adding a new Project
       // this.database.ProjectDao().createProject((new Project(PROJECT_ID,"Projet Tartampion", 0xFFEADAD1)));
        // Adding 3 tasks
        this.database.TaskDao().insertTask(TASK_DEMO1);
        this.database.TaskDao().insertTask(TASK_DEMO2);
        this.database.TaskDao().insertTask(TASK_DEMO3);
        // TEST
        List<Task> tasks = LiveDataTestUtil.getValue(this.database.TaskDao().getAllTasks());
        assertTrue(tasks.size()==3);
    }
    @Test
    public void DeleteTask() throws InterruptedException {
        // Adding a new Project
      //  this.database.ProjectDao().createProject((new Project(PROJECT_ID,"Projet Tartampion", 0xFFEADAD1)));
        this.database.TaskDao().deleteTask(ID_TASK1);
    // TEST
        List<Task> task = LiveDataTestUtil.getValue(this.database.TaskDao().getAllTasks());
        assertTrue(task.isEmpty());
    }
    @Test
    public void UpdateTask() throws InterruptedException {
        // Adding a new Project
      //  this.database.ProjectDao().createProject((new Project(PROJECT_ID,"Projet Tartampion", 0xFFEADAD1)));
        this.database.TaskDao().insertTask(TASK_DEMO1);
        TASK_DEMO1.setName("NAME_DEMO_UP_DATED");
        this.database.TaskDao().updateTask(TASK_DEMO1);

        // TEST
        Task task = LiveDataTestUtil.getValue(this.database.TaskDao().getTaskById(ID_TASK1));
        assertTrue(task.getName().equals("NAME_DEMO_UP_DATED"));

    }
    @Test
    public void removeAllTask() throws InterruptedException {
        //TEST
        database.TaskDao().deleteAllTask();
        List<Task> items = LiveDataTestUtil.getValue(this.database.TaskDao().getAllTasks());
        System.out.println("NOMBRE DES ITEM"+items.size());
        assertTrue(items.isEmpty());

    } @Test
    public void getTasksWhenNoTaskInserted() throws InterruptedException {
        //TEST
        database.TaskDao().getAllTasks();
        List<Task> items = LiveDataTestUtil.getValue(this.database.TaskDao().getAllTasks());
        assertTrue(items.isEmpty());

    }
}
