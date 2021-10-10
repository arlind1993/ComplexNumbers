package com.company;

public class Main {

    public static void main(String[] args) {
        ComplexNumber.precision=6;
        ComplexNumber cn1=new ComplexNumber(-53,21);
        ComplexNumber cn2=new ComplexNumber(47,-43);
        System.out.println(cn1.divide(cn2));
    }
}