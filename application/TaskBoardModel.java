package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskBoardModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private String taskBoardName = "TaskBoard 1";
	private List<ProjectModel> taskBoardProjects = new ArrayList<ProjectModel>();
	private String taskBoardFileName;

	public TaskBoardModel(String name){
		this.taskBoardName = name;
	}
	
	public void addProjectToList(ProjectModel projectModel){
		this.taskBoardProjects.add(projectModel);
	}
	
	public List<ProjectModel> getProjectList(){
		return this.taskBoardProjects;
	}

	
}
