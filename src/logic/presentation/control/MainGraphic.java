package logic.presentation.control;


import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;
import logic.application.SessionFacade;
import logic.application.Users;
import logic.presentation.GraphicHandler;
import logic.presentation.Scenes;
import logic.presentation.SharedGraphicElems;

public class MainGraphic extends SharedGraphicElems implements Initializable {
	
	@FXML
	private Button recBtn;
	
	@FXML
    private Hyperlink buyAdLink;


	@Override
	public void initialize(URL url, ResourceBundle resource) {
		/**/
	}
	
	@FXML
	private void searchEntrepreneur() {
		SessionFacade.getSession().setCurrUserType(Users.ENTREPRENEUR);
		
		Stage stage = (Stage)pane.getScene().getWindow();
		stage.setScene(GraphicHandler.switchScreen(Scenes.SEARCH_ENTR, null));
	}
	
	@FXML
	private void searchSeeker() {
		SessionFacade.getSession().setCurrUserType(Users.SEEKER);
	
		Stage stage = (Stage)pane.getScene().getWindow();
		stage.setScene(GraphicHandler.switchScreen(Scenes.SEARCH_SEEK, null));
	}
	
	@FXML
	public void loginMain() {
		if(recBtn.isArmed()) {
			SessionFacade.getSession().setCurrUserType(Users.RECRUITER);
		}
		
		Stage stage = (Stage)pane.getScene().getWindow();
		stage.setScene(GraphicHandler.switchScreen(Scenes.LOGIN, null));
	}

	@FXML
	public void openAdMarketplace() {
		/**/
    }
}
