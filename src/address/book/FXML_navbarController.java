/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_navbarController implements Initializable {
    @FXML
    private MenuItem add_new_product;
    @FXML
    private MenuItem add_new_cat;
    @FXML
    private MenuBar menubar;
    @FXML
    private MenuItem search_product;
    @FXML
    private MenuItem new_voucher;
    @FXML
    private MenuItem search_voucher;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onMenuitemClick(ActionEvent event) throws IOException {
        if(event.getSource().equals(add_new_product)){
            Parent root = FXMLLoader.load(getClass().getResource("FXML_add_new_product.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)menubar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        if(event.getSource().equals(search_product)){
            Parent root = FXMLLoader.load(getClass().getResource("FXML_search_product.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)menubar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        if(event.getSource().equals(add_new_cat)){
            Parent root = FXMLLoader.load(getClass().getResource("FXML_add_new_category.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)menubar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        if(event.getSource().equals(new_voucher)){
            Parent root = FXMLLoader.load(getClass().getResource("FXML_new_voucher.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)menubar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
        if(event.getSource().equals(search_voucher)){
            Parent root = FXMLLoader.load(getClass().getResource("FXML_search_voucher.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage)menubar.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    
}
