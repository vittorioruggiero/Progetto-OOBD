package controller;

import java.sql.Connection;
import java.sql.SQLException;

import dbConfig.DBConnection;
import gui.HomeFrame;

public class Controller {
	private HomeFrame homeFrame;
	DBConnection dbconn;
	Connection connection;
	
	public static void main(String[] args) {
		Controller controller = new Controller();
	}
	
	public Controller() {
		try {
            this.dbconn = DBConnection.getInstance();
            this.connection = this.dbconn.getConnection();
		}
		catch (SQLException exception) {
            System.out.println("SQLException: " + exception.getMessage());
        }
		this.homeFrame = new HomeFrame();
		this.homeFrame.setVisible(true);
	}
}
