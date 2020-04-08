package test.stream;

import ru.jschool.Person;
import ru.jschool.Streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

public class Main {
    public static <T> void println(T t) {
        out.println(t);
    }

    public static void main(String[] args) {
        // write your code here
        println("*******************");

        List<Integer>  list = Arrays.asList(1,2,3,4,5,6,8,9,0,112,23,13,1,4,4,52,4,63,4);
        out.println(list);
        Streams.of(list).filter(T-> T<10).forEach(T-> out.print(T+" "));
        out.println("");
        Streams.of(list).filter(T-> T<10).filter(T->T!=4).forEach(T-> out.print(T+" "));
        out.println("");
        Streams.of(list).filter(T-> T<10).filter(T->T!=4).transform( T-> new String(T+"_")).forEach(T-> out.print(T+" "));
        out.println("");
        out.println(list);

        List<Person> listP = new ArrayList<>();
        listP.add(new Person("A",1));
        listP.add(new Person("S",2));
        listP.add(new Person("D",3));
        listP.add(new Person("F",4));
        listP.add(new Person("G",5));
        listP.add(new Person("H",2));
        listP.add(new Person("J",2));
        listP.add(new Person("K",15));
        listP.add(new Person("L",1));
        listP.add(new Person("A",41));
        out.println(listP);
        out.println(Streams.of(listP).filter(T->T.getAge()<3).toMap​(T->T.getName(),T->T.getAge()) );

    }


}
