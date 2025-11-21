package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;


public class Pais {
    
    private int IdPais;
    private String Nombre;
    
    public Pais(){
    
    }
    
    public Pais(int IdPais, String Nombre){
    
        this.IdPais = IdPais;
        this.Nombre = Nombre;
    }
    public void setIdPais(int IdPais){
    
        this.IdPais = IdPais;
    }
    public int getIdPais(){
        return IdPais;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    public String getNombre(){
        return Nombre;
    }
}
