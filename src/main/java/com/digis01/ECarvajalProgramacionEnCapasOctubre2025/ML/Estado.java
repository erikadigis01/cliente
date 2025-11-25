package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;


public class Estado {
    
    private int IdEstado;
    private String Nombre;
    public Pais Pais;
    
    public Estado(){
    
    }
    
    public Estado(int IdEstado, String Nombre){
    
        this.IdEstado = IdEstado;
        this.Nombre = Nombre;
    }
    
    public void setIdEstado(int IdEstado){
    
        this.IdEstado = IdEstado;
    }
    public int getIdEstado(){
        return IdEstado;
    }
    
    public void setNombre(String Nombre){
    
        this.Nombre = Nombre;
    }
    public String getNombre(){
        return Nombre;
    }

    public Pais getPais() {
        return Pais;
    }

    public void setPais(Pais Pais) {
        this.Pais = Pais;
    }
    
}
