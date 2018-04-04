package co.example.um2.aigle.alo.Common.Commerce.ListItems;

/**
 * Created by L'Albatros on 4/2/2018.
 */

public class Item {

    private String nom;
    private String categorie;
    private String item;
    private String description;
    private String prix;
    private String longitude;
    private String lattitude;

    public Item(String nom, String categorie, String item, String description, String prix, String longitude, String lattitude) {
        this.nom = nom;
        this.categorie = categorie;
        this.item = item;
        this.description = description;
        this.prix = prix;
        this.longitude = longitude;
        this.lattitude = lattitude;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }
}
