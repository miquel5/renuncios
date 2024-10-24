package model;

public class TypeModel
{
    private int id;
    private String type;
    private String text;
    private String datai;
    private String dataf;
    private String size;
    private boolean color;
    private double price;

    public TypeModel(int pid, String ptext, String pdatai, String pdataf, String psize, boolean pcolor, double pprice) {
        this.id = pid;
        this.text = ptext;
        this.datai = pdatai;
        this.dataf = pdataf;
        this.size = psize;
        this.color = pcolor;
        this.price = pprice;
    }

    // Getters

    public int getId()
    {
        return id;
    }

    public String getType()
    {
        return type;
    }

    public String getText()
    {
        return text;
    }

    public String getDatai()
    {
        return datai;
    }

    public String getDataf()
    {
        return dataf;
    }

    public String getSize()
    {
        return size;
    }

    public boolean isColor()
    {
        return color;
    }

    public double getPrice()
    {
        return price;
    }

    // Setters

    public void setId(int pid)
    {
        this.id = pid;
    }

    public void setType(String ptype)
    {
        this.type = ptype;
    }

    public void setText(String ptext)
    {
        this.text = ptext;
    }

    public void setDatai(String pdatai)
    {
        this.datai = pdatai;
    }

    public void setDataf(String pdataf)
    {
        this.dataf = pdataf;
    }

    public void setSize(String psize)
    {
        this.size = psize;
    }

    public void setColor(boolean pcolor)
    {
        this.color = pcolor;
    }

    public void setPrice(double pprice)
    {
        this.price = pprice;
    }
}
