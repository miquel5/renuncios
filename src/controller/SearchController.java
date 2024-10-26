package controller;

import service.DatabaseQueries;
import utils.DatabaseConnection;

import java.sql.Connection;

public class SearchController
{
    private DatabaseQueries queries;
    private Connection con;

    public SearchController()
    {
        this.con = DatabaseConnection.connectionOracle();
        this.queries = new DatabaseQueries();
    }

    /*public void getProducts(String type)
    {
        this.queries.products(type);
    }*/
}
