package hw.jschool;

import java.io.*;
import java.nio.file.Path;

public class Main {
/**********************************************************************************************************************/
    public static void main(String[] args) throws UncheckedIOException,IOException {
	    Path p = CreateFile.getPathTestFile();
        try {
            CreateFile.test();
        }
	    catch (Exception e){
            p=  Path.of("test.txt");
       }

        ParserClass parserClass = new ParserClass(p);
        //System.out.println("\n\nAllArrayWorlds  :"+parserClass.getAllArrayWorldsl().subList(1,20));
        parserClass.allSetWorlds();
        parserClass.sortWorlds();
        parserClass.repeatWordCount();
        parserClass.reverseArrayLines();;
        parserClass.reverseIteratorForLIst();
        parserClass.getString();

    }

/**********************************************************************************************************************/
}
