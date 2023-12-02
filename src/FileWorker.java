import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class FileWorker {
    public static void main(String[] args){
        try {
            final Path ROOT_DIRECTORY = Paths.get("C:\\java_programming\\LearningFilesAPI\\src");
            Path directory = createDirectory(ROOT_DIRECTORY, "MyDirectory");

            Path file = createFile(directory);

            writeDataToFile(file, "Hello!\nThis is a trial file\nHere is some test text in a file");

            readDataFromFile(file);

            Path newDirectory = createDirectory(ROOT_DIRECTORY, "NewDirectory");
            copyFile(file, newDirectory);

            getFileTree(ROOT_DIRECTORY);

            deleteDirectory(newDirectory);
            deleteDirectory(directory);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static Path createDirectory(Path path, String directoryName) throws IOException {
        Path directory = path.resolve(directoryName);
        if (Files.exists(directory) && Files.isDirectory(directory)) {
            System.out.println("Directory already exists");
            return directory;
        }
        Files.createDirectory(directory);
        System.out.println("Directory was created");
        return directory;
    }

    public static Path createFile(Path path) throws IOException{
        final String FILE = "MyFile.txt";
        Path file = path.resolve(FILE);
        if (Files.exists(file) && Files.isRegularFile(file)) {
            System.out.println("File already exists");
            return file;
        }
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
        System.out.println("\nData from the file:");
        for (String line: lines) {
            System.out.println(line);
        }
        System.out.println();
    }

    public static void copyFile(Path initPath, Path finalPath){
        try {
            Files.copy(initPath, finalPath.resolve("CopiedFile.txt"));
            System.out.println("File was copied");
        } catch (FileAlreadyExistsException e) {
            System.out.println("Destination file already exists");
        }catch (IOException e){
            System.out.println("Error occurred while copying the file " + e.getMessage());
        }
    }

    public static void getFileTree(Path path){
        if (!Files.exists(path)){
            System.out.println("File doesn't exist!");
            return;
        }
        if (!Files.isDirectory(path)){
            System.out.println("This is not a directory!");
            return;
        }
        try (Stream<Path> files = Files.walk(path)){
            System.out.println("\nContent of directory " + path + " is:");
            files.filter((x) -> x.getFileName().toString().endsWith("txt"))
                            .forEach(System.out::println);
            System.out.println();
        }catch (IOException e){
            System.out.println("Error occurred while retrieving directory content: " + e.getMessage());
        }
    }

    public static void deleteFile(Path path) throws IOException {
        if (!Files.exists(path)){
            System.out.println("File doesn't exist!");
            return;
        }
        if (!Files.isRegularFile(path)){
            System.out.println("This is not a file!");
            return;
        }
        Files.delete(path);
        System.out.println("File " + path + " deleted");
    }

    public static void deleteDirectory(Path path) throws IOException{
        if (!Files.exists(path)){
            System.out.println("File doesn't exist!");
            return;
        }
        if (!Files.isDirectory(path)){
            System.out.println("This is not a directory!");
            return;
        }
        List<Path> filesList = new ArrayList<>();
        try(Stream<Path> files = Files.walk(path)){
            filesList = files.toList();
        }catch (IOException e){
            System.out.println("Error occurred while retrieving directory content: " + e.getMessage());
        }
        if(filesList.isEmpty()){
            return;
        }
        for (Path path1: filesList) {
            if(path1.equals(path))
                continue;
            if(Files.isDirectory(path1))
                deleteDirectory(path1);
            else
                deleteFile(path1);
        }
        Files.delete(path);
        System.out.println("Directory " + path + " deleted");
    }

}
