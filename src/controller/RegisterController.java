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

    // Registre d'usuari
    public UserModel register(String username, String company, String sector, String cif, String password, String repeatPassword, String sede)
    {
        UserModel user = queries.validateRegister(username, company, sector, cif, password, repeatPassword, "default", sede);
        return user;
    }
}
