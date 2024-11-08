package model;

import java.util.ArrayList;
import java.util.Date;

public class ServiceModel
{
    private static ArrayList<ServiceModel> instances = new ArrayList<>(); // Llistar tots els serviceModel

    private int numC;
    private int numS;
    private int typee;
    private String txt;
    private Date dataI;
    private Date dataF;
    private int sizee;
    private int color;
    private double price;

    public ServiceModel(int numC, int numS, int typee, String txt, Date dataI, Date dataF, int sizee, int color, double price)
    {
        this.numC = numC;
        this.numS = numS;
        this.typee = typee;
        this.txt = txt;
        this.dataI = dataI;
        this.dataF = dataF;
        this.sizee = sizee;
        this.color = color;
        this.price = price;

        instances.add(this); // Afegir a la llista
    }

    // Getters
    public int getNumC() { return numC; }
    public int getNumS() { return numS; }
    public int getTypee() { return typee; }
    public String getTxt() { return txt; }
    public Date getDataI() { return dataI; }
    public Date getDataF() { return dataF; }
    public int getSizee() { return sizee; }
    public int getColor() { return color; }
    public double getPrice() { return price; }

    public static ArrayList<ServiceModel> getAll()
    {
        return instances;
    }

    // Setters
    public void setNumC(int numC) { this.numC = numC; }
    public void setNumS(int numS) { this.numS = numS; }
    public void setTypee(int typee) { this.typee = typee; }
    public void setTxt(String txt) { this.txt = txt; }
    public void setDataI(Date dataI) { this.dataI = dataI; }
    public void setDataF(Date dataF) { this.dataF = dataF; }
    public void setSizee(int sizee) { this.sizee = sizee; }
    public void setColor(int color) { this.color = color; }
    public void setPrice(double price) { this.price = price; }
}