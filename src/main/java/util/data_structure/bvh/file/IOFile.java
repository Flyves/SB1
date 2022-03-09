package util.data_structure.bvh.file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class IOFile {

    public static Optional<List<String>> inputStreamAsStringList(InputStream inputStream) {
        List<String> fileContent = new ArrayList<>();

        try {
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            Scanner myReader = new Scanner(bufferedReader);
            while (myReader.hasNextLine()) {
                fileContent.add(myReader.nextLine());
            }
            myReader.close();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return Optional.empty();
        }

        return Optional.of(fileContent);
    }

    public static Optional<InputStream> fileToInputStream(final File file) {
        try {
            return Optional.of(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<List<String>> getFileContent(final File file) {
        return fileToInputStream(file).flatMap(IOFile::inputStreamAsStringList);
    }
}
