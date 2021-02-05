package wcyoung.storage.instance.classes;

public class Lunch {

    private Dinner dinner;

    public Lunch(Dinner dinner) {
        this.dinner = dinner;
    }

    public Dinner getDinner() {
        return dinner;
    }

}
