/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.toaafllab3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 *
 * @author Matt
 */
interface AutomatState{
        AutomatState nextState(String x, Stack<Statement> out);
    }
class Transfer{
    char[] arg;
    public Transfer(String x){
        arg=x.toCharArray();
    }
    public void SetArg(String x){
        arg=x.toCharArray();
    }
    public String GetArg(){
        return String.valueOf(arg);
    }
}
class State{
    private static Statement checkErrorType(String head){
        if(Condition.isCondition(head)) return Condition.ParseCondition(head);
        if(Assignement.isAssignement(head)) return Assignement.ParseAssignement(head);
        if(head.equals("if")) return new If();
        if(head.equals("then")) return new Then();
        if(head.equals("else")) return new Else();
        if(head.equals("begin")) return new Bracket(true);
        if(head.equals("end;")) return new Bracket(false);
        return null;
    }
    static AutomatState If(String head, Stack<Statement> out){
        if(head.equals("if")) {
            out.push(new If());
            return State::Condition;
        }
        else{
            if(head.equals("")){
                return State::If;
            }
            else{
                out.push(checkErrorType(head));
                out.push(new Error());
                return null;
            }
        }
    }
    static AutomatState Condition(String head, Stack<Statement> out){
        if(Condition.isCondition(head)){
            out.push(Condition.ParseCondition(head));
            return State::Then;
        }
         else{
            if(head.equals("")) return State::Condition;
            else{
                out.push(checkErrorType(head));
                out.push(new Error());
                return null;
            }
        }
    }
    static AutomatState Then(String head, Stack<Statement> out){
        if(head.equals("then")) {
            out.push(new Then());
            return State::Body;
        }
        else{
            if(head.equals("")) return State::Then;

            else{
                out.push(checkErrorType(head));
                out.push(new Error());
                return null;
            }
        }
    }
    static AutomatState Body(String head, Stack<Statement> out){
        if(head.equals("begin")){
            out.push(new Bracket(true));
            return State::ManyOperators;
        }
        else{
            if(head.equals("")) return State::Body;
            else {
                if(Assignement.isAssignement(head)){
                    out.push(Assignement.ParseAssignement(head));
                    return State::Else;
                }
                else{
                    out.push(checkErrorType(head));
                    out.push(new Error());
                    return null;
                }  
            }
        }
    }
    
    static AutomatState ManyOperators(String head, Stack<Statement> out){
        if(Assignement.isAssignement(head)){
            out.push(Assignement.ParseAssignement(head));
            return State::ManyOperators;
        }
        else{
            if(head.equals("")){
                return State::ManyOperators;
            }
            else{
                if(head.equals("end;")){
                    out.push(new Bracket(false));
                    return State::Else;
                }
                else{
                    out.push(checkErrorType(head));
                    out.push(new Error());
                    return null;
                }  
            }
        }
    }
    static AutomatState Else(String head, Stack<Statement> out){
        if(head.equals("else")) {
            out.push(new Else());
            return State::BodyElse;
        }
        else{
            if(head.equals("")) return State::Else;
            
            else{
                if(head.equals("e")) return State::Finish;
                else{
                    out.push(checkErrorType(head));
                    out.push(new Error());
                    return null;
                }
            }
        }
    }
    static AutomatState BodyElse(String head, Stack<Statement> out){
        if(head.equals("begin")){
            out.push(new Bracket(true));
            return State::ManyOperatorsElse;
        }
        else{
            if(head.equals("")) return State::Body;
            else {
                if(Assignement.isAssignement(head)){
                    out.push(Assignement.ParseAssignement(head));
                    return State::BodyElse;
                }
                else{
                   if(head.equals("e")) return State::Finish;
                    else{
                        out.push(checkErrorType(head));
                        out.push(new Error());
                        return null;
                    }
                }  
            }
        }
    }
    
    static AutomatState ManyOperatorsElse(String head, Stack<Statement> out){
       if(Assignement.isAssignement(head)){
            out.push(Assignement.ParseAssignement(head));
            return State::ManyOperatorsElse;
        }
        else{
            if(head.equals("")){
                return State::ManyOperatorsElse;
            }
            else{
                if(head.equals("end;")){
                    out.push(new Bracket(false));
                    return State::ManyOperatorsElse;
                }
                else{
                    if(head.equals("e")) return State::Finish;
                    else{
                        out.push(checkErrorType(head));
                        out.push(new Error());
                        return null;
                    }
                }  
            }
        }
    }
    static AutomatState Finish(String head, Stack<Statement> out){
        return State::Finish;
    }
}
public class Analyzer {
    public Analyzer(){
        
    }
    
    private String head(ArrayList<String> x){
        if(x.isEmpty()) return null;
        else return x.get(0);
    }
    public ArrayList<String> tail(ArrayList<String> x){
        if(x.isEmpty()) return null;
        else{
            x.remove(0);
            return x;
        }
    }
    private String translate(Stack<Statement> out){
        Collections.reverse(out);
        int tabs=0;
        boolean oneoper=false;
        String result="";
        while(!out.isEmpty()){
            Statement temp=out.pop();
            if(temp instanceof If){
                for(int i=0;i<tabs;i++){
                    result+="    ";
                }
                result+=temp.toCString()+" ";
                tabs++;
            }
            if(temp instanceof Condition){
                result+=temp.toCString()+" ";
            }
            if(temp instanceof Then){
                if(out.peek() instanceof Bracket) {
                    oneoper=false;
                    result+="{\n";
                }
                else{
                    oneoper=true;
                    result+="\n";
                }
            }
            if (temp instanceof Assignement){
                for(int i=0;i<tabs;i++){
                    result+="    ";
                }
                result+=temp.toCString()+"\n";
                if(oneoper) tabs--;
            }
            if(temp instanceof Bracket){
                if(!((Bracket) temp).isType()){
                    tabs--;
                    for(int i=0;i<tabs;i++){
                        result+="    ";
                    }
                    result+="}\n";
                }
                
            }
            if(temp instanceof Else){
                for(int i=0;i<tabs;i++){
                    result+="    ";
                }
                result+="else ";
                if(out.peek() instanceof Bracket) {
                    oneoper=false;
                    result+="{\n";
                }
                else{
                    oneoper=true;
                    result+="\n";
                }
                tabs++;
            }
            if(temp instanceof Error){
                return "Syntax Error";
            }
        }
        return result;
    }
    public String Run(ArrayList<String> input){ //Стартовая точка анализа
        Stack<Statement> out=new Stack<>();
        input.add("e"); //Дополняем строку эпсилоном
        IfThenElse(head(input),tail(input),State::If,out,true,new Transfer("")); //Рекурсивная процедура
        return translate(out);
    }
    private void IfThenElse(String head, ArrayList<String> tail, AutomatState state, Stack<Statement> out, boolean add,Transfer back){
        if(head!=null&&state!=null){//чекаем остались ли символы и не вышла ли ошибка
            if(out.size()>0&&head.equals("if")&&add){//проверяем является ли первым найденный if
                IfThenElse(head, tail,State::If,out,false,back); //делаем дополнительный вызов
                if(out.peek() instanceof Error){//откат изменений
                    out.pop();//выкидываем ошибку
                    head=out.pop().toPascalString();//Возвращаем значение вызвавшее ошибку
                    tail.add(0, back.GetArg());
                }
            }
            IfThenElse(head(tail),tail(tail),state.nextState(head, out),out,true,back);
        }
        else{
            if(state==null){
                back.SetArg(head);
            }
        }
    }
}
