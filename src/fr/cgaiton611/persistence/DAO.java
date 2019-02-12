package fr.cgaiton611.persistence;

import java.sql.Connection;

public abstract class DAO<T> {

    public Connection connection = ConnectionDatabase.getInstance();


    // Permet de creer un objet dans la base de donnees.
    
    public abstract T create (T obj);

    // Permet de trouver un objet
    
    public abstract T find (T obj);

    // Permet de modifier un objet existant dans la base de donnees.

    public abstract T  update (T obj);

    // Permet de supprimer un objet dans la base de donnees.

    public abstract void delete(T obj);


}

