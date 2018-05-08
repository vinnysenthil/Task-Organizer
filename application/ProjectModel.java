package application;

import java.util.ArrayList;
import java.util.List;

public class ProjectModel {
	
	private String projectName = "Project1";
	private List<String> columns = new ArrayList<String>();
	private List<TaskModel> taskList;
	
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
	
	
}
