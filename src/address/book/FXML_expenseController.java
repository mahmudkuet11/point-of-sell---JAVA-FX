/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_expenseController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextArea note;
    @FXML
    private TextField amount;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onSubmitButtonClick(ActionEvent event) {
        String name = this.name.getText().toString();
        String note = this.note.getText().toString();
        float amount = Float.parseFloat(this.amount.getText().toString());
        
        String date = String.valueOf(new Date().getTime());
        Database db = new Database();
        try {
            db.executeUpdate("insert into expenses (name,note,amount,date) values ('"+ name +"','"+ note +"',"+ amount +",'"+ date +"')");
        } catch (SQLException ex) {
            
        }
        
    }
    
}
