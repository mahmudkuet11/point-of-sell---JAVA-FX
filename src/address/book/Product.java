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
public class Product {
    // note all variable name is capitalized. beause of camel caps getter method name
    private String Barcode, Name;
    private float Pprice,Sprice,Qty;
    
    public Product(String barcode, String name, float qty, float p_price, float s_price){
        this.Barcode = barcode;
        this.Name = name;
        this.Qty = qty;
        this.Pprice = p_price;
        this.Sprice = s_price;
        
    }
    public String getBarcode(){
        return this.Barcode;
    }
    public String getName(){
        return this.Name;
    }
    public float getQty(){
        return this.Qty;
    }
    public float getPprice(){
        return this.Pprice;
    }
    public float getSprice(){
        return this.Sprice;
    }
    public void setName(String name){
        this.Name = name;
    }
    public void setBarcode(String barcode){
        this.Barcode = barcode;
    }
    public void setQty(float qty){
        this.Qty = qty;
    }
}
