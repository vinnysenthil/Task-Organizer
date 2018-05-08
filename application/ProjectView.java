package application;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProjectView {
	
	private TaskBoardModel boardModel;
	private Scene scene;
	private Label projectLabel = new Label("Name");
	private TextField projectTextField = new TextField();
	private Label columnLabel = new Label("Column");
	private Button columnBtn = new Button("+");
	private Button createBtn = new Button("Create");
	private Button cancelBtn = new Button("Cancel");
	private GridPane topGrid = new GridPane();
	private GridPane midGrid = new GridPane();
	private GridPane bottomGrid = new GridPane();
	private int columnCount = 0;
	private ProjectModel projectModel = new ProjectModel("temp");
	
	private List<TextField> textFieldList = new ArrayList<TextField>();
	private int textFieldCount = 0;
	
	private Scene prevScene;
	
	
	 
	public ProjectView(TaskBoardModel boardModel, Scene prevScene){
		
		this.prevScene = prevScene;
		this.boardModel = boardModel;
		
		VBox root = new VBox();
		this.topGrid.setAlignment(Pos.TOP_LEFT);
		this.topGrid.setHgap(10);
		this.topGrid.setVgap(20);
		this.topGrid.setPadding(new Insets(25, 25, 25, 25));
		this.topGrid.add(this.projectLabel, 1, 1);
		this.topGrid.add(this.projectTextField, 2, 1);
		this.topGrid.add(this.columnLabel, 1, 2);
		this.topGrid.add(this.columnBtn, 2, 2);
		
		this.midGrid.setAlignment(Pos.TOP_LEFT);
		this.midGrid.setHgap(10);
		this.midGrid.setVgap(20);

		this.bottomGrid.setAlignment(Pos.BOTTOM_RIGHT);
		this.bottomGrid.add(this.createBtn, 1, 1);
		this.bottomGrid.add(this.cancelBtn, 2, 1);

		
		this.scene = new Scene(root, 500, 500);
		root.getChildren().addAll(this.topGrid, this.midGrid, this.bottomGrid);
		
		columnBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	createColumnField();
		    }
		});
		
		createBtn.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		    	createProject();
		    }
		});

	}
	
	public void createColumnField(){
		
		TextField textField = new TextField();
		this.textFieldList.add(textField);
	    Button deleteBtn = new Button("-");

	    this.midGrid.add(this.textFieldList.get(this.textFieldCount), 1, this.columnCount);
		this.midGrid.add(deleteBtn, 2, this.columnCount);
		this.columnCount++;
		this.textFieldCount++;
		
		deleteBtn.setOnAction(new EventHandler<ActionEvent>() {
		    @Override
		    public void handle(ActionEvent e) {

		    	midGrid.getChildren().remove(deleteBtn);
		    	midGrid.getChildren().remove(textField);
		    	textFieldList.remove(textField);
		    	columnCount--;
		    	textFieldCount--;
		    }
		});


	}
	
	/*public GridPane getMidGrid(){
		return this.midGrid;
	}
	
	public List<TextField> getTextFieldList(){
		return this.textFieldList;
	}*/
	
	public void createProject(){
		
		for (int i = 0; i < this.columnCount; i++){
			this.projectModel.getColumns().add(textFieldList.get(i).getText());
		}
		
		this.projectModel.setProjectName(projectTextField.getText());
		this.boardModel.addProjectToList(this.projectModel);
    	Stage stageTheLabelBelongs = (Stage) this.createBtn.getScene().getWindow();
    	stageTheLabelBelongs.setScene(this.prevScene);
    	
		
	}
	
	public Scene getScene(){
		return this.scene;
	}
}
