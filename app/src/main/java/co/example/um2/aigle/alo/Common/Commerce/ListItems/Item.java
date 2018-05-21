package co.example.um2.aigle.alo.Common.Commerce.ListItems;

import java.io.Serializable;

/**
 * Created by L'Albatros on 4/2/2018.
 */

public class Item implements Serializable {

    private String nom;
    private String categorie;
    private String item;
    private String description;
    private String prix;
    private String longitude;
    private String lattitude;
    private String city;
    private String telephone;

    public Item(String nom, String categorie, String item, String description, String prix, String longitude, String lattitude, String city, String telephone) {
        this.nom = nom;
        this.categorie = categorie;
        this.item = item;
        this.description = description;
        this.prix = prix;
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.city = city;
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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
