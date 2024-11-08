package model;

import java.util.ArrayList;

public class CartModel
{
    private static CartModel instance; // Crear una classe única per tot el programa
    private ArrayList<Integer> list;
    private double total;
    private int discount;

    public CartModel()
    {
        this.list = new ArrayList<Integer>();
        this.discount = 0;
    }

    // Getters
    public ArrayList<Integer> getList() {return this.list;}
    public double getTotal() {return this.total;}
    public int getDiscount() {return this.discount;}

    public static CartModel getInstance()
    {
        if (instance == null)
        {
            instance = new CartModel();
        }

        return instance;
    }

    // Setters
    public void setList(ArrayList<Integer> plist) {this.list = plist;}
    public void setTotal(double ptotal) {this.total = ptotal;}
    public void setDiscount(int pdiscount) {this.discount = pdiscount;}

    // Others
    public void addToList(int value) {this.list.add(value);} // Afegir
    public void addTotal(double ptotal) {this.total += ptotal;} // Afegir
    public void subtractTotal(double ptotal) {this.total -= ptotal;} // Eliminar
    public void subtractList() {this.list.clear();} // Eliminar
}
