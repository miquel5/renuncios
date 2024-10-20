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

    public UserModel login(String username, String password)
    {
        UserModel user = queries.validateLogin(con, username, password);

        if (user != null)
        {
            return user;
        } else
        {
            return null;
        }
    }

}
