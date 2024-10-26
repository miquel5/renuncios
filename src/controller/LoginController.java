package controller;

import model.UserModel;
import service.DatabaseQueries;
import utils.DatabaseConnection;
import java.sql.Connection;

public class LoginController
{
    private DatabaseQueries queries;
    private Connection con;

    public LoginController()
    {
        this.con = DatabaseConnection.connectionOracle();
        this.queries = new DatabaseQueries();
    }

    // Login d'usuari
    public UserModel login(String username, String password)
    {
        UserModel user = queries.validateLogin(username, password);
        return user;
    }

}
