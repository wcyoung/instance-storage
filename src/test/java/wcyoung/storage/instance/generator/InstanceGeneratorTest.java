package wcyoung.storage.instance.generator;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.classes.ClassA;

import static org.junit.jupiter.api.Assertions.*;

class InstanceGeneratorTest {

    @Test
    void generate() {
        ClassA classA = InstanceGenerator.generate(ClassA.class);
        assertNotNull(classA);
    }

}
