
import com.binlee.annotation.*;

public class Test {

    @Gender
    private int mGender;

    public static void main(String[] args) {
        //  javac -cp apt/dist/apt.jar Test.java
        System.out.println(java.util.Arrays.toString(args));
    }
}