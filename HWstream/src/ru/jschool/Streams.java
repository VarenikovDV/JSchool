package ru.jschool;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class Streams<T> {
    private Streams<?> downstream;
    private final Iterator<T> iterator;

    private Streams(Iterator<T> iterator, Streams<T> downstream) {
        this.downstream = downstream;
        this.iterator = iterator;
    }


    private Streams(Iterator<T> i) {
        this.iterator = i;
    }

    public static <T> Streams<T> of(List<? extends T> list) {
        Objects.requireNonNull(list);
        return new Streams<T>((Iterator<T>) list.iterator());
    }

    public Streams<T> filter(Predicate<? super T> p) {
        Objects.requireNonNull(p);
        Iterator<T> tempIterator = new Iterator<T>() {
            T tempValue;
            boolean flag =false;
            @Override
            public boolean hasNext() {
                if (flag==false&&iterator.hasNext()) {
                    tempValue = iterator.next();
                    if (p.test(tempValue)) {
                        flag=true;
                        return true;
                    } else {
                        return hasNext();
                    }
                } else if(flag == true) return true;
                else return false;
               // return false;
            }

            @Override
            public T next() {
                if (hasNext()) {
                    flag = false;
                    return tempValue;
                }
                throw new NoSuchElementException();
            }
        };
        return new Streams<T>(tempIterator, this);
    }
    public <R> Streams<R> transform(Function<? super T,? extends R> mapper){
        Iterator<R> tempIterator = new Iterator<R>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public R next() {
                return mapper.apply(iterator.next());
            }
        };
        return new <R>Streams(tempIterator,this);
    }

    public void forEach(Consumer<? super T> action) {
        while (iterator.hasNext()) {
            action.accept(iterator.next());
        }
    }
    public <K,U>  Map <K,U> toMap​(Function<? super T,? extends K> keyMapper, Function<? super T,? extends U> valueMapper){
        Map<K,U> map = new HashMap<>();
        while (iterator.hasNext()){
            T t = iterator.next();
            map.put(keyMapper.apply(t),valueMapper.apply(t));
        }
        return map;
    }

/*
    public static <E> Streams<E> of(List<E> list) {
        List<E> streamList = new ArrayList<>();
        for (E e : list) {
            streamList.add(e);
        }
        return new Streams<>(streamList);
    }
*/
}


