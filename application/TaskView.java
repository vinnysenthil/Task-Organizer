package application;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;



public class TaskView implements Subject{
	
	private Observer observer;
	
	private TaskBoardModel boardModel;
	private Scene scene;
	
	private Label taskLabel = new Label("Name");
	private TextField taskTextField = new TextField();
	
	private Label descriptionLabel = new Label("Description");
	private TextField descriptionTextField = new TextField();
	
	private Label statusLabel = new Label("Status");
	private ComboBox<String> dropDownMenu = new ComboBox();

	private Label dueDateLabel = new Label("Due Date");
	private TextField dueDateTextField = new TextField();
	

	private Button createBtn = new Button("Create");
	private Button confirmBtn = new Button("Confirm");
	private Button cancelBtn = new Button("Cancel");
	
	private GridPane topGrid = new GridPane();
	private GridPane midGrid = new GridPane();
	private GridPane bottomGrid = new GridPane();
	
	private TaskModel projectModel = new TaskModel("temp");
	private int dropDownMenuIndex = 0;
	private ObservableList<String>  data = FXCollections.observableArrayList();
	private int currentTaskPassByValue = 0;

	private Scene prevScene;

/////////////////////////////////EDIT VERSION////////////////////////////////////////////////////////////
	
	public TaskView(TaskBoardModel boardModel, Scene prevScene, int dropDownMenuIndex, int currentTaskPassByValue){
	
		this.currentTaskPassByValue = currentTaskPassByValue;
		this.dropDownMenuIndex = dropDownMenuIndex;
		this.prevScene = prevScene;
		this.boardModel = boardModel;
		this.descriptionTextField.setPrefWidth(200);
		this.descriptionTextField.setPrefHeight(70);
		
		
		VBox root = new VBox();
		this.topGrid.setAlignment(Pos.TOP_LEFT);
		this.topGrid.setHgap(10);
		this.topGrid.setVgap(20);
		this.topGrid.setPadding(new Insets(25, 25, 25, 25));
		this.midGrid.setHgap(10);
		this.midGrid.setVgap(20);
		this.midGrid.setAlignment(Pos.TOP_LEFT);
		
		this.taskTextField.setText(boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).getTaskName());
		this.descriptionTextField.setText(boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).getDescription());
		this.dropDownMenu.getSelectionModel().select(boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).getStatus());
		this.dueDateTextField.setText(boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).getDueDate());
		
		this.topGrid.add(this.taskLabel, 1, 1);
		this.topGrid.add(this.taskTextField, 2, 1);
		
		this.midGrid.add(this.descriptionLabel, 1, 1);
		this.midGrid.add(this.descriptionTextField, 2, 1);
		this.midGrid.add(this.statusLabel, 1, 2);
		this.midGrid.add(this.dropDownMenu, 2, 2);
		this.midGrid.add(this.dueDateLabel, 1, 3);
		this.midGrid.add(this.dueDateTextField, 2, 3);
		
		this.bottomGrid.setAlignment(Pos.BOTTOM_RIGHT);
		this.bottomGrid.add(this.confirmBtn, 1, 1);
		this.bottomGrid.add(this.cancelBtn, 2, 1);
		
		for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getColumns().size(); i++){
		data.add(boardModel.getProjectList().get(dropDownMenuIndex).getColumns().get(i));  		
		}
		dropDownMenu.setItems(data);
		
		this.scene = new Scene(root, 500, 500);
		root.getChildren().addAll(this.topGrid, this.midGrid, this.bottomGrid);
		
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
		
			@Override
			public void handle(ActionEvent e) {
			editTask();
			}
		});
		
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent e) {
			backToMainScreen("cancel");
			
			}
		});
	}

	public TaskView(TaskBoardModel boardModel, Scene prevScene, int dropDownMenuIndex){
		
		this.dropDownMenuIndex = dropDownMenuIndex;
		this.prevScene = prevScene;
		this.boardModel = boardModel;
		this.descriptionTextField.setPrefWidth(200);
		this.descriptionTextField.setPrefHeight(70);

		
		VBox root = new VBox();
		this.topGrid.setAlignment(Pos.TOP_LEFT);
		this.topGrid.setHgap(10);
		this.topGrid.setVgap(20);
		this.topGrid.setPadding(new Insets(25, 25, 25, 25));
		this.midGrid.setHgap(10);
		this.midGrid.setVgap(20);
		this.midGrid.setAlignment(Pos.TOP_LEFT);
		
		this.topGrid.add(this.taskLabel, 1, 1);
		this.topGrid.add(this.taskTextField, 2, 1);
		
	
		this.midGrid.add(this.descriptionLabel, 1, 1);
		this.midGrid.add(this.descriptionTextField, 2, 1);
		this.midGrid.add(this.statusLabel, 1, 2);
		this.midGrid.add(this.dropDownMenu, 2, 2);
		this.midGrid.add(this.dueDateLabel, 1, 3);
		this.midGrid.add(this.dueDateTextField, 2, 3);
		
		this.bottomGrid.setAlignment(Pos.BOTTOM_RIGHT);
		this.bottomGrid.add(this.createBtn, 1, 1);
		this.bottomGrid.add(this.cancelBtn, 2, 1);
		
		for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getColumns().size(); i++){
			data.add(boardModel.getProjectList().get(dropDownMenuIndex).getColumns().get(i));  		
    	}
    	dropDownMenu.setItems(data);
		
		this.scene = new Scene(root, 500, 500);
		root.getChildren().addAll(this.topGrid, this.midGrid, this.bottomGrid);
		
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	createTask();
		    }
		});
		
		cancelBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
				backToMainScreen("cancel");

		    }
		});
		
		
	}
	
	public void backToMainScreen(String s){
		Stage stageTheLabelBelongs = (Stage) this.cancelBtn.getScene().getWindow();
    	stageTheLabelBelongs.setScene(this.prevScene); 
    	if (s.equals("task")) {
    		this.notifyObserver("task");
    	}else {
    		this.notifyObserver("cancel");
    	}
		
	}
	
	public void createTask(){
		
		projectModel.setTaskName(taskTextField.getText());
		projectModel.setDescription(descriptionTextField.getText());
		projectModel.setStatus(dropDownMenu.getValue());
		projectModel.setDueDate(dueDateTextField.getText());
		this.boardModel.getProjectList().get(dropDownMenuIndex).addTaskToList(projectModel);
		backToMainScreen("task");
		
	}
	
	public Scene getScene(){
		return this.scene;
	}
	
	public void editTask(){
		
		boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).setTaskName(taskTextField.getText());
		boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).setDescription(descriptionTextField.getText());
		boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).setStatus(dropDownMenu.getValue());
		boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(currentTaskPassByValue).setDueDate(dueDateTextField.getText());
		backToMainScreen("task");
	}
	@Override
	public void register(Observer o) {
		this.observer = o;
	}
	@Override
	public void unregister(Observer o) {
		// Future
	}
	@Override
	public void notifyObserver(String s){
		observer.update(s);
	}
}
