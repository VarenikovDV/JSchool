package hw.jschool;

import java.util.*;

public class TestLinkedList<T>  implements  Iterable<T> {

    private Node<T>  firstNode;
    private Node<T>  lastNode;
    int size = 0 ;
/*********************************************************************************************************************/
    private static class Node<T>{

        T currentValue;
        Node<T> nextNode;

        public Node(T currentValue,Node<T> nextNode){
           this.currentValue = currentValue;
           this.nextNode = nextNode;
        }

        public void setNextNodeReferens(Node<T> nextNote){
            this.nextNode=nextNote;
        }

        public T getValue(){
            return currentValue;
        }

        public Node<T> getNextNode(){
            return  nextNode;
        }
    }
/*********************************************************************************************************************/
    public boolean add(T t){
        Node<T> tempNode = lastNode;
        lastNode= new Node<>(t,null);
        if(firstNode == null){
            firstNode=lastNode;
        }
        else{
            tempNode.setNextNodeReferens(lastNode);
        }
        size++;
        return true;
    }
/*********************************************************************************************************************/
    public boolean add(int i,T t) throws IndexOutOfBoundsException {
        if(i < 0 || i >= size()) {
            throw  new IndexOutOfBoundsException();
        }
        Node<T> newNode = new Node<>(t,null);
        Node<T> tempNode = firstNode;
        if(i==0){
            newNode.setNextNodeReferens(firstNode);
            firstNode=newNode;
            size++;
            return true;
        }
        for(int n=1 ; n<i ; n++){
            tempNode=tempNode.getNextNode();
        }
        newNode.setNextNodeReferens(tempNode.getNextNode());
        tempNode.setNextNodeReferens(newNode);
        size++;
        return true;
    }
/*********************************************************************************************************************/
    public T get(int index){
        Node<T> tempNode = firstNode;
        for(int i = 0 ; i<index; i++){
            tempNode = tempNode.getNextNode();
        }
        return  tempNode.getValue();
    }
/*********************************************************************************************************************/
    public  int sizeByIteration(){
        int i = 0;
        Node<T> tempNode = firstNode;
        while( tempNode != null){
            i++;
            tempNode=tempNode.getNextNode();
        }
        return i;
    }
/*********************************************************************************************************************/
    public  int size(){ return size; }
/*********************************************************************************************************************/
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Array size: " + size()+"\n");
        if(size>0){
            sb.append("  [");
            Node<T> n = firstNode;
            while(n!=null){
                sb.append(" "+n.getValue()+" " );
                n=n.getNextNode();
            }
            sb.append("]");
        }
        return sb.toString();
    }
/*********************************************************************************************************************/
    public T remove(int i) throws IndexOutOfBoundsException  {
        if(i < 0 || i >= size()) throw  new IndexOutOfBoundsException();
        Node<T> t = firstNode;
        T v;
        if(i==0){
            firstNode=firstNode.getNextNode();
            size--;
            return t.getValue();
        }
        for(int n=1; n<i;n++) t=t.getNextNode();
        v=t.getNextNode().getValue();
        if(i==size-1) {
            lastNode=t;
            lastNode.setNextNodeReferens(null);
        }
        else {
            t.setNextNodeReferens(t.getNextNode().getNextNode());
        }
        size--;
        return v;
    }
/*********************************************************************************************************************/
    public Iterator<T> iterator(){
        return new Iterator<T>() {
            private Node<T> currentNode = firstNode;
            private T value;
            @Override
            public boolean hasNext() {
                return currentNode!=null?true:false;
            }

            @Override
            public T next() {
                if(currentNode==null) throw new NoSuchElementException();
                value = currentNode.getValue();
                currentNode=currentNode.getNextNode();
                return value;
            }
        };

    }
/*********************************************************************************************************************/
    public boolean addAll(Collection<? extends T> c){
        if(c==null) throw new NullPointerException();
        for(T t:c){
            add(t);
        }
        return true;
    }
/*********************************************************************************************************************/
    public boolean copy(Collection<? super T> c) throws NullPointerException{
        if(c==null) throw new NullPointerException();
        for(T t:this){
            c.add(t);
        }
        return  true;
    }
/*********************************************************************************************************************/
/*********************************************************************************************************************/
/*********************************************************************************************************************/
/*********************************************************************************************************************/

}
