package controller;

import model.ServiceModel;

import java.util.ArrayList;

public class CartController
{
    private ArrayList<ServiceModel> services;

    public CartController()
    {
        this.services = ServiceModel.getAll();
    }

    // Buscar un servei
    public ServiceModel findService(int numS)
    {
        for (ServiceModel service : services)
        {
            if (service.getNumS() == numS)
            {
                return service;
            }
        }
        return null;
    }

    // Generar un tíquet
    public static void generateTicket()
    {
        // TODO: Afegir aquí el codi per cridar a una nova service/DatabaseQueries
    }
}
