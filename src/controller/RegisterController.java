package controller;

import model.UserModel;
import service.DatabaseQueries;
import utils.DatabaseConnection;

import java.sql.Connection;

public class RegisterController
{
    private DatabaseQueries queries;
    private Connection con;

    public RegisterController()
    {
        this.con = DatabaseConnection.connectionOracle();
        this.queries = new DatabaseQueries();
    }

    public UserModel register(String username, String company, String sector, String password, String repeatPassword)
    {
        UserModel user = queries.validateRegister(con, username, company, sector, password, repeatPassword, "default");
        return user;
    }
}
