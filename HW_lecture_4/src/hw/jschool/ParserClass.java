package hw.jschool;

import javafx.scene.chart.ScatterChart;

import java.io.IOException;
import java.nio.channels.ScatteringByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class ParserClass {
    //private ArrayDeque<String>       allLines;
    private Set<String>         setWorlds ;
    private ArrayList<String>   allArrayWorlds ;
    private Path                pathTestFile ;
    private ArrayDeque<String>  allLines;

/**********************************************************************************************************************/
    public  ParserClass(Path path) throws IOException {
        allLines = new ArrayDeque<String>();
        setWorlds = new TreeSet<>();
        allArrayWorlds = new ArrayList<>();
        pathTestFile = path;
        try ( Stream<String> streamLines = Files.readAllLines(path).stream()) {
            streamLines.forEach(str -> {
                // Collections.addAll(setWorlds, str.replaceAll("\\|\\[|\\]|[^a-zA-z]+", "@").replaceAll("\\@+|\\|\\[|\\]|_", "@").split("\\@"));
                allLines.add(str);
                Collections.addAll(allArrayWorlds, str.replaceAll("[\\W]|_", "@").replaceAll("[A-Za-z]*[0-9]+[A-Za-z]*", "\\@").replaceAll("\\@+", "@").split("\\@"));
            });
        }
        Predicate<String> pr = new Predicate<String>() {
            @Override
            public boolean test(String s) {
                return s.isEmpty();
            }
        };
        allArrayWorlds.removeIf(pr);
    }
/**********************************************************************************************************************/
    public ArrayList<?> getAllArrayWorldsl() { return  allArrayWorlds;};
/**********************************************************************************************************************/
    public void allSetWorlds(){

        setWorlds.addAll(allArrayWorlds);
        System.out.println("\n   Задание 1");
        System.out.println("   pathTestFile "+pathTestFile);
        System.out.println("   worlds: "+setWorlds);
        System.out.println("   count worlds: " +setWorlds.size());

    }
/**********************************************************************************************************************/
    public void sortWorlds(){

        final List<String>   sortArray= new ArrayList<>();
        allArrayWorlds.stream().distinct().forEach(str-> sortArray.add(str));
        /*
        Comparator<String> cp = new Comparator<String>() {
            @Override
            public int compare(String s, String t1) {
                return 0;
            }
        }*/
        Collections.sort(sortArray,(str1,str2)-> str1.length()-str2.length());
        System.out.println("\n   Задание 2");
        System.out.println("   pathTestFile :"+pathTestFile);
        System.out.println("   Сортировка по длине");
       // System.out.println("                           start array: "+allArrayWorlds);
        System.out.println("                  sort array by length: "+sortArray);
        sortArray.sort((str1,str2)-> str1.compareTo(str2));
        System.out.println("       sort array by lexicographically: "+sortArray);
        System.out.println("                    start count worlds: " +allArrayWorlds.size());
        System.out.println("          coun after distincted worlds: " +sortArray.size());
    }
/**********************************************************************************************************************/
    public void repeatWordCount(){
        System.out.println("\n   Задание 3");
        System.out.println("   pathTestFile :"+pathTestFile);
        System.out.println("   сколшько раз каждое слово встречается в файле ");

        Map<String,Integer>  tempMap = new HashMap<>();
        String key;
        for(int i=0;i<allArrayWorlds.size();i++){
            key =allArrayWorlds.get(i);
            if ( tempMap.containsKey(key)){
                tempMap.put(key, tempMap.get(key)+1);
            }
            else{
                tempMap.put(key,1);
            }
        }
        System.out.print("      repeat wWord count: ");
        tempMap.entrySet().stream().sorted((e1,e2)->e1.getKey().compareTo(e2.toString())).forEach(e-> System.out.print(e+"   "));
        System.out.println("\n");
    }
/**********************************************************************************************************************/
    public void reverseArrayLines(){
        System.out.println("\n   Задание 4");
        System.out.println("   pathTestFile :"+pathTestFile);
        System.out.println("   вывести 50 строк файла в обратном порядке ");
        int i =0;
        Iterator<String> iterator = allLines.descendingIterator();
        while(i++<50){
            System.out.println("       "+i+"   //  "+iterator.next());
       }
    }
/**********************************************************************************************************************/
    private class RevArrayList<T> extends ArrayList<T> {
        int i = ParserClass.RevArrayList.this.size();
        @Override
        public Iterator<T> iterator() {
            return new Iterator<T>() {
                private int currentIndex = size()-1;

                @Override
                public boolean hasNext() {
                    return currentIndex >= 0 ? true : false;
                }

                @Override
                public T next() {
                    if (currentIndex < 0) {
                        throw new NoSuchElementException();
                    } else {
                        return get(currentIndex--);
                    }
                }
            };
        }
        public RevArrayList(Collection<? extends T> cc) {
            super(cc);
        }
    }

/**********************************************************************************************************************/
    public void reverseIteratorForLIst(){
        System.out.println("\n   Задание 5");
        System.out.println("   pathTestFile :"+pathTestFile);
        System.out.println("   реализуем итератор для обхода списка в обратном рорядке");

        Integer[] arrayInt = {1,2,3,4,5,6,7,8,9,10};
        List<Integer> list = new ArrayList<>(arrayInt.length);
        for(Integer i: arrayInt){
            list.add(i);
        }
        System.out.println("   Список по default итератору : "+list);
        ArrayList<Integer> revArrayList=new RevArrayList<>(list);
        System.out.print("   Список по reverse итератору var.1: [  ");
        for(Integer i : revArrayList ){
            System.out.print(i+"  ");
         }
        System.out.println("]");


        ArrayList<Integer> testArray = new ArrayList<Integer>(){
            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>() {
                    private int currentIndex = size()-1;

                    @Override
                    public boolean hasNext() {
                        return currentIndex >= 0 ? true : false;
                    }

                    @Override
                    public Integer next() {
                        if (currentIndex < 0) {
                            throw new NoSuchElementException();
                        } else {
                            return get(currentIndex--);
                        }
                    }
                };
            }
        };

        testArray.addAll(list);
        System.out.print("   Список по reverse итератору var.2: [  ");
        for(Integer i : testArray ){
            System.out.print(i+"  ");
        }
        System.out.println("]");

    }
/**********************************************************************************************************************/
    private int getIdString() throws  InvalidIndexLine{
        System.out.print("id line : ");
        Scanner scanner = new Scanner(System.in);
        int i;
        if (scanner.hasNextInt()) {
            i = scanner.nextInt();
            if (i >= 0 && i<allLines.size()) {
                return i;
            } else {
                throw new InvalidIndexLine();
            }
        } else {
            return -1;
        }
    }
/**********************************************************************************************************************/
    public void getString()  {
        System.out.println("\n   Задание 6");
        System.out.println("   pathTestFile :"+pathTestFile);
        System.out.println("   вывод строки по запросу.");
        System.out.println("   вывод число от 0 до " + allLines.size());
        System.out.println("   для выхода введите не число.");

       try {
           int i;
           while ((i = getIdString()) != -1) {
               System.out.println("     line "+i+": "+ allLines.toArray()[i]);
           }
           System.out.println("*** END ***");
       }
       catch (InvalidIndexLine e){
           System.out.println(e.getMessage());
       }
    }


/**********************************************************************************************************************/
    class InvalidIndexLine extends Exception{
        public InvalidIndexLine(){
            super("ERROR: invalid iIndex line.");
        }
    }
}