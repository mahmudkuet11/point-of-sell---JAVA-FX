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
public class Voucher {
    
    private String Date,Name,Phone;
    private float Total,Discount,Paid,Due;
    
    public Voucher(String date, String name, String phone, float total, float discount, float paid, float due){
        this.Date = date;
        this.Name = name;
        this.Phone = phone;
        this.Total = total;
        this.Discount = discount;
        this.Paid = paid;
        this.Due = due;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public float getTotal() {
        return Total;
    }

    public void setTotal(float Total) {
        this.Total = Total;
    }

    public float getDiscount() {
        return Discount;
    }

    public void setDiscount(float Discount) {
        this.Discount = Discount;
    }

    public float getPaid() {
        return Paid;
    }

    public void setPaid(float Paid) {
        this.Paid = Paid;
    }

    public float getDue() {
        return Due;
    }

    public void setDue(float Due) {
        this.Due = Due;
    }
    
}
