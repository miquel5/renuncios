package model;

public class UserModel
{
    private String id;
    private String username;
    private String headquarters;
    private String company;
    private String sector;
    private String role;

    public UserModel()
    {
        this.headquarters = "Lleida";
        this.role = "Client";
    }

    // Getters

    public String getId() {
        return this.id;
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getHeadquarters()
    {
        return this.headquarters;
    }

    public String getCompany()
    {
        return this.company;
    }
    public String getRole()
    {
        return this.role;
    }

    // Setters

    public void setId(String pid) {
        this.id = pid;
    }

    public void setUsername(String pusername)
    {
        this.username = pusername;
    }

    public void setHeadquarters(String pheadquarters)
    {
        this.headquarters = pheadquarters;
    }

    public void setCompany(String pcompany)
    {
        this.company = pcompany;
    }

    public void setSector(String psector)
    {
        this.sector = psector;
    }

    public void setRole(String prole)
    {
        this.role = prole;
    }

}
