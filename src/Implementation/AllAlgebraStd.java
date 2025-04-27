package Implementation;

import Interfaces.twoDimAlgebra;
import Interfaces.threeDimAlgebra;
import Points.twoDimPoint;
import Points.threeDimPoint;
import java.lang.Math;

public class AllAlgebraStd implements twoDimAlgebra, threeDimAlgebra {
    public int distance2D(Object p1, Object p2) {
        twoDimPoint P1 = (twoDimPoint) p1;
        twoDimPoint P2 = (twoDimPoint) p2;

        double res = 0.0;
        res += (P1.getx() - P2.getx()) * (P1.getx() - P2.getx());
        res += (P1.gety() - P2.gety()) * (P1.gety() - P2.gety());
        res = Math.sqrt(res);

        return (int) res;
    }

    public void display2D(Object p) {
        twoDimPoint P = (twoDimPoint) p;

        System.out.println(P.getx() + " " + P.gety());
    }

    public int scalarProduct2D(Object p1, Object p2) {
        int res = 0;

        twoDimPoint P1 = (twoDimPoint) p1;
        twoDimPoint P2 = (twoDimPoint) p2;

        res += P1.getx() * P2.getx();
        res += P1.gety() * P2.gety();

        return res;
    }

    public int distance3D(Object p1, Object p2) {
        threeDimPoint P1 = (threeDimPoint) p1;
        threeDimPoint P2 = (threeDimPoint) p2;

        double res = 0.0;
        res += (P1.getx() - P2.getx()) * (P1.getx() - P2.getx());
        res += (P1.gety() - P2.gety()) * (P1.gety() - P2.gety());
        res += (P1.getz() - P2.getz()) * (P1.getz() - P2.getz());
        res = Math.sqrt(res);

        return (int) res;
    }

    public void display3D(Object p) {
        threeDimPoint P = (threeDimPoint) p;

        System.out.println(P.getx() + " " + P.gety() + " " + P.getz());
    }

    public int scalarProduct3D(Object p1, Object p2) {
        int res = 0;

        threeDimPoint P1 = (threeDimPoint) p1;
        threeDimPoint P2 = (threeDimPoint) p2;

        res += P1.getx() * P2.getx();
        res += P1.gety() * P2.gety();
        res += P1.getz() * P2.getz();

        return res;
    }
}
