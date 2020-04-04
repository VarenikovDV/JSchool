package ru.jschool.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {

    public enum EnumCacheType {
        IN_MEMORY, FILE;
    }

    ;

    // {} -identity by all parameters
    // {false} - ignoring all parameters
    // {true,false,true} - ignoring second parameters and all other  parameters starting from 4
    public boolean[] identityBy() default {};

    public EnumCacheType cacheType() default EnumCacheType.IN_MEMORY;

    //  value must be greater than 1 or "-1" - for caching all list (all other value => default )
    public int listList() default -1;

    public String fileNamePrefix() default "";

    public boolean zip() default false;
}
