package hw.jschool;

import java.math.BigInteger;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        System.out.println("+++ testTestLinkedList() +++");
        testTestLinkedList();
        System.out.println("+++ testImpCountMap() +++");
        testImpCountMap();
        System.out.println("+++ testCollectionUtils() +++");
        testCollectionUtils();
    }

    public  static  void testCollectionUtils(){
        List<Number> an =  new ArrayList<>(Arrays.asList(1,2,3d,23L));
        List<Integer> ai= Arrays.asList(11,22,33,44);
        CollectionUtils.addAll(Arrays.asList(11,22,33,44,1), an);
        System.out.println(an);
        System.out.println(CollectionUtils.indexOf(an,3.0));
        System.out.println(CollectionUtils.newArrayList().addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,0)));
        System.out.println(CollectionUtils.newArrayList( Arrays.asList(1,2,3,4,5,6,7,8,9,0).iterator()));
        System.out.println(CollectionUtils.range(Arrays.asList(8,1,3,5,6, 4), 3, 6));
        System.out.println(CollectionUtils.range(Arrays.asList(8,1,3,5,6, 4), 3, 6,(v1,v2)->v1.compareTo(v2)));
    }
    public static void testImpCountMap(){
        CountMap<Number> map = new ImpCountMap<>();
        map.add(10);
        map.add(10);
        map.add(5d);
        map.add(6);
        map.add(5d);
        map.add(10);
        System.out.println(map.getCount(5)); // 2
        System.out.println(map.getCount(5d)); // 2
        System.out.println(map.getCount(6)); // 1
        System.out.println(map.getCount(10)); // 3*/
        Map<Number,Integer> m1 = map.toMap();
        System.out.println(m1);
        map.add(10);
        map.toMap(m1);
        System.out.println(m1);
    }

    public static void testTestLinkedList(){
        TestLinkedList<Integer> testArray = new TestLinkedList<>();
        System.out.println(testArray);
        /**************************************************************************************************************/
        System.out.println("***********************************************************");
        testArray.add(1);
        testArray.add(3);
        testArray.add(2);
        testArray.add(0,123);
        testArray.add(2,33);
        testArray.add(4,311);
        System.out.println(testArray);
        /**************************************************************************************************************/
        System.out.println("***********************************************************");
        System.out.println("  remove value: "+testArray.remove(0));
        System.out.println(testArray);
        System.out.println("  remove value: "+testArray.remove(4));
        System.out.println(testArray);
        System.out.println("  remove value: "+testArray.remove(2));
        System.out.println(testArray);
        /**************************************************************************************************************/
        System.out.println("***********************************************************");
        System.out.print("  by iterator:  [ ");
        for(Integer i : testArray){
            System.out.print( " "+i+" ");
        }
        System.out.println("]");
        /**************************************************************************************************************/
        System.out.println("***********************************************************");
        testArray.addAll(Arrays.asList(1,2,3,4,5,6,7,8,9,10));
        System.out.println(testArray);
        TestLinkedList<Number> testLinkedListNumber= new TestLinkedList<>();
        testLinkedListNumber.add((Number)123l);
        testLinkedListNumber.add(Integer.valueOf(20));
        testLinkedListNumber.add(123d);
        testLinkedListNumber.addAll(Arrays.asList(1000L,200));
        System.out.println(testLinkedListNumber);
        for(Object o: testLinkedListNumber){
            System.out.print("  "+o.getClass());
        }

        /**************************************************************************************************************/
        System.out.println("\n***********************************************************");
        List<Integer> listInt= new ArrayList<>();
        testArray.copy(listInt);
        System.out.println("   Copy TestLinkedList<Integer> to Collection<Integer>: "+listInt);
        List<Number> listNumber = new ArrayList<>();
        testArray.copy(listNumber);
        System.out.println("   Copy TestLinkedList<Integer> to  Collection<Number>: "+listNumber);
        /**************************************************************************************************************/

    }

}
