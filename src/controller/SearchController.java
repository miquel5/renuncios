package controller;

import model.TypeModel;

public class SearchController
{
    // Afegir un producte
    public void addType(int id, String type, String text, String datai, String dataf, String size, boolean color, double price)
    {
        TypeModel tm = new TypeModel(id, text, datai, dataf, size, color, price);
    }
}
