package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;

import jakarta.validation.constraints.Min;


public class Roll {

    @Min(value = 1, message = "Selecciona un rol válido")
    private int IdRoll;
    
    private String NombreRoll;
    
    public Roll(){
    
    }
    
    public Roll(int IdRoll, String NombreRoll){
        
        this.IdRoll = IdRoll;
        this.NombreRoll = NombreRoll;
    }
    
    public void setIdRoll(int IdRoll){
        this.IdRoll = IdRoll;
    }
    public int getIdRoll(){
        return IdRoll;
    }
    
    public void setNombreRoll(String NombreRoll){
        this.NombreRoll = NombreRoll;
    }
    public String getNombreRoll(){
        return NombreRoll;
    }
}
