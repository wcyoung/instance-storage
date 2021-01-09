package wcyoung.storage.instance.loader;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.Stored;
import wcyoung.storage.instance.target.*;

import static org.junit.jupiter.api.Assertions.*;

class AnnotatedClassScannerTest {

    @Test
    void scan() {
        Class<?>[] expectedClasses = {ClassA.class, ClassB.class, ClassC.class, ClassD.class, ClassE.class};
        Class<?>[] scannedClasses = AnnotatedClassScanner.scan("wcyoung.storage.instance.target", Stored.class);
        assertArrayEquals(expectedClasses, scannedClasses);
    }

}
