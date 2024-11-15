package model;

import java.util.ArrayList;

public class CartModel
{
    private static CartModel instance; // Crear una classe única per tot el programa
    private ArrayList<Integer> list; // Llista de productes
    private double total;

    public CartModel()
    {
        this.list = new ArrayList<Integer>();
    }

    // Getters
    public ArrayList<Integer> getList() {return this.list;}
    public double getTotal() {return this.total;}
    public static CartModel getInstance() { if (instance == null) { instance = new CartModel(); } return instance; } // Crear una intancia si és null (intancia = classe unica)

    // Setters
    public void setList(ArrayList<Integer> plist) {this.list = plist;}
    public void setTotal(double ptotal) {this.total = ptotal;}

    // Others
    public void addToList(int value) {this.list.add(value);} // Afegir
    public void sumTotal(double ptotal) {this.total += ptotal;} // Afegir
    public void subtractTotal(double ptotal) {this.total -= ptotal;} // Eliminar
    public void subtractList() {this.list.clear();} // Eliminar
}
