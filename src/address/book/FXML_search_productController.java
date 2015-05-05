/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import address.book.Product;
import java.awt.AWTException;
import java.awt.Robot;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_search_productController implements Initializable {
    @FXML
    private TableView<Product> products;

    @FXML
    private TableColumn<Product,String> barcode;
    @FXML
    private TableColumn<Product,String> name;
    @FXML
    private TableColumn<Product,Integer> qty;
    @FXML
    private TableColumn<Product,Float> p_price;
    @FXML
    private TableColumn<Product,Float> s_price;
    @FXML
    private TextField search;

    private ObservableList<Product> data;
    @FXML
    private TextField edit_barcode;
    @FXML
    private TextField edit_name;
    @FXML
    private TextField edit_qty;
    @FXML
    private TextField edit_p_price;
    @FXML
    private TextField edit_s_price;
    @FXML
    private Button update;
    
    private Product productObject;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();

        barcode.setCellValueFactory(new PropertyValueFactory<Product,String>("Barcode"));
        name.setCellValueFactory(new PropertyValueFactory<Product,String>("Name"));
        qty.setCellValueFactory(new PropertyValueFactory<Product,Integer>("Qty"));
        p_price.setCellValueFactory(new PropertyValueFactory<Product,Float>("Pprice"));
        s_price.setCellValueFactory(new PropertyValueFactory<Product,Float>("Sprice"));

        products.setItems(data);
        
        products.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {
                
                productObject = products.getFocusModel().getFocusedItem();
                
                String barcode = products.getFocusModel().getFocusedItem().getBarcode();
                String name = products.getFocusModel().getFocusedItem().getName();
                int qty = products.getFocusModel().getFocusedItem().getQty();
                float p_price = products.getFocusModel().getFocusedItem().getPprice();
                float s_price = products.getFocusModel().getFocusedItem().getSprice();
                
                edit_barcode.setText(barcode);
                edit_name.setText(name);
                edit_qty.setText(String.valueOf(qty));
                edit_p_price.setText(String.valueOf(p_price));
                edit_s_price.setText(String.valueOf(s_price));
                
            }
        });
        
        Connection c = null;
        ResultSet rs = null;
        try {
            Database db = new Database();
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select * from categories");
            while(rs.next()){
                String barcode,name;
                int qty;
                float pprice,sprice;
                barcode = rs.getString("barcode");
                name = rs.getString("name");
                qty = rs.getInt("quantity");
                pprice = rs.getFloat("purchase_price");
                sprice = rs.getFloat("sell_price");
                
                Product p = new Product(barcode, name, qty, pprice, sprice);
                data.add(p);
            }
            rs.close();
            c.close();
            
        } catch (SQLException ex) {
            
        }
    }    

    @FXML
    private void onSearch(KeyEvent event) throws SQLException {
        String keyword = search.getText().toString();
        Connection c = null;
        try {
            Database db = new Database();
            c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from categories where name like '%"+ keyword +"%'");
            data.clear();
            while(rs.next()){
                String barcode,name;
                int qty;
                float pprice,sprice;
                barcode = rs.getString("barcode");
                name = rs.getString("name");
                qty = rs.getInt("quantity");
                pprice = rs.getFloat("purchase_price");
                sprice = rs.getFloat("sell_price");
                
                Product p = new Product(barcode, name, qty, pprice, sprice);
                data.add(p);
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXML_search_productController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            c.close();
        }
    }

    @FXML
    private void onUpdate(ActionEvent event) throws IOException {
        try {
            String barcode = edit_barcode.getText();
            String name = edit_name.getText();
            int qty = Integer.parseInt(edit_qty.getText());
            float p_price = Float.parseFloat(edit_p_price.getText());
            float s_price = Float.parseFloat(edit_s_price.getText());
            
            Database db = new Database();
            db.executeUpdate("update categories set name='"+ name +"', purchase_price="+ p_price +", sell_price="+ s_price +" where barcode='"+ barcode +"'");
            
            JOptionPane.showMessageDialog(null, "Updated Successfully");
            
            Parent root = FXMLLoader.load(getClass().getResource("FXML_search_product.fxml"));
            root.getStylesheets().add("file:///C:/Users/mohar/Desktop/style.css");
            Scene scene = new Scene(root);
            Stage stage = (Stage)search.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error to update item");
            System.err.println(ex.getMessage());
        }
        
    }
    
}
