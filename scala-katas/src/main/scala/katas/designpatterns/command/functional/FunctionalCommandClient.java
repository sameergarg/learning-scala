package katas.designpatterns.command.functional;

import katas.designpatterns.command.Dinner;

import java.util.function.Consumer;

/**
 * Command client
 */
public class FunctionalCommandClient {

    public void create(){
        //command
        Consumer<Dinner> dinner = d -> System.out.printf("Having "+d+" in dinner");

        //invoker
        FunctionalCommandInvoker<Dinner> dinnerInvoker = new FunctionalCommandInvoker<>();

        //then
        dinnerInvoker.invokeCommand(dinner, new Dinner());
    }
}
