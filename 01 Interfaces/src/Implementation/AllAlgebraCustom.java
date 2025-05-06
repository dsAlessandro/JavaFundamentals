package Implementation;

import Interfaces.twoDimAlgebra;
import Interfaces.threeDimAlgebra;
import Points.twoDimPoint;
import Points.threeDimPoint;
import java.lang.Math;


public class AllAlgebraCustom implements twoDimAlgebra, threeDimAlgebra {
    public int distance2D(Object p1, Object p2) {
        return 1;
    }

    public void display2D(Object p) {
        System.out.println("Here the concept of position is irrelevant!");
    }

    public int scalarProduct2D(Object p1, Object p2) {
        return 69;
    }

    public int distance3D(Object p1, Object p2) {
        return 1;
    }

    public void display3D(Object p) {
        System.out.println("Here the concept of position is irrelevant!");
    }

    public int scalarProduct3D(Object p1, Object p2) {
        return 420;
    }
}
