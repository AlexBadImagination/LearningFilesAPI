import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileWorker {
    public static void main(String[] args){
        try {
            final Path ROOT_DIRECTORY = Paths.get("C:\\java_programming\\Learning Files API\\src");
            Path directory = createDirectory(ROOT_DIRECTORY, "MyDirectory");

            Path file = createFile(directory);

            writeDataToFile(file, "Hello!\nThis is a trial file\nHere is some test text in a file");

            readDataFromFile(file);

            Path newDirectory = createDirectory(ROOT_DIRECTORY, "NewDirectory");
            copyFile(file, newDirectory);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static Path createDirectory(Path path, String directoryName) throws IOException {
        Path directory = Paths.get(path.toString() + "\\" + directoryName);
        Files.createDirectory(directory);
        System.out.println("Directory was created");
        return directory;
    }

    public static Path createFile(Path path) throws IOException{
        final String FILE = "MyFile.txt";
        Path file = Paths.get(path.toString() + "\\" + FILE);
        Files.createFile(file);
        System.out.println("File was created");
        return file;
    }

    public static void writeDataToFile(Path file, String data) throws IOException{
        Files.write(file, data.getBytes());
        System.out.println("Data has been written to the file");
    }

    public static void readDataFromFile(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        System.out.println("Data from the file:\n");
        for (String line: lines) {
            System.out.println(line);
        }
        System.out.println();
    }

    public static void copyFile(Path initPath, Path finalPath) throws IOException{
        Files.copy(initPath, Paths.get(finalPath.toString() + "\\" + "CopiedFile.txt"),REPLACE_EXISTING);
        System.out.println("File was copied");
    }
}
