package katas.designpatterns.nullobject;

import java.util.Optional;

/**
 *
 */
public class NullObjectPattern {

    public String nullCheck(String toCheck){
        if(toCheck == null) {
            return "The passed value is null";
        } else {
            return "Hello "+toCheck;
        }
    }

    //java 8
    public Optional<String> optional(String toCheck){
        return Optional.ofNullable(toCheck);
    }
}
