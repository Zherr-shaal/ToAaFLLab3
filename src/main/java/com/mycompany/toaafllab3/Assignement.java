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
public class Assignement implements Statement {
    private String var;
    private String value;

    public String getVar() {
        return var;
    }

    public String getValue() {
        return value;
    }

    public Assignement(String var, String value) {
        this.var = var;
        this.value = value;
    }
    public static boolean isAssignement(String x){
        return x.contains(":=");
    }
    public static Assignement ParseAssignement(String x){
        return new Assignement(x.substring(0, x.indexOf(':')),x.substring(x.indexOf('=')+1, x.length()-1)); 
    }

    @Override
    public String toString() {
        return  var + " = " + value + ';';
    }

    @Override
    public String toPascalString() {
         return  var + " := " + value + ';';
    }

    @Override
    public String toCString() {
        return  var + " = " + value + ';';
    }
    
}
