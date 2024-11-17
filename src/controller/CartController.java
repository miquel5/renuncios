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
    public ServiceModel findService(int uniqueId)
    {
        for (ServiceModel service : services)
        {
            if (service.getUniqueId() == uniqueId)
            {
                return service;
            }
        }
        return null;
    }

    // Generar un tíquet
    public static void generateTicket()
    {
        // TODO: Afegir aquí el codi per cridar a una nova databaseQueries.generateTiked()
    }
}
