import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

public class Whizlab {

    int j = 10;

    public static void main(String args[]) {
        int x = 9, y = 3;
        int z = (x + y / y) * y;

        System.out.print(z);
    }

    boolean go(int y) {
        if (y > 5)
            return true;
        else
            return false;
    }
}

class Employer {
    Employer(String s, int i) {
        name = s;
        age = i;
    }

    String name;
    int age;
}