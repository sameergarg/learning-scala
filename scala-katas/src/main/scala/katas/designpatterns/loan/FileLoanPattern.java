package katas.designpatterns.loan;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class FileLoanPattern {

    static Stream<String> linesOfTempFile() throws IOException {
        File tempFile = File.createTempFile("hello", "txt");
        return Files.lines(tempFile.toPath());
    }

    //lender
    public boolean checkFile(Predicate<Stream<String>> block) throws IOException {
        Stream<String> lines = linesOfTempFile();
        boolean result = block.test(lines);
        lines.close();
        return result;
    }

    //another lender
    public void useFile(Consumer<Stream<String>> block) throws IOException {
        Stream<String> lines = linesOfTempFile();
        block.accept(lines);
        lines.close();
    }

    //lendee
    public boolean isEmpty() throws IOException {

        boolean result = checkFile(lines -> {
            if (lines.count() == 0) {
                System.out.println("File is empty");
                return true;
            } else {
                System.out.println("File has contents");
                return false;
            }
        });

        return result;
    }

}
