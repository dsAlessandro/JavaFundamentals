package Implementation;

import java.lang.Math;

public class Complex implements Comparable<Complex> {
    double Re;
    double Im;

    public Complex(double re, double im) {
        Re = re;
        Im = im;
    }

    public Complex(Complex c) {
        Re = c.Re;
        Im = c.Im;
    }

    public void setRe(double re) {
        Re = re;
    }

    public void setIm(double im) {
        Im = im;
    }

    public void setBoth(double re, double im) {
        Re = re;
        Im = im;
    }

    public double mod() {
        return Math.sqrt(Re*Re + Im*Im);
    }


    
    // Metodi ereditati dall'interfaccia `Comparable<Complex>`
    
    @Override
    public int compareTo(Complex c) {
        if (this.mod() < c.mod())
        return -1;
        if (this.mod() > c.mod())
        return 1;
        return 0;
    }



    // Metodi ereditati dalla classe Object

    @Override
    public boolean equals(Object o) {
        if (o instanceof Complex) {
            Complex c = (Complex) o;
            return this.Re == c.Re && this.Im == c.Im;
        }

        else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "( " + Re + ", " + Im + " )";
    }
    
}
