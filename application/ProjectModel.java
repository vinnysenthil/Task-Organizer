package application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectModel implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String projectName = "Project1";
	private List<String> columns = new ArrayList<String>();
	private List<TaskModel> taskList = new ArrayList<TaskModel>();
	
	public ProjectModel(String name){
		this.projectName = name;

	}
	
	public void setProjectName(String projectName){
		this.projectName = projectName;
	}
	
	public String getProjectName(){
		return this.projectName;
	}
	
	public List<String> getColumns(){
		return this.columns;
	}
	
	public List<TaskModel> getTasks(){
		return this.taskList;
	}
	
	public void addTaskToList(TaskModel taskList){
		this.taskList.add(taskList);
	}
	
}
