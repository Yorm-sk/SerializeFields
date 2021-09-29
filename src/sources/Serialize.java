package sources;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class Serialize {
    public static void serialize(Object o) throws IOException, IllegalAccessException {
        Class <?> cls = o.getClass();
        try (FileWriter fw = new FileWriter("D:\\saves\\info" +".txt")) {
            Field[] fields = cls.getDeclaredFields();
            for (Field f : fields) {
                f.setAccessible(true);
                if (f.isAnnotationPresent(Save.class)) {
                    fw.write(f.getName() + "=");

                    if (f.getType() == String.class) fw.write(f.get(o) + "\n");
                    else fw.write(f.getInt(o) + "\n");
                }
            }
        }
    }

    public static <T> T deserialize(String path, Class <T> cls) throws IOException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        T res = cls.newInstance();
        ArrayList <String> values= new ArrayList<>();
        try (FileReader fr = new FileReader(path)){
            try(BufferedReader bf = new BufferedReader(fr)){
                String line = bf.readLine();
                while (line != null){
                    values.add(line);
                    line = bf.readLine();
                }
            }
        }
        for (String s: values){
            String [] nv = s.split("=");
            if (nv.length != 2) throw new RuntimeException("Too many parameters in line!");

            String name = nv[0];
            String value = nv[1];
            Field f = cls.getDeclaredField(name);
            if (Modifier.isPrivate(f.getModifiers())){
                f.setAccessible(true);
            }

            if (f.isAnnotationPresent(Save.class)){
                if (f.getType() == int.class) f.setInt(res, Integer.parseInt(value));
                else f.set(res, value);
            }
        }
        return res;
    }
}
