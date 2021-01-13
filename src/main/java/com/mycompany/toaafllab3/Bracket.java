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
public class Bracket implements Statement {

    private boolean type;

    public Bracket(boolean type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(type) return "{";
        else return "}";
    }
    
    public boolean isType() {
        return type;
    }

    @Override
    public String toPascalString() {
        if(type) return "begin";
        else return "end";
    }

    @Override
    public String toCString() {
         if(type) return "{";
        else return "}";
    }
}
