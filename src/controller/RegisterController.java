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
        UserModel user = queries.validateRegister(con, username, company, sector, password, repeatPassword);

        if (user != null)
        {
            System.out.println("Se ha creado una cuenta: " + user.getUsername());
            return user;
        } else
        {
            System.out.println("Register fallido. un campo no és correcto.");
            return null;
        }
    }
}
