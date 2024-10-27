package model;

import java.util.ArrayList;

public class CartModel
{
    private static CartModel instance; // Crear una classe única per tot el programa
    private ArrayList<Integer> list;
    private double total;

    public CartModel()
    {
        this.list = new ArrayList<Integer>();
    }

    // Getters
    public ArrayList<Integer> getList() {return this.list;}
    public double getTotal() {return this.total;}

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

    // Others
    public void addToList(int value) {this.list.add(value);}
    public void addTotal(double ptotal) {this.total += ptotal;}
    public void subtractTotal(double ptotal) {this.total -= ptotal;}
    public void subtractList() {this.list.clear();}
}
