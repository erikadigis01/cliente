package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;


public class Direccion {
    
    private int IdDireccion;
    
    @NotNull(message = "El campo no puede ser nulo")
    private String Calle;
    
    
    private String NumeroInterior;
    
    @NotNull(message = "El campo no puede ser nulo")
    private String NumeroExterior;
    
    public Colonia Colonia;
    
    public Direccion(){
    
    }
    
    public Direccion(int IdDireccion, String Calle, String NumeroInterior, String NumeroExterior){
        this.IdDireccion = IdDireccion;
        this.Calle = Calle;
        this.NumeroInterior = NumeroInterior;
        this.NumeroExterior = NumeroExterior;
    }
    
    public void setIdDireccion(int IdDireccion){
        this.IdDireccion = IdDireccion;
    }
    public int getIdDireccion(){
        return IdDireccion;
    }
    
    public void setCalle(String Calle){
        this.Calle = Calle;
    }
    public String getCalle(){
        return Calle;
    }
    public void setNumeroInterior(String NumeroInterior){
        this.NumeroInterior = NumeroInterior;
    }
    public String getNumeroInterior(){
        return NumeroInterior;
    }
    
    public void setNumeroExterior(String NumeroExterior){
        this.NumeroExterior = NumeroExterior;
    }
    public String getNumeroExterior(){
        return NumeroExterior;
    }
    public Colonia getColonia() {
        return Colonia;
    }

    public void setColonia(Colonia Colonia) {
        this.Colonia = Colonia;
    }
    
}
