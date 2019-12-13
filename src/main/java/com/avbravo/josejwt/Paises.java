/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.josejwt;

/**
 *
 * @author avbravo
 */
public class Paises {
     private String id;
 private String pais;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public Paises() {
    }

    public Paises(String id, String pais) {
        this.id = id;
        this.pais = pais;
    }
    
    
 
 
}
