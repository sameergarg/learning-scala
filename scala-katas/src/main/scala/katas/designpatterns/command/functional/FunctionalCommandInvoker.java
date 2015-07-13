package katas.designpatterns.command.functional;

import java.util.function.Consumer;

/**
 *
 */
public class FunctionalCommandInvoker<T> {

    public void invokeCommand(Consumer<T> consumer, T command) {
        consumer.accept(command);
    }
}
