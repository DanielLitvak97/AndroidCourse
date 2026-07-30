package com.example.daniellitvakproject;

public class FirebaseAnimal {
    private int id;
    private String name;
    private String kingdom;
    private String phylum;
    private String subphylum;
    private String className;
    private String order;
    private String family;
    private String image;

    public FirebaseAnimal(){

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getKingdom() {
        return kingdom;
    }

    public String getPhylum() {
        return phylum;
    }

    public String getSubphylum() {
        return subphylum;
    }

    public String getClassName() {
        return className;
    }

    public String getOrder() {
        return order;
    }

    public String getFamily() {
        return family;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id){
        this.id = id;
    }
}
