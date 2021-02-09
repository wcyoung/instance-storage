package wcyoung.storage.instance.scanner;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.annotation.Item;
import wcyoung.storage.instance.classes.ClassA;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AnnotatedClassScannerTest {

    @Test
    void scan() {
        AnnotatedClassScanner scanner = new AnnotatedClassScanner(
                "wcyoung.storage.instance.classes", Item.class);
        Set<Class<?>> classes = scanner.scan();

        Set<Class<?>> expectedClasses = new HashSet<>();
        expectedClasses.add(ClassA.class);

        assertEquals(expectedClasses, classes);
    }

    @Test
    void scanFail() {
        AnnotatedClassScanner scanner = new AnnotatedClassScanner("nothing.package", Item.class);
        Set<Class<?>> classes = scanner.scan();

        assertNull(classes);
    }

    @Test
    void scanFailBecauseBasePackageIsNull() {
        AnnotatedClassScanner scanner = new AnnotatedClassScanner(null, Item.class);
        Set<Class<?>> classes = scanner.scan();

        assertNull(classes);
    }

    @Test
    void scanFailBecauseAnnotationTypeIsNull() {
        AnnotatedClassScanner scanner = new AnnotatedClassScanner("wcyoung.storage.instance.classes", null);
        Set<Class<?>> classes = scanner.scan();

        assertNull(classes);
    }

}
