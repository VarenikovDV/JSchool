package hw.jschool;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TempClass {
/*    private static List<Path> resolvePaths(List<Path> inputDirs, List<InputFile> inputFiles) throws IOException {
        List<Path> ret ;//= new ArrayList<>(inputFiles.size());

        for (InputFile inputFile : inputFiles) {
            boolean found = false;

            for (Path inputDir : inputDirs) {
                try (Stream<Path> matches = Files.find(inputDir, Integer.MAX_VALUE, (path, attr) -> inputFile.equals(path), FileVisitOption.FOLLOW_LINKS)) {
                    Path file = matches.findFirst().orElse(null);

                    if (file != null) {
                        ret.add(file);
                        found = true;
                        break;
                    }
                } catch (UncheckedIOException e) {
                    throw e.getCause();
                }
            }

            if (!found) throw new IOException("can't find input "+inputFile.getFileName());
        }
/
        return ret;
    }*/
}
