/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static sun.security.krb5.KrbException.errorMessage;


/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_add_new_productController implements Initializable {
    @FXML
    private TextField barcode;
    @FXML
    private TextField quantity;
    @FXML
    private ComboBox<String> category = new ComboBox();


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select name from categories");
            while(rs.next()){
                category.getItems().add(rs.getString("name"));
            }
            rs.close();
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FXML_add_new_productController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }    

    @FXML
    private void onSelectCategory(ActionEvent event) throws SQLException {
        Database db = new Database();
        Connection c = db.getConnection();
        ResultSet rs = c.createStatement().executeQuery("select barcode from categories where name='"+ category.getValue() +"'");
        //System.out.println(rs.getString("barcode"));
        barcode.setText(rs.getString("barcode"));
        c.close();
        
    }

    @FXML
    private void onBarcodeEnter(ActionEvent event){
        Database db = new Database();
        Connection c;
        try {
            c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select name from categories where barcode='"+ barcode.getText().toString() +"'");
            category.setValue(rs.getString("name"));
        } catch (SQLException ex) {
            System.out.println("not found");
            JOptionPane.showMessageDialog(null, "Barcode not found!!!");
        }

    }

    @FXML
    private void onAddnewProductButtonClick(ActionEvent event) {
        String barcode = this.barcode.getText().toString();
        int quantity = Integer.parseInt(this.quantity.getText().toString());
        
        Database db;
        try {
            db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select quantity from categories where barcode='"+ barcode +"'");
            quantity += rs.getInt("quantity");
            c.close();
            db = new Database();
            db.executeUpdate("update categories set quantity="+ quantity +" where barcode='"+ barcode +"'");
            JOptionPane.showMessageDialog(null, "Add product success");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Something error");
        }
    }
    
}
