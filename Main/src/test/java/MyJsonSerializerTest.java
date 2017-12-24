import com.main.acad.serializator.JsonSerializer;
import com.main.acad.serializator.MyJsonSerializer;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MyJsonSerializerTest {

    JsonSerializer serializer;

    @Before
    public void setUp() {
        serializer = new MyJsonSerializer();
    }

    @Test
    public void testWriteCat() throws IllegalAccessException {
        Cat cat = new Cat();
        cat.setName("Cat");
        cat.setAge(10);
        cat.setHungry(true);
        ArrayList list =new ArrayList();
        cat.setEnemy(new Dog("Dog1", list));
        String expectedResult = "{\"name\":\"Cat\",\"age\":10,\"isHungry\":true,\"enemy\":{\"nameD\":\"Dog1\",\"enemies\":[]}}";
        String actualResult = serializer.write(cat);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWriteMouse() throws IllegalAccessException {
        Mouse mouse = new Mouse();
        mouse.setAge(10);
        mouse.setName("mouse");
        String expectedResult = "{\"age\":10,\"name\":\"mouse\"}";
        String actualResult = serializer.write(mouse);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWriteDog() throws IllegalAccessException {
        Cat cat = new Cat();
        cat.setName("Cat");
        cat.setAge(10);
        cat.setHungry(true);
          cat.setEnemy(null);
        ArrayList list =new ArrayList();
        list.add(cat);
         Dog dog = new Dog("Dog", list);
        String expectedResult = "{\"nameD\":\"Dog\",\"enemies\":[{\"name\":\"Cat\",\"age\":10,\"isHungry\":true,\"enemy\":null}]}";
            String actualResult = serializer.write(dog);
             assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testWriteListOfCats() throws IllegalAccessException {
        Cat cat1 = new Cat();
        cat1.setName("Cat1");
        cat1.setAge(10);
        cat1.setEnemy(new Dog("Dog1", new ArrayList()));
        cat1.setHungry(true);
        Cat cat2 = new Cat();
        cat2.setName("Cat2");
        cat2.setAge(5);
        cat2.setHungry(false);
        cat2.setEnemy(new Dog("Dog2", new ArrayList()));
        String expectedResult = "[{\"name\":\"Cat1\",\"age\":10,\"isHungry\":true,\"enemy\":{\"nameD\":\"Dog1\",\"enemies\":[]}}" +
                ",{\"name\":\"Cat2\",\"age\":5,\"isHungry\":false,\"enemy\":{\"nameD\":\"Dog2\",\"enemies\":[]}}]";
        String actualResult = serializer.write(Arrays.asList(cat1, cat2));
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void testReadCat() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ArrayList<String> list = new ArrayList<>();
        list.add("saf");
        list.add("saf");
        Dog dog = new Dog();
        dog.setNameD("sas");
        dog.setEnemies(list);
        Cat cat = new Cat();
        cat.setName("Cat");
        cat.setAge(10);
        cat.setHungry(true);
        cat.setEnemy(dog);
        String expectedResult = "{\"name\":\"Cat\",\"age\":10,\"isHungry\":true,\"enemy\":{\"nameD\":\"sas\",\"enemies\":[{\"saf\",\"saf\"}]}}\"";
        Object actualResult = serializer.read(expectedResult, Class.forName("Cat"));
        System.out.println(actualResult);
        System.out.println(cat.toString());
        assertEquals(cat, actualResult);
    }

    @Test
    public void testReadList() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        List<String> list = new ArrayList<>();
        list.add("sasha");
        list.add("bogdan");
        list.add("bogda");
        String expectedResult = "[{\"sasha\",\"bogdan\",\"bogda\"}]";
        ArrayList actualResult = (ArrayList) serializer.read(expectedResult, list.getClass());
        assertEquals(list, actualResult);
    }

    @Test
    public void testReadObjectInList() throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        List<Object> list = new ArrayList();
        Cat cat = new Cat();
        cat.setName("papa");
        cat.setAge(15);
        list.add("aaa");
        list.add(cat);
        String expectedResult = "{ \"papa\",{\"name\":\"papa\",\"age\":\15}}";
        ArrayList actualResult = (ArrayList) serializer.read(expectedResult, list.getClass());
        assertEquals(list, actualResult);
    }
}