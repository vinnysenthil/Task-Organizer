package application;

import java.io.File;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SaveView {

	private SaveViewModel saveViewModel = new SaveViewModel();
	private FileChooser fileChooser = new FileChooser();
	private File file;
	
	public SaveView(Stage stage){
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
		this.fileChooser.getExtensionFilters().add(extFilter);
		this.file = fileChooser.showSaveDialog(stage);
	}
	
	public void saveToFile(TaskBoardModel boardModel) {
		if (this.file==null) {
			System.out.println("SAVE OPERATION CANCELLED!");
		} else {
			if(saveViewModel.save(boardModel, this.file)) {
				System.out.println("SUCCESSFULLY SAVED!");
			} else {
				System.out.println("SAVE FAILED!!!");
			}
		}
	}
}
