package model;

public class UserModel
{
    private static UserModel instance;

    private String id;
    private String username;
    private String sede;
    private String company;
    private String sector;
    private String role;
    private String cif;

    public UserModel()
    {
        this.sede = "Lleida";
        this.role = "Default";
    }

    // Getters
    public static UserModel getInstance() { if (instance == null) { instance = new UserModel();} return instance; }

    public String getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getSede() { return this.sede; }
    public String getCompany() { return this.company; }
    public String getSector() { return this.sector; }
    public String getRole() { return this.role; }
    public String getCif() { return cif; }

    // Setters
    public void setId(String pid) { this.id = pid; }
    public void setUsername(String pusername) { this.username = pusername; }
    public void setSede(String psede) { this.sede = psede; }
    public void setCompany(String pcompany) { this.company = pcompany; }
    public void setSector(String psector) { this.sector = psector; }
    public void setRole(String prole) { this.role = prole; }
    public void setCif(String cif) { this.cif = cif; }
}
