package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;


public class Municipio {

    private int IdMunicipio;
    private String Nombre;
    public Estado Estado;
    
    
    public Municipio(){
    
    }
    
    public Municipio(int IdMunicipio, String Nombre){
    
        this.IdMunicipio = IdMunicipio;
        this.Nombre = Nombre;
    }
    
    public void setIdMunicipio(int IdMunicipio){
        this.IdMunicipio = IdMunicipio;
    }
    public int getIdMunicipio(){
        return IdMunicipio;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    public String getNombre(){
        return Nombre;
    }

    public Estado getEstado() {
        return Estado;
    }

    public void setEstado(Estado Estado) {
        this.Estado = Estado;
    }
    
}
