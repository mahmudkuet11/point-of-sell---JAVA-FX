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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author mohar
 */
public class FXML_net_profit_report_by_dateController implements Initializable {
    @FXML
    private TextField from;
    @FXML
    private TextField to;
    @FXML
    private Button search;
    @FXML
    private TableColumn<Voucher, String> s_date;
    @FXML
    private TableColumn<Voucher, String> sell;
    @FXML
    private TableColumn<Voucher, String> discount;
    @FXML
    private TableColumn<Voucher, String> paid;
    @FXML
    private TableColumn<Voucher, String> due;
    @FXML
    private TableColumn<Voucher, String> purchase;
    @FXML
    private TableColumn<Voucher, String> revenue;
    @FXML
    private TableColumn<Expense, String> e_date;
    @FXML
    private TableColumn<Expense, String> amount;
    @FXML
    private Label total_revenue;
    @FXML
    private Label total_expense;
    @FXML
    private Label total_profit;

    private ObservableList<Voucher> data;
    private ObservableList<Expense> ex_data;    //expense data
    @FXML
    private TableView<Voucher> sell_table;
    @FXML
    private TableView<Expense> expense_table;
    
    private float final_revenue, final_expense,final_profit;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        data = FXCollections.observableArrayList();
        ex_data = FXCollections.observableArrayList();
        s_date.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Date"));
        sell.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Total"));
        discount.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Discount"));
        paid.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Paid"));
        due.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Due"));
        purchase.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Purchase"));
        revenue.setCellValueFactory(new PropertyValueFactory<Voucher,String>("Revenue"));
        
        sell_table.setItems(data);
        
        e_date.setCellValueFactory(new PropertyValueFactory<Expense,String>("Date"));
        amount.setCellValueFactory(new PropertyValueFactory<Expense,String>("Amount"));
        
        expense_table.setItems(ex_data);
        
    }    

    @FXML
    private void onSearchClick(ActionEvent event) {
        
        final_revenue = 0.0f;
        final_expense = 0.0f;
        final_profit = 0.0f;
        
        data.clear();
        // from date is f
        String f,t;
        f = this.from.getText().toString();
        t = this.to.getText().toString();
        
        try {
            Date d = new SimpleDateFormat("dd-MM-yyyy").parse(f);
            String from = String.valueOf(d.getTime());
            d = new SimpleDateFormat("dd-MM-yyyy").parse(t);
            String to = String.valueOf(d.getTime());
            
            Database db = new Database();
            Connection c = db.getConnection();
            ResultSet rs = c.createStatement().executeQuery("select * from vouchers where date between '"+ from +"' and '"+ to +"'");
            
            while(rs.next()){
                String date = rs.getString("date");
                float total = rs.getFloat("total_price");
                float discount = rs.getFloat("discount");
                float paid = rs.getFloat("paid");
                float due = total - discount - paid;
                float purchase_cost = rs.getFloat("total_purchase_price");
                float revenue = total - discount - purchase_cost;
                final_revenue += revenue;
                Voucher v = new Voucher(date,total,discount,paid,due,purchase_cost,revenue);
                data.add(v);
            }
            rs.close();
            c.close();
            
            ex_data.clear();
            
            db = new Database();
            c = db.getConnection();
            rs = c.createStatement().executeQuery("select * from expenses where date between '"+ from +"' and '"+ to +"'");
            
            while(rs.next()){
                String date = rs.getString("date");
                float amount = rs.getFloat("amount");
                final_expense += amount;
                Expense e = new Expense(date,amount);
                ex_data.add(e);
            }
            rs.close();
            c.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(FXML_net_profit_report_by_dateController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(FXML_net_profit_report_by_dateController.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            total_revenue.setText(String.valueOf(final_revenue));
            total_expense.setText(String.valueOf(final_expense));
            final_profit = final_revenue - final_expense;
            total_profit.setText(String.valueOf(final_profit));
        }
        
    }
    
}
