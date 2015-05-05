/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

import address.book.Product;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_new_voucherController implements Initializable {
    @FXML
    private TableView<Product> table;
    @FXML
    private TableColumn<Product, String> barcode;
    @FXML
    private TableColumn<Product, String> name;
    @FXML
    private TableColumn<Product, String> quantity;
    @FXML
    private TableColumn<Product, String> price;
    @FXML
    private TextField scannedBarcode;

    private ObservableList<Product> data;
    private Product productObject;
    @FXML
    private TextField edit_barcode;
    @FXML
    private TextField edit_name;
    @FXML
    private TextField edit_qty;
    @FXML
    private TextField edit_s_price;
    @FXML
    private Button update;
    @FXML
    private TextField total;
    @FXML
    private TextField discount;
    @FXML
    private TextField paid;
    @FXML
    private TextField due;
    @FXML
    private TextField customer_name;
    @FXML
    private TextField phone;
    @FXML
    private Label error;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        data = FXCollections.observableArrayList();
        barcode.setCellValueFactory(new PropertyValueFactory<Product,String>("Barcode"));
        name.setCellValueFactory(new PropertyValueFactory<Product,String>("Name"));
        quantity.setCellValueFactory(new PropertyValueFactory<Product,String>("Qty"));
        price.setCellValueFactory(new PropertyValueFactory<Product,String>("Sprice"));
        table.setItems(data);
        
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent t) {

                productObject = table.getFocusModel().getFocusedItem();

                String barcode = table.getFocusModel().getFocusedItem().getBarcode();
                String name = table.getFocusModel().getFocusedItem().getName();
                float s_price = table.getFocusModel().getFocusedItem().getSprice();

                edit_barcode.setText(barcode);
                edit_name.setText(name);
                edit_qty.setText(String.valueOf(productObject.getQty()));
                edit_s_price.setText(String.valueOf(s_price));

            }
        });
        
    }    

    @FXML
    private void onBarcodeScanned(ActionEvent event) {
        try {
            String barcode = scannedBarcode.getText().toString();
            Database db = new Database();
            Connection c = db.getConnection();
            
            ResultSet rs = c.createStatement().executeQuery("select * from categories where barcode='"+ barcode +"'");
            
            while(rs.next()){
                Product p = new Product(barcode,rs.getString("name"),1,rs.getFloat("purchase_price"),rs.getFloat("sell_price"));
                if(data.size() == 0){
                    data.add(p);
                }else{
                    boolean found = false;
                    for(int i = 0; i < data.size(); i++){
                        if(data.get(i).getBarcode().equals(barcode)){
                            data.get(i).setQty(data.get(i).getQty()+1);
                            found = true;
                            break;
                        }
                    }
                    if(!found){
                        data.add(p);
                    }
                }
                
                table.getColumns().clear();
                table.getColumns().addAll(this.barcode,this.name,this.quantity,this.price);
                
            }
            c.close();
        } catch (SQLException ex) {
            Logger.getLogger(FXML_new_voucherController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            scannedBarcode.setText("");
            updateTotalPrice();
        }
        
    }

    @FXML
    private void onUpdateClicked(ActionEvent event) {
        String barcode = edit_barcode.getText();
        String name = edit_name.getText();
        int qty = Integer.parseInt(edit_qty.getText());
        for(int i = 0; i < data.size(); i++){
            if(data.get(i).getBarcode().equals(barcode)){
                data.get(i).setQty(qty);
            }
        }
        table.getColumns().clear();
        table.getColumns().addAll(this.barcode,this.name,this.quantity,this.price);
        updateTotalPrice();
    }

    private void updateTotalPrice() {
        
        float total = 0f;
        
        for(int i = 0; i < data.size(); i++){
            total += data.get(i).getQty() * data.get(i).getSprice();
        }
        this.total.setText(String.valueOf(total));
        
    }

    @FXML
    private void calculateDue(KeyEvent event) {
        float total,discount,paid,due;
        
        total = Float.parseFloat(this.total.getText());
        discount = Float.parseFloat(this.discount.getText());
        paid = Float.parseFloat(this.paid.getText());
        due = Float.parseFloat(this.due.getText());
        
        due = total - discount - paid;
        this.due.setText(String.valueOf(due));
        
        if(due < 0){
            error.setVisible(true);
        }else{
            error.setVisible(false);
        }
        
    }

    @FXML
    private void confirmVoucherButtonClick(ActionEvent event) {
        try {
            String name = customer_name.getText();
            String phone = this.phone.getText();
            float total_price = Float.parseFloat(this.total.getText());
            float discount = Float.parseFloat(this.discount.getText());
            float paid = Float.parseFloat(this.paid.getText());
            float total_purchase_price = 0.0f;
            for(int i = 0; i < data.size(); i++){
                total_purchase_price += data.get(i).getPprice() * data.get(i).getQty();
            }
            String date = String.valueOf(new Date().getTime());
            Database db;
            db = new Database();
            db.executeUpdate("insert into vouchers (name,phone,total_price,discount,paid,total_purchase_price,date) values ('"+ name +"','"+ phone +"',"+ total_price +","+ discount +","+ paid +","+ total_purchase_price +",'"+ date +"')");
            
            db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select id from vouchers where date='"+ date +"'");
            int voucher_id = Integer.parseInt(rs.getString("id"));
            c.close();
            
            for(int i = 0; i < data.size(); i++){
                db = new Database();
                String cat = data.get(i).getName();
                String barcode = data.get(i).getBarcode();
                float Sprice = data.get(i).getSprice();
                int quantity = data.get(i).getQty();
                db.executeUpdate("insert into orders (voucher_id,barcode,category,price,quantity) values ("+ voucher_id +",'"+ barcode +"','"+ cat +"',"+ Sprice +","+ quantity +")");    
            }
            JOptionPane.showMessageDialog(null, "Success");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Something error");
        }
        
    }
    

    
}
