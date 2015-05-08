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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_remaining_product_reportController implements Initializable {
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, Float> p_price;
    @FXML
    private TableColumn<Product, Float> s_price;
    @FXML
    private TableColumn<Product, Float> quantity;
    @FXML
    private TableView<Product> table;
    
    private ObservableList<Product> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            data = FXCollections.observableArrayList();
            
            name.setCellValueFactory(new PropertyValueFactory<Product,String>("Name"));
            p_price.setCellValueFactory(new PropertyValueFactory<Product,Float>("Pprice"));
            s_price.setCellValueFactory(new PropertyValueFactory<Product,Float>("Sprice"));
            quantity.setCellValueFactory(new PropertyValueFactory<Product,Float>("Qty"));
            
            table.setItems(data);
            
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from categories");
            
            while(rs.next()){
                String name = rs.getString("name");
                float qty = rs.getFloat("quantity");
                float warning_qty = rs.getFloat("warning_quantity");
                float p_price = rs.getFloat("purchase_price");
                float s_price = rs.getFloat("sell_price");
                
                Product p = new Product("",name,qty,p_price,s_price);
                data.add(p);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(FXML_remaining_product_reportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    
    
}
