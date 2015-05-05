/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author mohar
 */
public class loginController implements Initializable {
    @FXML
    private TextField username_input;
    @FXML
    private TextField password_input;
    @FXML
    private Button login_btn;
    @FXML
    private Label error_label;
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String sql = "create table if not exists users (id int primary key not null,username varchar(20) not null,password varchar(60) not null)";
        error_label.setVisible(false);
    }    

    @FXML
    private void loginButtonClick(MouseEvent event) throws IOException, SQLException {
        String username = username_input.getText().toString();
        String password = password_input.getText().toString();
        Database db = new Database();
        /*ResultSet rs = db.executeQuery("SELECT * FROM users where username='"+ username +"'");
        try {
            while(rs.next()){
                if(rs.getString("password").equals(password)){
                    Parent root = FXMLLoader.load(getClass().getResource("FXML_home.fxml"));
                    root.getStylesheets().add("file:///C:/Users/mohar/Desktop/style.css");
                    Scene scene = new Scene(root);
                    Stage stage = (Stage)error_label.getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }else{
                    error_label.setVisible(true);
                }
            }
            error_label.setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(loginController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }
}
