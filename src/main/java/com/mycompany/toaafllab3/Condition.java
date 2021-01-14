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
public class Condition implements Statement{

   
    private String oper1;
    private String operator;
    private String oper2;

    public Condition(String oper1, String operator, String oper2) {
        this.oper1 = oper1;
        this.operator = operator;
        this.oper2 = oper2;
    }

    public String getOper1() {
        return oper1;
    }

    public String getOperator() {
        return operator;
    }

    public String getOper2() {
        return oper2;
    }
    public static boolean isCondition(String x){
        return (x.contains("<")||x.contains(">")||x.contains("="))&&!x.contains(":");
    }
    private static String removebrackets(String x){
        String res=x;
        if(x.contains("(")) res=res.substring(1);
        if(x.contains(")")) res=res.substring(0,res.length()-1);
        return res;
    }
    public static Condition ParseCondition(String x){
        x=removebrackets(x);
        int spos1=0;
        int spos2=0;
        for(int i=0;i<x.length();i++){
            if(x.charAt(i)=='<'||x.charAt(i)=='>'||x.charAt(i)=='='){
                if(spos1!=0) spos2=i;
                else spos1=i;
            }
        }
        if(spos2==0) return new Condition(x.substring(0, spos1),x.substring(spos1, spos1+1),x.substring(spos1+1,x.length()));
        else return new Condition(x.substring(0, spos1),x.substring(spos1, spos2+1),x.substring(spos2+1,x.length()));
    }

    @Override
    public String toString() {
        if(operator.equals("=")) return '(' + oper1 + " == " + oper2 + ')';
        if(operator.equals("<>")) return '(' + oper1 + " != " + oper2 + ')';
        return '(' + oper1 + " " + operator + " " + oper2 + ')';
    }

    @Override
    public String toPascalString() {
         return oper1 +" "+operator+" "+oper2;
    }

    @Override
    public String toCString() {
        if(operator.equals("=")) return '(' + oper1 + " == " + oper2 + ')';
        if(operator.equals("<>")) return '(' + oper1 + " != " + oper2 + ')';
        return '(' + oper1 + " " + operator + " " + oper2 + ')';
    }
    
    
}
