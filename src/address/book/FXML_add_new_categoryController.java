/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_add_new_categoryController implements Initializable {
    @FXML
    private TextField name;
    @FXML
    private TextField quantity;
    @FXML
    private TextField warning_quantity;
    @FXML
    private TextField purchase_price;
    @FXML
    private TextField sell_price;
    @FXML
    private TextField barcode;
    @FXML
    private Button add_category_btn;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void onAddCategoryButtonClick(MouseEvent event) {
        try {
            String name,barcode;
            int quantity,warning_quantity;
            float purchase_price,sell_price;
            name = this.name.getText().toString();
            barcode = this.barcode.getText().toString();
            warning_quantity = Integer.parseInt(this.warning_quantity.getText());
            purchase_price = Float.parseFloat(this.purchase_price.getText());
            sell_price = Float.parseFloat(this.sell_price.getText());
            
            Database db = new Database();
            String sql = "insert into categories (name,barcode,warning_quantity,purchase_price,sell_price) values ('"+ name +"','"+ barcode +"',"+ warning_quantity +","+ purchase_price +","+ sell_price +")";
            
            db.executeUpdate(sql);
        //db.closeConnection();
        } catch (SQLException ex) {
            Logger.getLogger(FXML_add_new_categoryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
