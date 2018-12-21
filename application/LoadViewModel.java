package application;

import java.io.File; 
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class LoadViewModel {
	
	private File file;
	
	public TaskBoardModel load(File f) {
		this.file = f;
		return deserializeTaskBoardModel(this.file);
	}
	
	public TaskBoardModel deserializeTaskBoardModel(File f) {
		TaskBoardModel boardModel = null;

		try (ObjectInputStream ois 
			= new ObjectInputStream(new FileInputStream(file))) {

			boardModel = (TaskBoardModel) ois.readObject();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return boardModel;
	}
}
