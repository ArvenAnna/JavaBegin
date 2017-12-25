import java.util.Objects;

public class Mouse {
    private int age;
    private String name;

    public Mouse(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mouse)) return false;
        Mouse mouse = (Mouse) o;
        return getAge() == mouse.getAge() &&
                Objects.equals(getName(), mouse.getName());
    }

    @Override
    public int hashCode() {

        return Objects.hash(getAge(), getName());
    }

    public Mouse() {

    }

    @Override
    public String toString() {
        return "Mouse{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {

        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}
