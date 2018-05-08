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

/*

	public class LoginViewController{
		private LoginView controllerView;
		
		LoginViewController(LoginView view){
			this.controllerView = view;
			view.addBtnListener(new addBtnListener());
		}
		
		
		 class addBtnListener implements ActionListener {
		        public void actionPerformed(ActionEvent e) {
		            String userInput = "";
		            String pwInput = "";
		
		            try {
		                userInput = controllerView.getUserInput();
		                pwInput = controllerView.getPWInput();
		                m_model.multiplyBy(userInput);
		                m_view.setTotal(m_model.getValue());
		
		            } catch (NumberFormatException nfex) {
		                m_view.showError("Bad input: '" + userInput + "'");
		            }
		        }
		
				@Override
				public void actionPerformed(java.awt.event.ActionEvent e) {
					// TODO Auto-generated method stub
		
				}
		    }
	}    
*/
