import Interfaces.twoDimAlgebra;
import Interfaces.threeDimAlgebra;
import Points.twoDimPoint;
import Points.threeDimPoint;
import Implementation.AllAlgebraStd;
import Implementation.AllAlgebraCustom;

public class Interfaces {
    public static void main(String[] args) throws Exception {
        twoDimPoint a2D = new twoDimPoint();
        twoDimPoint b2D = new twoDimPoint();

        threeDimPoint a3D = new threeDimPoint();
        threeDimPoint b3D = new threeDimPoint();




        a2D.setx(2);
        a2D.sety(3);

        b2D.setx(12);
        b2D.sety(30);


        a3D.setx(12);
        a3D.sety(7);
        a3D.setz(3);



        b3D.setx(4);
        b3D.sety(23);
        b3D.setz(8);


        System.out.println("2D stuff!");
        twoDimAlgebra Algebra1 = new AllAlgebraStd();
        twoDimAlgebra Algebra2 = new AllAlgebraCustom();


        System.out.println(Algebra1.distance2D(a2D, b2D));
        System.out.println(Algebra2.distance2D(a2D, b2D));

        Algebra1.display2D(a2D);
        Algebra1.display2D(b2D);
        Algebra2.display2D(a2D);
        Algebra2.display2D(b2D);

        System.out.println(Algebra1.scalarProduct2D(a2D, b2D));
        System.out.println(Algebra2.scalarProduct2D(a2D, b2D));





        System.out.println("3D stuff!");
        threeDimAlgebra Algebra3 = (threeDimAlgebra) Algebra1;
        threeDimAlgebra Algebra4 = (threeDimAlgebra) Algebra2;

        System.out.println(Algebra3.distance3D(a3D, b3D));
        System.out.println(Algebra4.distance3D(a3D, b3D));

        Algebra3.display3D(a3D);
        Algebra3.display3D(b3D);
        Algebra4.display3D(a3D);
        Algebra4.display3D(b3D);

        System.out.println(Algebra3.scalarProduct3D(a3D, b3D));
        System.out.println(Algebra4.scalarProduct3D(a3D, b3D));

    }
}
