package application;

import java.io.File; 
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class SaveViewModel {
	
	private TaskBoardModel boardModel;
	private File file;
	
	public boolean save(TaskBoardModel boardModel,File file) {
		this.file=file;
		this.boardModel = boardModel;
		return serializeTaskBoardModel(this.boardModel);
	}
	
	public boolean serializeTaskBoardModel(TaskBoardModel boardModel) {
		try (ObjectOutputStream oos = 
				new ObjectOutputStream(new FileOutputStream(this.file))) {

			oos.writeObject(boardModel);
			System.out.println("Done");
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
}
