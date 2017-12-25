import java.util.List;
import java.util.Objects;

public class Dog {
    private String nameD;
    private List<Cat> enemies;

    public Dog() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog)) return false;
        Dog dog = (Dog) o;
        return Objects.equals(getNameD(), dog.getNameD()) &&
                Objects.equals(getEnemies(), dog.getEnemies());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getNameD(), getEnemies());
    }

    public Dog(String nameD, List<Cat> enemies) {
        this.nameD = nameD;
        this.enemies = enemies;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public String toString() {
        return "Dog{" +
                "nameD='" + nameD + '\'' +
                ", enemies=" + enemies +
                '}';
    }

    public String getNameD() {
        return nameD;
    }

    public void setNameD(String name) {
        this.nameD = name;
    }

    public List<Cat> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Cat> enemies) {
        this.enemies = enemies;
    }
}
