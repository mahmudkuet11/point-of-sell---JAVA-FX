/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package address.book;

/**
 *
 * @author mohar
 */
public class Expense {
    private String Date;
    private float Amount;
    
    public Expense(String date, float amount){
        this.Date = date;
        this.Amount = amount;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }
}
