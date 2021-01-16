package wcyoung.storage.instance.scanner;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.annotation.Item;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.classes.ClassB;
import wcyoung.storage.instance.classes.ClassC;
import wcyoung.storage.instance.classes.ClassD;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnnotatedClassScannerTest {

    @Test
    void scan() {
        AnnotatedClassScanner scanner = new AnnotatedClassScanner(
                "wcyoung.storage.instance.classes", Item.class);
        Set<Class<?>> classes = scanner.scan();

        Class<?>[] expectedClasses = {ClassA.class, ClassB.class, ClassC.class, ClassD.class};
        assertArrayEquals(expectedClasses, classes.toArray(new Class<?>[0]));
    }

}
