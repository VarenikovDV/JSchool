package hw.jschool;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        //List<Number> list = new ArrayList<Integer> (); 
        List<Integer> l1 = new ArrayList<Integer>();

        TestLinkedList<Integer> testArray = new TestLinkedList<>();
        System.out.println(testArray);
/**********************************************************************************************************************/
        testArray.add(1);
        System.out.println(testArray);

        testArray.add(3);
        System.out.println(testArray);

        testArray.add(2);
        System.out.println(testArray);

        testArray.add(0,123);
        System.out.println(testArray);

        testArray.add(2,33);
        System.out.println(testArray);

        testArray.add(4,311);
        System.out.println(testArray);

/**********************************************************************************************************************/
        System.out.println("  remove value: "+testArray.remove(0));
        System.out.println(testArray);

        System.out.println("  remove value: "+testArray.remove(4));
        System.out.println(testArray);

        System.out.println("  remove value: "+testArray.remove(2));
        System.out.println(testArray);
/**********************************************************************************************************************/
        System.out.print("  by iterator:  [ ");
        for(Integer i : testArray){
            System.out.print( " "+i+" ");
        }
        System.out.println("]");
/**********************************************************************************************************************/
        testArray.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.println(testArray);

/**********************************************************************************************************************/
        //Collection<Integer> collection= Collections.emptyList();
        List<Integer> collection= new ArrayList<>();
        testArray.copy(collection);
        System.out.println("   Copy collection: "+collection);
/**********************************************************************************************************************/
    }
}
