import org.junit.Test;

public class Mouse {
    private int age;
    private String name;

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

    @Override
    public String toString() {
        return "Mouse{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }
}
