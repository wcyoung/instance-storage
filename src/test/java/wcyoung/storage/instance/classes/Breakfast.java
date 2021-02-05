package wcyoung.storage.instance.classes;

public class Breakfast {

    private Lunch lunch;

    public Breakfast(Lunch lunch) {
        this.lunch = lunch;
    }

    public Lunch getLunch() {
        return lunch;
    }

}
