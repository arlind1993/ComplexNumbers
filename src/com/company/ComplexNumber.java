package com.company;

public class ComplexNumber {

    char initialisedInCoordinates;

    double realPart;
    double complexPart;

    int quadrant;

    double angle;
    double length;

    static int precision;

    public ComplexNumber() {
        this.realPart = 0;
        this.complexPart = 0;
        this.initialisedInCoordinates = 'C';
        calculatePolarCoordinates();
    }


    public ComplexNumber(double realPart) {
        this.realPart = realPart;
        this.complexPart = 0;
        this.initialisedInCoordinates = 'C';
        calculatePolarCoordinates();
    }

    ComplexNumber(double realPart, double complexPart) {
        this.realPart = realPart;
        this.complexPart = complexPart;
        this.initialisedInCoordinates = 'C';
        calculatePolarCoordinates();
    }

    ComplexNumber(double arg1, double arg2, char initialisedInCoordinates) {
        this.initialisedInCoordinates=initialisedInCoordinates;
        switch (initialisedInCoordinates) {
            case 'C':
                this.realPart = arg1;
                this.complexPart = arg2;
                calculatePolarCoordinates();
                break;
            case 'P':
                this.length = arg1;
                this.angle = arg2;
                calculateCartesianCoordinates();
                break;
        }
    }

    private void calculatePolarCoordinates() {
        length=Math.sqrt(realPart*realPart+complexPart*complexPart);

        if (realPart>0&&complexPart>0){
            quadrant=1;
            angle=Math.atan(complexPart/realPart);
        }else if (realPart<0&&complexPart>0){
            quadrant=2;
            angle=Math.atan(complexPart/realPart)+Math.PI;
        }else if (realPart<0&&complexPart<0){
            quadrant=3;
            angle=Math.atan(complexPart/realPart)-Math.PI;
        }else if (realPart>0&&complexPart<0){
            quadrant=4;
            angle=Math.atan(complexPart/realPart);
        }else if(realPart==0&&complexPart==0){
            quadrant=0;
            angle=0;
        }else if (complexPart==0){
            if (realPart>0){
                quadrant=5;
                angle=0;
            }else {
                quadrant=7;
                angle=Math.PI;
            }
        }else if (realPart==0){
            if (complexPart>0){
                quadrant=6;
                angle=Math.PI/2;
            }else {
                quadrant=8;
                angle=-Math.PI/2;
            }
        }
    }

    private void calculateCartesianCoordinates() {
        if (length<=0){
            quadrant=0;
            realPart=0;
            complexPart=0;
            angle=0;
            return;
        }

        if (angle==0){
            quadrant=5;
            realPart=length* Math.cos(angle);
            complexPart=0;
        }else if (angle==Math.PI/2){
            quadrant=6;
            realPart=0;
            complexPart=length*Math.sin(angle);;
        }else if (angle==Math.PI){
            quadrant=7;
            realPart=length* Math.cos(angle);
            complexPart=0;
        }else if (angle==-Math.PI/2){
            quadrant=8;
            realPart=0;
            complexPart=length*Math.sin(angle);;
        }else{
            realPart=length* Math.cos(angle);
            complexPart=length*Math.sin(angle);
            if (angle>0&&angle<Math.PI/2){
                quadrant=1;
            }else if (angle>Math.PI/2&&angle<Math.PI){
                quadrant=2;
            }else if (angle>-Math.PI&&angle<-Math.PI/2){
                quadrant=3;
            }else if (angle>-Math.PI/2&&angle<0){
                quadrant=4;
            }
        }
    }

    public double precise(double num){
        if (precision>0){
            return Math.round(num*Math.pow(10,precision))/Math.pow(10,precision);
        }
        return num;
    }

    public String toStringCartesianForm() {
        return "( " + precise(realPart) + " + " + precise(complexPart) + "i )";
    }

    public String toStringPolarForm() {
        return "( " + precise(length) + "( sin("+precise(angle)+") + icos(" + precise(angle) + ") )";
    }
    @Override
    public String toString() {
        return "ComplexNumber{" +
                "initInCoords=" + initialisedInCoordinates +
                ", realPart=" + precise(realPart) +
                ", complexPart=" + precise(complexPart) +
                ", quadrant=" + quadrant +
                ", angle(pi)=" + precise(angle) +
                ", angle(degree)=" + precise(Math.toDegrees(angle)) +
                ", length=" + precise(length) +
                ", precision=" + precision +
                '}';
    }

    public ComplexNumber add(ComplexNumber other){
        return new ComplexNumber(this.realPart+other.realPart, this.complexPart+other.complexPart);
    }
    public ComplexNumber subtract(ComplexNumber other){
        return new ComplexNumber(this.realPart-other.realPart, this.complexPart-other.complexPart);
    }
    public ComplexNumber multiply(ComplexNumber other){
        return new ComplexNumber(this.realPart*other.realPart-this.complexPart*other.complexPart,
                this.realPart*other.complexPart+other.realPart*this.complexPart);
    }
    public ComplexNumber divide(ComplexNumber other){
        return new ComplexNumber((this.realPart*other.realPart+this.complexPart*other.complexPart)
                /(other.realPart*other.realPart+other.complexPart*other.complexPart)
                ,(this.realPart*other.complexPart-other.realPart*this.complexPart)
                /(other.realPart*other.realPart+other.complexPart*other.complexPart)
        );
    }

    public ComplexNumber ln(){
        return new ComplexNumber(Math.log(this.length), this.angle);
    }

    public ComplexNumber log_(ComplexNumber other){
        return new ComplexNumber((Math.log(this.length)*Math.log(other.length)+this.angle*other.angle)
                /(Math.log(other.length)*Math.log(other.length)+other.angle*other.angle)
                ,(Math.log(this.length)*other.angle-Math.log(other.length)*this.angle)
                /(Math.log(other.length)*Math.log(other.length)+other.angle*other.angle)
        );
    }

    public ComplexNumber power(ComplexNumber other){
        return new ComplexNumber(Math.pow(this.length, other.realPart) * Math.exp(-this.angle*other.complexPart) *
                Math.cos(this.angle*other.realPart+other.complexPart*Math.log(this.length)),
                Math.pow(this.length, other.realPart) * Math.exp(-this.angle*other.complexPart) *
                Math.sin(this.angle*other.realPart+other.complexPart*Math.log(this.length))
        );
    }

    public ComplexNumber surd(ComplexNumber other){
        return power(other);
    }



}
