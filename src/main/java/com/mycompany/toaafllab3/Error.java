/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.toaafllab3;

/**
 *
 * @author Matt
 */
public class Error implements Statement {
    public Error(){
        
    }

    @Override
    public String toPascalString() {
        return "Ошибка!";
    }

    @Override
    public String toCString() {
        return "Ошибка!";
    }
}
