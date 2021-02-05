package wcyoung.storage.instance.classes;

public class Student {

    private MechanicalPencil mechanicalPencil;
    private Eraser eraser;

    public Student(MechanicalPencil mechanicalPencil, Eraser eraser) {
        this.mechanicalPencil = mechanicalPencil;
        this.eraser = eraser;
    }

    public MechanicalPencil getMechanicalPencil() {
        return mechanicalPencil;
    }
    public Eraser getEraser() {
        return eraser;
    }

}
