package model;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;

public class ServiceModel
{
    private static ArrayList<ServiceModel> instances = new ArrayList<>(); // Llistar tots els serviceModel

    // General
    private int uniqueId;
    private int numC;
    private int numS;
    private int tipo;
    private String txt;
    private Blob imatge;
    private Date dataI;
    private Date dataF;
    private int mida;
    private int color;
    private double precio;
    private String pagamento;

    // Web
    private int idw;
    private String w_nombre;
    private String w_enlace;
    private double w_preup;
    private double w_preum;
    private double w_preug;

    // Pancarta
    private int idl;
    private String l_descrip;
    private String l_cordenadas;
    private double l_preu;

    // Flayer
    private int cp;
    private String f_poblacio;
    private String f_provincia;
    private double f_preu;

    private double total;
    private int mes;

    // Constructor
    public ServiceModel(int uniqueId, int mes, int pnumC, int pnumS, int ptipo, String ptxt, Blob pimatge, Date pdataI, Date pdataF, int pmida, int pcolor, double pprecio, String ppagamento,
                        int pidw, String pw_nombre, String pw_enlace, double pw_preup, double pw_preum, double pw_preug,
                        int pidl, String pl_descrip, String pl_cordenadas, double pl_preu,
                        int pcp, String pf_poblacio, String pf_provincia, double pf_preu)
    {
        // General
        this.uniqueId = uniqueId;
        this.mes = mes;
        this.numC = pnumC;
        this.numS = pnumS;
        this.tipo = ptipo;
        this.txt = ptxt;
        this.imatge = pimatge;
        this.dataI = pdataI;
        this.dataF = pdataF;
        this.mida = pmida;
        this.color = pcolor;
        this.precio = pprecio;
        this.pagamento = ppagamento;

        // Web
        this.idw = pidw;
        this.w_nombre = pw_nombre;
        this.w_enlace = pw_enlace;
        this.w_preup = pw_preup;
        this.w_preum = pw_preum;
        this.w_preug = pw_preug;

        // Pancarta
        this.idl = pidl;
        this.l_descrip = pl_descrip;
        this.l_cordenadas = pl_cordenadas;
        this.l_preu = pl_preu;

        // Flayer
        this.cp = pcp;
        this.f_poblacio = pf_poblacio;
        this.f_provincia = pf_provincia;
        this.f_preu = pf_preu;

        // Total and mes
        this.total = precio;

        // Add instance to list
        instances.add(this);
    }

    // Getters
    public int getUniqueId() { return uniqueId; }
    public int getNumC() { return numC; }
    public int getNumS() { return numS; }
    public int getTipo() { return tipo; }
    public String getTxt() { return txt; }
    public Blob getImatge() { return imatge; }
    public Date getDataI() { return dataI; }
    public Date getDataF() { return dataF; }
    public int getMida() { return mida; }
    public int getColor() { return color; }
    public double getPrecio() { return precio; }
    public String getPagamento() { return pagamento; }

    public int getIdw() { return idw; }
    public String getWNombre() { return w_nombre; }
    public String getWEnlace() { return w_enlace; }
    public double getWPreup() { return w_preup; }
    public double getWPreum() { return w_preum; }
    public double getWPreug() { return w_preug; }

    public int getIdl() { return idl; }
    public String getLDescrip() { return l_descrip; }
    public String getLCordenadas() { return l_cordenadas; }
    public double getLPreu() { return l_preu; }

    public int getCp() { return cp; }
    public String getFPoblacio() { return f_poblacio; }
    public String getFProvincia() { return f_provincia; }
    public double getFPreu() { return f_preu; }

    public double getTotal() { return total; }
    public int getMes() { return mes; }

    public static ArrayList<ServiceModel> getAll() {
        return instances;
    }

    // Setters
    public void setUniqueId(int uniqueId) { this.uniqueId = uniqueId; }
    public void setNumC(int numC) { this.numC = numC; }
    public void setNumS(int numS) { this.numS = numS; }
    public void setTipo(int tipo) { this.tipo = tipo; }
    public void setTxt(String txt) { this.txt = txt; }
    public void setImatge(Blob imatge) { this.imatge = imatge; }
    public void setDataI(Date dataI) { this.dataI = dataI; }
    public void setDataF(Date dataF) { this.dataF = dataF; }
    public void setMida(int mida) { this.mida = mida; }
    public void setColor(int color) { this.color = color; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setPagamento(String pagamento) { this.pagamento = pagamento; }

    public void setIdw(int idw) { this.idw = idw; }
    public void setWNombre(String w_nombre) { this.w_nombre = w_nombre; }
    public void setWEnlace(String w_enlace) { this.w_enlace = w_enlace; }
    public void setWPreup(double w_preup) { this.w_preup = w_preup; }
    public void setWPreum(double w_preum) { this.w_preum = w_preum; }
    public void setWPreug(double w_preug) { this.w_preug = w_preug; }

    public void setIdl(int idl) { this.idl = idl; }
    public void setLDescrip(String l_descrip) { this.l_descrip = l_descrip; }
    public void setLCordenadas(String l_cordenadas) { this.l_cordenadas = l_cordenadas; }
    public void setLPreu(double l_preu) { this.l_preu = l_preu; }

    public void setCp(int cp) { this.cp = cp; }
    public void setFPoblacio(String f_poblacio) { this.f_poblacio = f_poblacio; }
    public void setFProvincia(String f_provincia) { this.f_provincia = f_provincia; }
    public void setFPreu(double f_preu) { this.f_preu = f_preu; }

    public void setTotal(double total) { this.total = total; }
    public void setMes(int mes) { this.mes = mes; }

    // Others
    public void sumTotal(double ptotal) { this.total += ptotal; }
    public void subtractTotal(double ptotal) { this.total -= ptotal; }
}
