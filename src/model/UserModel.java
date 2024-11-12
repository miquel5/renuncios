package model;

public class UserModel
{
    private String id;
    private String username;
    private String sede;
    private String company;
    private String sector;
    private String role;

    public UserModel()
    {
        this.sede = "Lleida";
        this.role = "Default";
    }

    // Getters
    public String getId() { return this.id; }
    public String getUsername() { return this.username; }
    public String getSede() { return this.sede; }
    public String getCompany() { return this.company; }
    public String getRole() { return this.role; }

    // Setters
    public void setId(String pid) { this.id = pid; }
    public void setUsername(String pusername) { this.username = pusername; }
    public void setSede(String psede) { this.sede = psede; }
    public void setCompany(String pcompany) { this.company = pcompany; }
    public void setSector(String psector) { this.sector = psector; }
    public void setRole(String prole) { this.role = prole; }
}
