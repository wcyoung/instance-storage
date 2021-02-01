package wcyoung.storage.instance.collector;

import org.junit.jupiter.api.Test;
import wcyoung.storage.instance.scanner.ClassScanner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ClassesDependencyCollectorTest {

    @Test
    void collect() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DependencyClassA.class);

        ClassesDependencyCollector collector = new ClassesDependencyCollector(classes);
        Map<Class<?>, List<Class<?>>> dependencies = collector.collect();

        List<Class<?>> expectedClasses = new ArrayList<>();
        expectedClasses.add(DependencyClassB.class);
        expectedClasses.add(DependencyClassC.class);

        assertEquals(expectedClasses, dependencies.get(DependencyClassA.class));
    }

    @Test
    void collectWithScanner() {
        ClassScanner<Set<Class<?>>> scanner = new ClassScanner<Set<Class<?>>>() {
            @Override
            public Set<Class<?>> scan() {
                Set<Class<?>> classes = new HashSet<>();
                classes.add(DependencyClassA.class);
                return classes;
            }
        };

        ClassesDependencyCollector collector = new ClassesDependencyCollector(scanner);
        Map<Class<?>, List<Class<?>>> dependencies = collector.collect();

        List<Class<?>> expectedClasses = new ArrayList<>();
        expectedClasses.add(DependencyClassB.class);
        expectedClasses.add(DependencyClassC.class);

        assertEquals(expectedClasses, dependencies.get(DependencyClassA.class));
    }

    @Test
    void collectFailBecauseClassesNull() {
        ClassesDependencyCollector collector = new ClassesDependencyCollector((Set<Class<?>>) null);
        Map<Class<?>, List<Class<?>>> dependencies = collector.collect();

        assertNull(dependencies);
    }

    @Test
    void collectFailBecauseScannerNull() {
        ClassesDependencyCollector collector = new ClassesDependencyCollector((ClassScanner<Set<Class<?>>>) null);
        Map<Class<?>, List<Class<?>>> dependencies = collector.collect();

        assertNull(dependencies);
    }

    @Test
    void collectFailBecauseDuplicateDependencies() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(DependencyClassB.class);

        ClassesDependencyCollector collector = new ClassesDependencyCollector(classes);
        assertThrows(DependencyCollectException.class, collector::collect);
    }

}

class DependencyClassA {

    private DependencyClassB dependencyClassB;
    private DependencyClassC dependencyClassC;

    public DependencyClassA(DependencyClassB dependencyClassB, DependencyClassC dependencyClassC) {
        this.dependencyClassB = dependencyClassB;
        this.dependencyClassC = dependencyClassC;
    }

    public DependencyClassB getDependencyClassB() {
        return dependencyClassB;
    }
    public DependencyClassC getDependencyClassC() {
        return dependencyClassC;
    }

}

class DependencyClassB {

    private DependencyClassC dependencyClassC1;
    private DependencyClassC dependencyClassC2;

    public DependencyClassB(DependencyClassC dependencyClassC1, DependencyClassC dependencyClassC2) {
        this.dependencyClassC1 = dependencyClassC1;
        this.dependencyClassC2 = dependencyClassC2;
    }

    public DependencyClassC getDependencyClassC1() {
        return dependencyClassC1;
    }
    public DependencyClassC getDependencyClassC2() {
        return dependencyClassC2;
    }

}

class DependencyClassC {}
