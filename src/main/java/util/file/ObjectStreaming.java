package util.file;

import java.io.*;

public class ObjectStreaming {
    public static void save(final Object obj, final File file) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(fos);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }

        try {
            oos.writeObject(obj);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }

        try {
            oos.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static <U> U load(final File file) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(fis);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }

        U u = null;
        try {
            //noinspection unchecked
            u = (U) ois.readObject();
        } catch (IOException | ClassNotFoundException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }

        try {
            ois.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
            throw new RuntimeException();
        }

        return u;
    }
}
