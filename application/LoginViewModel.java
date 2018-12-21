package application;

import java.awt.event.ActionListener;
import javafx.event.ActionEvent;
import java.awt.event.*;


public class LoginViewModel {

	private String username, password;
	
	LoginViewModel (String a, String b){
		this.username = a;
		this.password = b;
	}
	
	boolean check() {
		if (this.username.equals("admin") && this.password.equals("admin")) {
			return true;
		}
		else {
			return false;
		}
	}
}

