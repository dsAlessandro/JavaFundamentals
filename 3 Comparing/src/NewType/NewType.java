package NewType;



public class NewType {
    public int x, y, z;

    public NewType(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }


    public static int sum2(NewType t) {
        return t.x + t.y + t.z;
    }

    public int sum() {
        return this.x + this.y + this.z;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")";
    }
}
