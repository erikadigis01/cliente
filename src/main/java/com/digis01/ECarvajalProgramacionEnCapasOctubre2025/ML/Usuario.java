package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;


public class Usuario {
    
    
    @JsonProperty("idUsuario")
    private int IdUsuario;
    @Pattern(regexp="(?=.*[a-zA-ZñÑ])(?=.*[\\d])(?=.*[._-])^[a-zA-ZñÑ][a-zA-Z0-9_.-]+?$", message = "Su username debe contener Letras, numeros y caracteres especiales")
    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo debe contener datos")
    @Size(min = 5, max = 17, message = "Limite de letras excedido, entre 5 y 20")
    
    @JsonProperty("userName")
    private String UserName;
  
    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo debe contener datos")
    @Size(min = 2, max = 17, message = "Limite de letras excedido, entre 2 y 20")
    @Pattern(regexp = "^[a-zA-Z]+(?: [a-zA-Z]+)?$", message = "El nombre solo debe contener letras")
    @JsonProperty("nombre")
    private String Nombre;
    
    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo debe contener datos")
    @Size(min = 2, max = 20, message = "Limite de letras excedido, entre 2 y 20")
    @Pattern(regexp = "^[a-zA-ZñÑ]+?$", message = "Solo se permiten letras")
    @JsonProperty("apellidoPaterno")
    private String ApellidoPaterno;
    
    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo debe contener datos")
    @Size(min = 2, max = 20, message = "Limite de letras excedido, entre 2 y 20")
    @Pattern(regexp = "^[a-zA-ZñÑ]+?$", message = "Solo se permiten letras")
    @JsonProperty("apellidoMaterno")
    private String ApellidoMaterno;
    
    
    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo debe contener datos")
    @Pattern(regexp = "(^[^\\s]+@+[a-zA-Z0-9_-]+[\\.]+[a-zA-Z0-9_-]+[\\.]+[a-zA-Z0-9_-]+$)", message = "Direccion de correo invalida")
    @JsonProperty("email")
    private String Email;
    
    @NotNull(message = "El campo no puede ser nulo", groups = ValidationGroup.OnCreate.class)
    @NotBlank(message = "El campo debe contener datos")
//    @Pattern(regexp ="(?=.{8})(?=.*[A-Z])(?=.*[-_])^[a-zA-ZñÑ0-9_-]+$" , message = "El password debe contener una mayuscula, un numero, un guion y debe tener 8 caracteres minimo")
    @JsonProperty("password")
    private String Password;
    
    @NotNull(message = "El campo no puede ser nulo")
//    @NotBlank(message = "El campo debe contener datos")
    @Past(message = "La fecha debe ser anterior al dia actual")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("fechaNacimiento")
    private Date FechaNacimiento;
   
    @NotNull(message = "El campo no puede ser vacio")
    @JsonProperty("sexo")
    private char Sexo;
    
    @NotNull(message = "El campo no puede ser nulo")
    @Pattern(regexp = "^\\+*([0-9]{2,3})*[\\s{1}]?[0-9]{10}$", message = "Numero de telefono no valido")
    @JsonProperty("telefono")
    private String Telefono;
    
    @NotNull(message = "El campo no puede ser nulo")
    @Pattern(regexp = "^\\+*([0-9]{2,3})*[\\s{1}]?[0-9]{10}$", message = "Numero de celular no valido")
    @JsonProperty("celular")
    private String Celular;
    
    @NotNull(message = "El campo no puede ser nulo")
    @NotBlank(message = "El campo debe contener datos")
    @JsonProperty("curp")
    private String Curp;
    //@Pattern(regexp = "/^([A-Z]{4})([0-9]{6})([HM]{1})([A-Z]{5})(([A-Z]{0,1})([0-9]{1,2}))$/gm", message = "CURP no valida")
    
    @JsonProperty("imagen")
    private String Imagen;
    
    @Valid
    @JsonProperty("Roll")
    public Roll Roll;
    
    @Valid
    @JsonProperty("Direccion")
    public List<Direccion> Direccion;
    
   
    
    public Usuario(){
    
    }
    
    public Usuario(int IdUsuario, String UserName, String Nombre, String ApellidoPaterno, String ApellidoMaterno, String Email, 
           String Password, Date FechaNacimiento, char Sexo, String Telefono, String Celular, String Curp, String Imagen ){
        this.IdUsuario = IdUsuario;
        this.UserName = UserName;
        this.Nombre = Nombre;
        this.ApellidoPaterno = ApellidoPaterno;
        this.ApellidoMaterno = ApellidoMaterno;      
        this.Email = Email;
        this.Password = Password;
        this.FechaNacimiento = FechaNacimiento;
        this.Sexo = Sexo;
        this.Telefono = Telefono;
        this.Celular = Celular;
        this.Curp = Curp;
        this.Imagen = Imagen;
      
    }
    
    public void setIdUsuario (int IdUsuario){
        this.IdUsuario = IdUsuario;
    }
    public int getIdUsuario(){
        return IdUsuario;
    }
    
    public void setUserName(String UserName){
        this.UserName = UserName;
    }
    public String getUserName(){
        return UserName;
    }
    
    public void setNombre(String Nombre){
        this.Nombre = Nombre;
    }
    public String getNombre(){
        return Nombre;
    }
    
    public void setApellidoPaterno(String ApellidoPaterno){
        this.ApellidoPaterno = ApellidoPaterno;
    }
    public String getApellidoPaterno(){
        return ApellidoPaterno;
    }
    
    public void setApellidoMaterno(String ApellidoMaterno){
        this.ApellidoMaterno = ApellidoMaterno;
    }
    public String getApellidoMaterno(){
        return ApellidoMaterno;
    }
    
    public void setEmail(String Email){
        this.Email = Email;
    }
    
    public String getEmail(){
        return Email;
    }
    
    public void setPassword(String Password){
        this.Password = Password;
    }
    public String getPassword(){
        return Password;
    }
    
    public void setFechaNacimiento(Date FechaNacimiento){
        this.FechaNacimiento = FechaNacimiento;
    }
    public Date getFechaNacimiento(){
        return FechaNacimiento;
    }
    
    public void setSexo(char Sexo){
        this.Sexo = Sexo;
    }
    public char getSexo(){
        return Sexo;
    }
    
    public void setTelefono(String Telefono){
        this.Telefono = Telefono;
    }
    public String getTelefono(){
        return Telefono;
    }
    
    public void setCelular(String Celular){
        this.Celular = Celular;
    }
    public String getCelular(){
        return Celular;
    }
    
    public void setCurp(String Curp){
        this.Curp = Curp;
    }
    public String getCurp(){
        return Curp;
    }
    
    public void setImagen(String Imagen){
        
        this.Imagen = Imagen;
    
    }
    public String getImagen(){
    
        return Imagen;
        
    }
    
    
     public Roll getRoll() {
        return Roll;
    }

    public void setRoll(Roll Roll) {
        this.Roll = Roll;
    }

    public List<Direccion> getDireccion() {
        return Direccion;
    }

    public void setDireccion(List<Direccion> Direccion) {
        this.Direccion = Direccion;
    }
    
    
}
