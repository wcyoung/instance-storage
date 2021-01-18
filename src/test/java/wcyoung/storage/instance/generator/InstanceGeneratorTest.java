package wcyoung.storage.instance.generator;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.classes.AbstractClassA;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.classes.InterfaceA;

import static org.junit.jupiter.api.Assertions.*;

class InstanceGeneratorTest {

    @Test
    void generate() {
        ClassA classA = InstanceGenerator.generate(ClassA.class);
        assertNotNull(classA);
    }

    @Test
    void generateFailBecauseInterface() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.generate(InterfaceA.class));
    }

    @Test
    void generateFailBecauseAbstract() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.generate(AbstractClassA.class));
    }

}
