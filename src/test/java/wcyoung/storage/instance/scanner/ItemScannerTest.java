package wcyoung.storage.instance.scanner;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.classes.ClassA;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ItemScannerTest {

    @Test
    void scan() {
        ItemScanner scanner = new ItemScanner("wcyoung.storage.instance.classes");
        Set<Class<?>> classes = scanner.scan();

        Set<Class<?>> expectedClasses = new HashSet<>();
        expectedClasses.add(ClassA.class);

        assertEquals(expectedClasses, classes);
    }

    @Test
    void scanFail() {
        ItemScanner scanner = new ItemScanner("nothing.package");
        Set<Class<?>> classes = scanner.scan();

        assertNull(classes);
    }

}
