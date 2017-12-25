import java.util.Objects;

public class Cat {
    private String name;
    private int age;
    private boolean isHungry;
    private Dog enemy;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHungry() {
        return isHungry;
    }

    public void setHungry(boolean hungry) {
        isHungry = hungry;
    }

    @Override
    public String toString() {
        return "Cat{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isHungry=" + isHungry +
                ", enemy=" + enemy +
                '}';
    }

    public Dog getEnemy() {
        return enemy;
    }

    public void setEnemy(Dog enemy) {
        this.enemy = enemy;
    }

    public double getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cat)) return false;
        Cat cat = (Cat) o;
        return getAge() == cat.getAge() &&
                isHungry() == cat.isHungry() &&
                Objects.equals(getName(), cat.getName()) &&
                Objects.equals(getEnemy(), cat.getEnemy());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getName(), getAge(), isHungry(), getEnemy());
    }
}
