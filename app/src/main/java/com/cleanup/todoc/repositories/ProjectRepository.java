package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;

import com.cleanup.todoc.base.database.dao.ProjectDao;
import com.cleanup.todoc.model.Project;

import java.util.List;

public class ProjectRepository {

    private  final  ProjectDao projectDao;
    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }
    public ProjectDao getProjectDao() {
        return projectDao;
    }
     // --- GET ---
   public LiveData<Project> getProject(Long id){
        return projectDao.getProject(id);
   }
   public LiveData<List<Project>> getAllProject(){
       return projectDao.getAllProject();
   }
    // --- CREATE ---
    public void createProject(Project project){
        projectDao.createProject(project);
    }

}
