package hw.jschool;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class CreateFile {

    private static final Path  javaHomeDir  = Path.of(System.getenv("JAVA_HOME"));
    private static final Path  javaSRC      = Path.of("src.zip");
    private static final Path  testFile1    = Path.of("C:\\test1.txt");
    private static final Path  testFile2    = Path.of("C:\\test2.txt");
    private static final Path  testFile3    = Path.of("C:\\test3.txt");
    private static final Path  timeLogFile  = Path.of("C:\\timelog.txt");
    private static Integer countFile = 0;
/**********************************************************************************************************************/
    public static <T> void println(T a){
        System.out.println(a);
    }
/**********************************************************************************************************************/

/**********************************************************************************************************************/
    public static  Path getPathTestFile(){return testFile1;};
/**********************************************************************************************************************/
    public static void test() throws UncheckedIOException,IOException {
        // Home work  by the lecture four
        // All tasks are done in one project
        logTime(timeLogFile,"***************************************************",0);

        long start = System.currentTimeMillis();
        createTestFile1();
        logTime(timeLogFile,"createTestFile1",System.currentTimeMillis()-start);
        println("************************************************************************************************************************");

        start = System.currentTimeMillis();
        createTestFile2();
        logTime(timeLogFile,"createTestFile2",System.currentTimeMillis()-start);
        println("************************************************************************************************************************");

        start = System.currentTimeMillis();
        createTestFile3();
        logTime(timeLogFile,"createTestFile3",System.currentTimeMillis()-start);
        println("************************************************************************************************************************");

        logTime(timeLogFile,"***************************************************",0);
    }
    /**********************************************************************************************************************/
    public static Path findAbsPath(Path pathFrom,Path fileName) throws UncheckedIOException,IOException {

        println("   ************************************************************************************************************************");
        println("   // найдем абсолютный путь к src.zip");
        println("   // искать будем в %JAVA_HOME%");
        println("   ---------------------------------------------------------");
        try (Stream<Path> streamPath =  Files.walk(pathFrom, 20, FileVisitOption.FOLLOW_LINKS)) {
            //Optional<Path> path = streamPath.filter(p->{if(!Files.isDirectory(p)&& p.getFileName().compareTo(fileName)==0 ) {return true;}  else {return false;}})
            Optional<Path> path = streamPath.filter(p->{if( p.getFileName()!=null &&
                                                            !Files.isDirectory(p) &&
                                                            p.getFileName().compareTo(fileName)==0 ) {return true;}  else {return false;}})

                    .findFirst();
            if(path.isEmpty()) {throw new FileNotFoundException();}
            Path absPathSRC = path.get().toAbsolutePath();
            println("   Нашли:");
            println("   "+absPathSRC);
            println("   ************************************************************************************************************************");
            return absPathSRC ;
        } catch (UncheckedIOException | IOException e) {
            println("***ERROR: search file was fail.");
            throw e;
        }
    }

    /**********************************************************************************************************************/
    public static void createTestFile1() throws UncheckedIOException,IOException {
        println("*** START createTestFile1 ***");
        println("************************************************************************************************************************");
        Path absPathSRC = findAbsPath(javaHomeDir,javaSRC);
        println("   // пройдемся по архиву и расшарим все */java.base/java/lang/.*");
        println("   // апишим все в "+testFile1);
        countFile=0;
        try(FileChannel channel = FileChannel.open(absPathSRC) ){
            int chLength= (int)channel.size();
            MappedByteBuffer mapBuffer = channel.map(FileChannel.MapMode.READ_ONLY,0,chLength);
            println("   размер используемого буфера:"+chLength);
            byte[] byteArray = new byte[mapBuffer.remaining()];
            int n = 0;
            while (mapBuffer.hasRemaining()){
                byteArray[n++] = mapBuffer.get();
            };

            try(ZipInputStream zipIStream = new ZipInputStream(new BufferedInputStream(new ByteArrayInputStream(byteArray)));
                SeekableByteChannel bw =  Files.newByteChannel(testFile1,StandardOpenOption.READ,StandardOpenOption.WRITE,StandardOpenOption.CREATE)
            ){
                ZipEntry zipEntry;
                long entrySize;
                while ((zipEntry= zipIStream.getNextEntry())!=null) {
                    //println("File name: " + zipEntry.getName()); // получим название файла
                    //println("File size: " + zipEntry.getSize());
                    if (!zipEntry.isDirectory() && zipEntry.getName().toString().matches("java.base/java/lang/.*")){
                        entrySize = zipEntry.getSize();
                        byte[] b = new byte[(int) zipEntry.getSize()];
                        countFile++;
                        int countRead = 0;
                        while (countRead < entrySize) {
                            countRead += zipIStream.read(b, countRead, (int)(entrySize - countRead));
                        }
                        zipIStream.closeEntry();
                        bw.write(ByteBuffer.wrap(b));
                    }
                }
            }
        }
        println("   countFile:"+countFile);
        println("   *** END  createTestFile1 ***");
        println("************************************************************************************************************************");
    }
    /**********************************************************************************************************************/
    public static void createTestFile2() throws UncheckedIOException,IOException {
        println("*** START  createTestFile2 ***");
        println("************************************************************************************************************************");
        Path absPathSRC = findAbsPath(javaHomeDir,javaSRC);
        println("   // пройдемся по архиву и расшарим все */java.base/java/lang/.*");
        println("   // апишим все в "+testFile2);
        countFile=0;
        ClassLoader cl = null;
        try(FileSystem fsZip = FileSystems.newFileSystem(absPathSRC, cl)){
            Set<FileVisitOption> options = new HashSet<FileVisitOption>();
            options.add(FileVisitOption.FOLLOW_LINKS);
            /*Stream<PAth>*/
            Files.deleteIfExists(testFile2);
            Files.walkFileTree(fsZip.getPath("\\"),
                    options,
                    100000,
                    new SimpleFileVisitor<Path>() {  @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        // println("file.toString():"+file.getFileName().toString());
                        if(!attrs.isDirectory() && file.toString().matches("/java.base/java/lang/.*")){
                            ++countFile;
                            //  println("file.toString():"+file.toString());
                            Files.write(testFile2,Files.readAllBytes(file),StandardOpenOption.CREATE,StandardOpenOption.APPEND);
                        }
                        return FileVisitResult.CONTINUE;
                    }
                    });
        };

        println("   countFile:"+countFile);
        println("   *** END  createTestFile2 ***");
        println("************************************************************************************************************************");
    }
    /**********************************************************************************************************************/
    public static void createTestFile3() throws UncheckedIOException,IOException {
        println("*** START  createTestFile3 ***");
        println("************************************************************************************************************************");
        Path absPathSRC = findAbsPath(javaHomeDir,javaSRC);
        println("   // пройдемся по архиву и расшарим все */java.base/java/lang/.*");
        println("   // апишим все в "+testFile3);

        countFile=0;
        //     try( ZipInputStream zipIS = new ZipInputStream(new BufferedInputStream(new FileInputStream(absPathSRC.toString())));
        //          OutputStream fileOS = new BufferedOutputStream(new FileOutputStream(testFile3.toString())) ){
        try( ZipInputStream zipIS = new ZipInputStream(new FileInputStream(absPathSRC.toString()));
             OutputStream fileOS = new  FileOutputStream(testFile3.toString()) ){

            ZipEntry ze;
            int entrySize;
            while ( (ze = zipIS.getNextEntry())!=null ){
                if (!ze.isDirectory() && ze.getName().toString().matches("java.base/java/lang/.*")){
                    entrySize = (int) ze.getSize();
                    byte[] b = new byte[entrySize];
                    countFile++;
                    int countRead = 0;
                    while (countRead < entrySize) {
                        countRead += zipIS.read(b, countRead, entrySize - countRead);
                    }
                    zipIS.closeEntry();
                    fileOS.write(b);
                }
            }

        };
        println("   countFile:"+countFile);
        println("   *** END  createTestFile3 ***");
        println("************************************************************************************************************************");
    }
    /**********************************************************************************************************************/
    public static void logTime(Path p, String funcName,long millisec) throws IOException{
        try (FileWriter writer = new FileWriter(p.toString(), true);
             BufferedWriter bufferWriter = new BufferedWriter(writer)){
            //bufferWriter.write("*************************\n");
            bufferWriter.write("Время "+funcName+" : "+millisec+"\n");
        }
    }
/**********************************************************************************************************************/
/**********************************************************************************************************************/
}
