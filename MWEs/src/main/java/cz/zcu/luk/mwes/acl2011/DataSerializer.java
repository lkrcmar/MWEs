package cz.zcu.luk.mwes.acl2011;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: Lukr
 * Date: 13.4.13
 * Time: 11:53
 * To change this template use File | Settings | File Templates.
 */
public class DataSerializer {

    public static void serializeData(Object data, String dataFN) {
        String filePathString = Config.TASK_DATA_DIR + "data/" + dataFN + ".data";

        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePathString);
            ObjectOutput oos = new ObjectOutputStream(fos);
            oos.writeObject(data);
            oos.close();
            System.out.println("Serialized: " + dataFN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static Object deserialiazeData(String dataFN) {
        String filePathString = Config.TASK_DATA_DIR + "data/" + dataFN + ".data";

        // if file does not exist, return null..
        if (!(new File(filePathString).exists())) return null;

        FileInputStream fis;
        Object data = null;
        try {
            fis = new FileInputStream(filePathString);
            ObjectInputStream ois = new ObjectInputStream(fis);
            data = ois.readObject();
            ois.close();
            System.out.println("Deserialized: " + dataFN);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return data;
    }
}
