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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_due_reportController implements Initializable {
    @FXML
    private TableView<Voucher> table;
    @FXML
    private TableColumn<Voucher, String> date;
    @FXML
    private TableColumn<Voucher, String> name;
    @FXML
    private TableColumn<Voucher, String> phone;
    @FXML
    private TableColumn<Voucher, Float> purchase;
    @FXML
    private TableColumn<Voucher, Float> discount;
    @FXML
    private TableColumn<Voucher, Float> paid;
    @FXML
    private TableColumn<Voucher, Float> due;
    @FXML
    private Label total_due;
    
    private ObservableList<Voucher> data;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            data = FXCollections.observableArrayList();
            
            date.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Date"));
            name.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Name"));
            phone.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Phone"));
            purchase.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Total"));
            discount.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Discount"));
            paid.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Discount"));
            due.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Discount"));
            
            table.setItems(data);
            
            Database db = new Database();
            Connection c = db.getConnection();
            
            ResultSet rs = c.createStatement().executeQuery("select * from vouchers");
            
            float total_due = 0f;
            while(rs.next()){
                String date = rs.getString("date");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                float purchase = rs.getFloat("total_price");
                float discount = rs.getFloat("discount");
                float paid = rs.getFloat("paid");
                float due = purchase - discount - paid;
                Voucher v = new Voucher(date,name,phone,purchase,discount,paid,due);
                data.add(v);
                
                total_due += due;
                this.total_due.setText(String.valueOf(total_due));
            }
            
            rs.close();
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FXML_due_reportController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }    
    
}
