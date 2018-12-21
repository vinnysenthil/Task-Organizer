package application;

import java.io.Serializable;

public class TaskModel implements Serializable{

	private String taskName = "Task1";
	private String description;
	private String dueDate;
	private String status;
	
	public TaskModel(String taskName){
		this.taskName = taskName;
	}
	
	public String getTaskName(){
		return this.taskName;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	public String getDueDate(){
		return this.dueDate;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public void setTaskName(String taskName){
		this.taskName = taskName;
	}
	
	public void setDescription(String description){
		this.description = description;
	}
	
	public void setDueDate(String dueDate){
		this.dueDate = dueDate;
	}
	
	public void setStatus(String status){
		this.status = status;
	}
}
