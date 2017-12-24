import java.util.List;
import java.util.Objects;

public class Dog {
    private String nameD;
   private List<String> enemies;

    public Dog() {
    }

    public Dog(String nameD, List<String> enemies) {

        this.nameD = nameD;
        this.enemies = enemies;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "nameD='" + nameD + '\'' +
                ", enemies=" + enemies +
                '}';
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

    public String getNameD() {
        return nameD;
    }

    public void setNameD(String name) {
        this.nameD = name;
    }

    public List<String> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<String> enemies) {
        this.enemies = enemies;
    }
}
