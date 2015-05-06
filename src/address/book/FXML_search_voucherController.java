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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_search_voucherController implements Initializable {
    @FXML
    private TextField search_name;
    @FXML
    private TableView<Voucher> table;
    @FXML
    private TextField name;
    @FXML
    private TextField phone;
    @FXML
    private TextField total;
    @FXML
    private TextField discount;
    @FXML
    private TextField paid;
    @FXML
    private TextField due;
    @FXML
    private Button update;
    @FXML
    private TableColumn<Voucher, String> date_c;
    @FXML
    private TableColumn<Voucher, String> name_c;
    @FXML
    private TableColumn<Voucher, String> phone_c;
    @FXML
    private TableColumn<Voucher, Float> total_c;
    @FXML
    private TableColumn<Voucher, Float> discount_c;
    @FXML
    private TableColumn<Voucher, Float> paid_c;
    @FXML
    private TableColumn<Voucher, Float> due_c;
    
    private ObservableList<Voucher> data;
    private Voucher focusedRow;
    String l_date;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();

        date_c.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Date"));
        name_c.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Name"));
        phone_c.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Phone"));
        
        total_c.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Total"));
        discount_c.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Discount"));
        paid_c.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Paid"));
        due_c.setCellValueFactory(new PropertyValueFactory<Voucher,Float>("Due"));
        
        table.setItems(data);
        
        try{
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from vouchers");
            while(rs.next()){
                float due = rs.getFloat("total_price") - rs.getFloat("discount") - rs.getFloat("paid");
                Voucher v = new Voucher(rs.getString("date"),rs.getString("name"), rs.getString("phone"),rs.getFloat("total_price"),rs.getFloat("discount"),rs.getFloat("paid"),due);
                data.add(v);
            }
            rs.close();
            c.close();
        }catch(Exception e){
            
        }
        
        
        table.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent t) {
                
               focusedRow = table.getFocusModel().getFocusedItem();
               
               String l_name,l_phone;
               float l_discount,l_total,l_paid,l_due;
               
               l_name = focusedRow.getName();
               l_date = focusedRow.getDate();
               l_phone = focusedRow.getPhone();
               l_total = focusedRow.getTotal();
               l_discount = focusedRow.getDiscount();
               l_paid = focusedRow.getPaid();
               l_due = focusedRow.getDue();
               
               name.setText(l_name);
               phone.setText(l_phone);
               total.setText(String.valueOf(l_total));
               discount.setText(String.valueOf(l_discount));
               paid.setText(String.valueOf(l_paid));
               due.setText(String.valueOf(l_due));
               
               
               
                
            }
            
        });
        
        
    }    

    @FXML
    private void onUpdateVoucherButtonClick(ActionEvent event) {
        
        try {
            String name = this.name.getText();
            String phone = this.phone.getText();
            float total = Float.parseFloat(this.total.getText());
            float discount = Float.parseFloat(this.discount.getText());
            float paid = Float.parseFloat(this.paid.getText());
            float due = total - discount - paid;
            
            
            
            Database db = new Database();
            db.executeUpdate("update vouchers set name='"+ name +"',phone='"+ phone +"',discount="+ discount +",paid="+ paid +" where date='"+ l_date +"'");
            
            for(int i = 0; i < data.size(); i++){
                if(data.get(i).getDate().equals(l_date)){
                    data.get(i).setName(name);
                    data.get(i).setPhone(phone);
                    data.get(i).setTotal(total);
                    data.get(i).setDiscount(discount);
                    data.get(i).setPaid(paid);
                    data.get(i).setDue(due);
                    break;
                    
                }
            }
            
            table.getColumns().clear();
            table.getColumns().addAll(this.date_c, this.name_c, this.phone_c, this.total_c, this.discount_c, this.paid_c, this.due_c);
            
        } catch (SQLException ex) {
            
        }
        
    }

    @FXML
    private void calculateDue(KeyEvent event) {
        float total,discount,paid,due;
        total = Float.parseFloat(this.total.getText());
        discount = Float.parseFloat(this.discount.getText());
        paid = Float.parseFloat(this.paid.getText());
        due = Float.parseFloat(this.due.getText());
        
        this.due.setText(String.valueOf(total - discount - paid));
        
    }

    @FXML
    private void searchVoucherByName(KeyEvent event) {
        data.clear();
        try{
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from vouchers where name like '%"+ search_name.getText() +"%'");
            while(rs.next()){
                float due = rs.getFloat("total_price") - rs.getFloat("discount") - rs.getFloat("paid");
                Voucher v = new Voucher(rs.getString("date"),rs.getString("name"), rs.getString("phone"),rs.getFloat("total_price"),rs.getFloat("discount"),rs.getFloat("paid"),due);
                data.add(v);
            }
            rs.close();
            c.close();
        }catch(Exception e){
            
        }finally{
            table.getColumns().clear();
            table.getColumns().addAll(this.date_c, this.name_c, this.phone_c, this.total_c, this.discount_c, this.paid_c, this.due_c);
        }
        
        
        
    }

    
}
