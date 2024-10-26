package model;

import java.util.Date;

public class ServiceModel
{
    private int numC;
    private int numS;
    private String typee;
    private String txt;
    private Date dataI;
    private Date dataF;
    private String sizee;
    private boolean color;
    private double price;

    public ServiceModel(int numC, int numS, String typee, String txt, Date dataI, Date dataF, String sizee, boolean color, double price)
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
    }

    // Getters
    public int getNumC() { return numC; }
    public int getNumS() { return numS; }
    public String getTypee() { return typee; }
    public String getTxt() { return txt; }
    public Date getDataI() { return dataI; }
    public Date getDataF() { return dataF; }
    public String getSizee() { return sizee; }
    public boolean getColor() { return color; }
    public double getPrice() { return price; }

    // Setters
    public void setNumC(int numC) { this.numC = numC; }
    public void setNumS(int numS) { this.numS = numS; }
    public void setTypee(String typee) { this.typee = typee; }
    public void setTxt(String txt) { this.txt = txt; }
    public void setDataI(Date dataI) { this.dataI = dataI; }
    public void setDataF(Date dataF) { this.dataF = dataF; }
    public void setSizee(String sizee) { this.sizee = sizee; }
    public void setColor(boolean color) { this.color = color; }
    public void setPrice(double price) { this.price = price; }
}