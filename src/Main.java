import sources.Animal;
import sources.Serialize;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, IllegalAccessException, NoSuchFieldException, InstantiationException {
        Animal monkey = new Animal();
        monkey.setType("monkey");
        monkey.setName("Todd");
        monkey.setAge(2);
        Serialize.serialize(monkey);
        Animal t;
        t = Serialize.deserialize("D:\\saves\\info1" +".txt", Animal.class);
        System.out.println(t.getType() + ", " + t.getName() + ", " + t.getAge());
    }
}
