package application;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.metal.MetalIconFactory.FolderIcon16;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;


public class MainScreen implements Observer {
	
	private Scene scene;

	private Label selectLabel = new Label("Select Project");
	private ComboBox<String> dropDownMenu = new ComboBox();
	private ObservableList<String>  data = FXCollections.observableArrayList();
	
	private Button editBtn = new Button("Edit");
	private Button saveBtn = new Button("Save");
	private Button loadBtn = new Button("Load...   ");
	private Button createBtn = new Button("Create New");
	private Button logOutBtn = new Button("Logout");
	private int projectSize = 0;
	private TaskBoardModel boardModel = new TaskBoardModel("boo");
	private GridPane topGrid = new GridPane();
	private GridPane bottomGrid = new GridPane();
	private int bottomGridCount = 0;
	
	private Button taskRefreshBtn = new Button("After task");
	private Button createRefreshBtn = new Button("After create");
	private Button editRefreshBtn = new Button("After editing");
	private int dropDownMenuIndex;
	private int currentTaskPassByValue = 0;

	// Static used as a counter
	private static int observerIDTracker = 0;
	// Used to track the observers
	private int observerID = 0;
	// Will hold reference to the ProjectView object
	private Subject projectView;
	//To get a specific ProjectView
	private ProjectView pView;
	// Will hold reference to the taskView object
	private Subject taskView;
	// to get a specific TaskView
	private TaskView tView;
	// Same
	private Subject loadView;
	// Same
	private LoadView lView;
	//taskBoardModel.getProjectList().get(INDEX OF WHICH PROJECT)
	
	//To get a specific column under a specific project
	//taskBoardModel.getProjectList().get(INDEX OF WHICH PROJECT).getColumns().get(INDEX OF WHICH COLUMN)
	
	//To get a specific task under a specific project
	//taskBoardModel.getProjectList().get(INDEX OF WHICH PROJECT).getTasks().get(INDEX OF WHICH TASKS)
	
	
	
	//Summary
	
	// MainScreen is a taskBoardModel view,
	// taskBoardModel view contains, a LIST of PROJECTMODELS, i.e. a list containing models of a project
	// EACH PROJECTMODEL contains a  LIST of STRINGS called COLUMNS, these strings are the title of our COLUMNS
	// EACH PROJECTMODEL also contains a LIST of TASKS

	
	// Features
	
	// Creation/Edit/Selection of different and unique projects
	// Creation/Edit/Selection of different and unique columns
	// Creation/Edit/Selection of different and unique tasks
	// Saving and loading projects
	
	public MainScreen() {
		
		VBox root = new VBox();
		this.topGrid.setAlignment(Pos.TOP_LEFT);
		this.topGrid.setHgap(10);
		this.topGrid.setVgap(10);
		this.topGrid.setPadding(new Insets(25, 25, 25, 25));		
		this.topGrid.add(this.selectLabel, 0, 1);
		this.topGrid.add(this.dropDownMenu, 1, 1);
		this.topGrid.add(this.editBtn, 2, 1);
		this.topGrid.add(this.saveBtn, 3, 1);
		this.topGrid.add(this.loadBtn, 4, 1);
		this.topGrid.add(this.createBtn, 5, 1);
		this.topGrid.add(this.logOutBtn, 6, 1);
		this.topGrid.add(this.createRefreshBtn, 7, 1);
		this.topGrid.add(this.editRefreshBtn, 8, 1);
		this.topGrid.add(this.taskRefreshBtn, 9, 1);
		this.scene = new Scene(root, 1000, 700);

		///////////////////////////////////////////////
		//Implement task columns

		this.bottomGrid.setAlignment(Pos.TOP_LEFT);
		this.bottomGrid.setHgap(10);
		this.bottomGrid.setVgap(10);
		this.bottomGrid.setPadding(new Insets(25, 25, 25, 25));

		root.getChildren().addAll(this.topGrid,this.bottomGrid);

		///Controller//////////////////////////////////////////////////////////////////////////////////////////////

		logOutBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	Stage stageTheLabelBelongs = (Stage) createBtn.getScene().getWindow();	        	
	        	LoginView secondPane = new LoginView();
	        	stageTheLabelBelongs.setScene(secondPane.getScene());
	        	stageTheLabelBelongs.setX(400);
	        	stageTheLabelBelongs.setY(200);  
		    }  
		});

		dropDownMenu.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
	
		    	while (bottomGrid.getChildren().size() != 0){
		    		bottomGrid.getChildren().remove(0);
		    		bottomGridCount--;
		    	}

		    	dropDownMenuIndex = dropDownMenu.getSelectionModel().getSelectedIndex();

		    	for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getColumns().size(); i++){
	        		createColumn(boardModel.getProjectList().get(dropDownMenuIndex).getColumns().get(i));
		    	}	
		    	
		    	refreshTask();
		    }
		});
		
		//Saves Project
		saveBtn.setOnAction(ActionEvent -> {
			Stage stageTheLabelBelongs = (Stage) saveBtn.getScene().getWindow();
			SaveView save = new SaveView(stageTheLabelBelongs);
			save.saveToFile(this.boardModel);
		});
				
		//Loads Project
		loadBtn.setOnAction(ActionEvent -> {
			Stage stageTheLabelBelongs = (Stage) loadBtn.getScene().getWindow();
			lView = new LoadView(stageTheLabelBelongs);
			TaskBoardModel temp;
			temp = lView.LoadFromFile();
			if (temp !=null) {
				this.boardModel = temp;
				for(int j = 0; j < this.boardModel.getProjectList().size(); j++){
					for(int i = 0; i < this.boardModel.getProjectList().get(j).getColumns().size(); i++){
						addProjectList("temp");
						refreshPage();
						refreshTask();
					}
				}
			}
		});
		
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {

		        	Stage stageTheLabelBelongs = (Stage) createBtn.getScene().getWindow();	        	
		        	pView = new ProjectView(boardModel, getScene());
		        	addSubject(pView);
		        	stageTheLabelBelongs.setScene(pView.getScene());
		        	stageTheLabelBelongs.setX(0);
		        	stageTheLabelBelongs.setY(0);
		    }
		});
		
		editBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {

		        	Stage stageTheLabelBelongs = (Stage) createBtn.getScene().getWindow();
		        	pView = new ProjectView(boardModel, getScene(), dropDownMenuIndex);
		        	addSubject(pView);
		        	stageTheLabelBelongs.setScene(pView.getScene());
		        	stageTheLabelBelongs.setX(0);
		        	stageTheLabelBelongs.setY(0);  	
		    }
		});
		

		createRefreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    		addProjectList("temp");	
		    		refreshPage();
		    }  
		});
		

		editRefreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	refreshPage();   	
		    }   
		});
		
		taskRefreshBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {	
		    	refreshTask();
		    }   
		});
		
	}
	

	
	
	public void refreshTask(){
		
		while (bottomGrid.getChildren().size() != 0){
    		bottomGrid.getChildren().remove(0);
    		bottomGridCount--;
    	}


    	for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getColumns().size(); i++){
    		createColumn(boardModel.getProjectList().get(dropDownMenuIndex).getColumns().get(i));
    	}	
		
		if (boardModel.getProjectList().get(dropDownMenuIndex).getTasks().size() != 0){
	        for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getColumns().size(); i++){
	        	for (int j = 0; j < boardModel.getProjectList().get(dropDownMenuIndex).getTasks().size();j++){
	                if (boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(j).getStatus()
	                		.equals(boardModel.getProjectList().get(dropDownMenuIndex).getColumns().get(i))){
	            		
	                	Label name = new Label("Name: ");
	                	Label description = new Label("Description: ");
	                	Label status = new Label("Column: ");
	                	Label dueDate = new Label("Due Date: ");
	                	
	                	Text taskName = new Text(boardModel.getProjectList()
        						.get(dropDownMenuIndex).getTasks().get(j).getTaskName());
	            		Text taskDescription = new Text(boardModel.getProjectList()
        						.get(dropDownMenuIndex).getTasks().get(j).getDescription());
	            		Text taskStatus = new Text(boardModel.getProjectList()
            					.get(dropDownMenuIndex).getTasks().get(j).getStatus());
	            		Text taskDueDate = new Text(boardModel.getProjectList()
            					.get(dropDownMenuIndex).getTasks().get(j).getDueDate());
	            		
	            		
	                	
	                
	                	 		
	                	GridPane taskGrid = new GridPane();
	                    RowConstraints rowConstraint1 = new RowConstraints(0);
	                    RowConstraints rowConstraint2 = new RowConstraints(25);
	                    RowConstraints rowConstraint3 = new RowConstraints(100);
	                    ColumnConstraints columnConstraint1 = new ColumnConstraints(0);
	                    ColumnConstraints columnConstraint2 = new ColumnConstraints(200);

	                	taskGrid.setGridLinesVisible(true);
	                	taskGrid.setPrefSize(100, 200);
	                	taskGrid.setMaxWidth(200);
	                	taskGrid.setMaxHeight(300);
	                	taskDescription.wrappingWidthProperty().bind(taskGrid.widthProperty());
	                
	                	taskGrid.add(name, 1, 1);
	                	taskGrid.add(description, 1, 2);
	                	
	                	taskGrid.add(dueDate, 1, 3);
	                	
	                	taskGrid.add(status, 1, 4);
	                	
	                	taskGrid.add(taskName, 2, 1);
	                	taskGrid.add(taskDescription, 2, 2);
	                	taskGrid.getRowConstraints().addAll(rowConstraint1, rowConstraint2, rowConstraint3);
	                	taskGrid.getColumnConstraints().addAll(columnConstraint1, new ColumnConstraints(100), new ColumnConstraints(150));

	                	taskGrid.add(taskDueDate, 2, 3);
	                	taskGrid.add(taskStatus, 2, 4);
	                	
	                	bottomGrid.addColumn(i , taskGrid);  	
	         
	                	bottomGridCount++;
	               
	                	
	                	taskGrid.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
	                        @Override
	                        public void handle(MouseEvent mouseEvent) {
	                        	
	                           Node source = (Node)mouseEvent.getSource();
	                           Integer colIndex = GridPane.getColumnIndex(source);
	                           Integer rowIndex = GridPane.getRowIndex(source);
	                           Node currentTaskName = (Text)taskGrid.getChildren().get(5);
	                           System.out.println(((Text) currentTaskName).getText());
	                           String taskName = ((Text) currentTaskName).getText();
	                           
	                           for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getTasks().size(); i++){
	                        	   if (boardModel.getProjectList().get(dropDownMenuIndex).getTasks().get(i).getTaskName() == taskName)
	                        	   {
	                        		   currentTaskPassByValue = i;
	                        	   }
	                           }
	                           
	                            
	                            Stage stageTheLabelBelongs = (Stage) createBtn.getScene().getWindow();
	        		        	tView = new TaskView(boardModel, getScene(), dropDownMenuIndex, currentTaskPassByValue);
	        		        	stageTheLabelBelongs.setScene(tView.getScene());
	        		        	stageTheLabelBelongs.setX(0);
	        		        	stageTheLabelBelongs.setY(0); 
	        		        	
	                            
	                        }
	                    });
	                }
	        	}
	        }
	    }
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////

	
	
	
	public void refreshPage(){
		while (bottomGrid.getChildren().size() != 0){
    		bottomGrid.getChildren().remove(0);
    		bottomGridCount--;
    	}

    	for (int i = 0; i < boardModel.getProjectList().get(dropDownMenuIndex).getColumns().size(); i++){
    		createColumn(boardModel.getProjectList().get(dropDownMenuIndex).getColumns().get(i));  
    	}

    	data.set(dropDownMenuIndex, boardModel.getProjectList().get(dropDownMenuIndex).getProjectName());
    	dropDownMenu.getItems().set(dropDownMenuIndex, data.get(dropDownMenuIndex));
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////

	
	public void addProjectList(String projectName){
		
			this.data.add(this.boardModel.getProjectList().get(projectSize).getProjectName());
			this.projectSize += 1;
			this.dropDownMenu.setItems(data);
		
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////

	
	public void createColumn(String columnName){

		SplitPane columnTasks = new SplitPane();
		columnTasks.setOrientation(Orientation.VERTICAL);
		columnTasks.setPrefSize(200, 50);
		Label columnLabel = new Label(columnName);
        Button newTaskBtn = new Button("+");
        
        
        columnTasks.getItems().addAll(columnLabel, newTaskBtn);
		this.bottomGrid.add(columnTasks, this.bottomGridCount, 1);
		this.bottomGridCount++;
		

		newTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {
		    	Stage stageTheLabelBelongs = (Stage) newTaskBtn.getScene().getWindow();	        	
	        	TaskView secondPane = new TaskView(boardModel, getScene(), dropDownMenuIndex);
	        	addSubject2(secondPane);
	        	stageTheLabelBelongs.setScene(secondPane.getScene());
	        	stageTheLabelBelongs.setX(0);
	        	stageTheLabelBelongs.setY(0);
		    }
		});


	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////

	public Scene getScene(){
		return this.scene;
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////
	//THESE ARE OBSERVERS
	@Override
	
	//Refreshes done if subject notifies observer with message
	public void update(String s) {
		if (s.equals("create")) {
			addProjectList("temp");
			refreshPage();
		} else if (s.equals("edit")) {
			refreshPage();
		} else if (s.equals("task")){
    		refreshTask();
    	} else if (s.equals("load")){
    		refreshPage();
    	}else {
			//Do Nothing
		}
	}
	
	//Adds ProjectView object to observe
	public void addSubject(ProjectView projectview) {
		this.projectView = projectview;
    	this.observerID = ++observerIDTracker;
    	System.out.println("New Observer " + this.observerID);
    	this.projectView.register(this);
	}
	
	//Adds TaskView object to observe
	public void addSubject2(TaskView taskview) {
		this.taskView = taskview;
		System.out.println("New Observer " + this.observerID);
		this.taskView.register(this);
	}
	
}
