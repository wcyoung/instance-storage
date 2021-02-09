package wcyoung.storage.instance.generator;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.annotation.Inject;
import wcyoung.storage.instance.classes.AbstractClassA;
import wcyoung.storage.instance.classes.ClassA;
import wcyoung.storage.instance.classes.InterfaceA;

import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class InstanceGeneratorTest {

    @Test
    void generateWithClassWithDefaultConstructor() {
        ClassA classA = InstanceGenerator.generate(ClassA.class);
        assertNotNull(classA);
    }

    @Test
    void generateWithClassWithSingleConstructor() {
        CaseA caseA = InstanceGenerator.generate(CaseA.class);
        assertNotNull(caseA);
    }

    @Test
    void generateWithClassWithSingleConstructorWithInject() {
        CaseB caseB = InstanceGenerator.generate(CaseB.class);
        assertNotNull(caseB);
    }

    @Test
    void generateWithClassWithConstructorWithInject() {
        String parameter = "parameter";
        CaseC caseC = InstanceGenerator.generate(CaseC.class, parameter);
        assertNotNull(caseC);
        assertEquals(parameter, caseC.getString());
    }

    @Test
    void generateFailBecauseClassIsNull() {
        assertThrows(NullPointerException.class, () -> InstanceGenerator.generate((Class<?>) null));
    }

    @Test
    void generateFailBecauseClassIsInterface() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.generate(InterfaceA.class));
    }

    @Test
    void generateFailBecauseClassIsAbstract() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.generate(AbstractClassA.class));
    }

    @Test
    void generateFailBecauseIllegalArgument() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.generate(CaseC.class));
    }

    @Test
    void generateWithDefaultConstructor() {
        Constructor<?> constructor = InstanceGenerator.findConstructor(ClassA.class);
        ClassA classA = InstanceGenerator.generate(constructor);
        assertNotNull(classA);
    }

    @Test
    void generateWithSingleConstructor() {
        Constructor<?> constructor = InstanceGenerator.findConstructor(CaseA.class);
        CaseA caseA = InstanceGenerator.generate(constructor);
        assertNotNull(caseA);
    }

    @Test
    void generateWithSingleConstructorWithInject() {
        Constructor<?> constructor = InstanceGenerator.findConstructor(CaseB.class);
        CaseB caseB = InstanceGenerator.generate(constructor);
        assertNotNull(caseB);
    }

    @Test
    void generateWithConstructorWithInject() {
        String parameter = "parameter";
        Constructor<?> constructor = InstanceGenerator.findConstructor(CaseC.class);
        CaseC caseC = InstanceGenerator.generate(constructor, parameter);
        assertNotNull(caseC);
        assertEquals(parameter, caseC.getString());
    }

    @Test
    void generateWithConstructorFailBecauseConstructorIsNull() {
        assertThrows(NullPointerException.class, () -> InstanceGenerator.generate((Constructor<?>) null));
    }

    @Test
    void generateWithConstructorFailBecauseIllegalArgument() {
        assertThrows(InstanceGenerateException.class, () -> {
            Constructor<?> constructor = InstanceGenerator.findConstructor(CaseC.class);
            InstanceGenerator.generate(constructor);
        });
    }

    @Test
    void findDefaultConstructor() {
        assertNotNull(InstanceGenerator.findConstructor(ClassA.class));
    }

    @Test
    void findSingleConstructor() {
        assertNotNull(InstanceGenerator.findConstructor(CaseA.class));
    }

    @Test
    void findSingleConstructorWithInject() {
        assertNotNull(InstanceGenerator.findConstructor(CaseB.class));
    }

    @Test
    void findConstructorWithInject() {
        assertNotNull(InstanceGenerator.findConstructor(CaseC.class));
    }

    @Test
    void findConstructorFailBecauseClassIsNull() {
        assertThrows(NullPointerException.class, () -> InstanceGenerator.findConstructor(null));
    }

    @Test
    void findConstructorFailBecauseClassIsInterface() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.findConstructor(InterfaceA.class));
    }

    @Test
    void findConstructorFailBecauseClassIsAbstract() {
        assertThrows(InstanceGenerateException.class, () -> InstanceGenerator.findConstructor(AbstractClassA.class));
    }

    @Test
    void findConstructorFailBecauseItDoesNotHavePublic() {
        assertThrows(ConstructorNotFoundException.class, () ->
                InstanceGenerator.findConstructor(ConstructorNotFoundCaseA.class));
    }

    @Test
    void findConstructorFailBecauseItDoesNotHavePublicWithInject() {
        assertThrows(ConstructorNotFoundException.class, () ->
                InstanceGenerator.findConstructor(ConstructorNotFoundCaseB.class));
    }

    @Test
    void findConstructorFailBecauseTooManyPublicWithInject() {
        assertThrows(ConstructorNotFoundException.class, () ->
                InstanceGenerator.findConstructor(ConstructorNotFoundCaseC.class));
    }

}

class CaseA {
    public CaseA() {}
}

class CaseB {
    @Inject
    public CaseB() {}
}

class CaseC {

    private String string;

    public CaseC() {}

    @Inject
    public CaseC(String string) {
        this.string = string;
    }

    public String getString() {
        return string;
    }

}

class ConstructorNotFoundCaseA {
    private ConstructorNotFoundCaseA() {}
}

class ConstructorNotFoundCaseB {
    public ConstructorNotFoundCaseB() {}
    public ConstructorNotFoundCaseB(String string) {}
}

class ConstructorNotFoundCaseC {
    @Inject
    public ConstructorNotFoundCaseC() {}
    @Inject
    public ConstructorNotFoundCaseC(String string) {}
}
