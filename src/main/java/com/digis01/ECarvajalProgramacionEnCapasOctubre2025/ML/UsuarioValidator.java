package com.digis01.ECarvajalProgramacionEnCapasOctubre2025.ML;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;

@Service

public class UsuarioValidator {


    @Autowired
    private Validator validator;


    public BindingResult validateObject(Object target){

        DataBinder dataBinder = new DataBinder(target);

        dataBinder.setValidator(validator);

        dataBinder.validate();


        return dataBinder.getBindingResult();

    }


    


}