package katas.designpatterns.command.functional;

import java.util.function.Consumer;

/**
 * No Command classes are required
 * Command client
 */
public class FunctionalCommandInvoker<T> {

    public void invokeCommand(Consumer<T> consumer, T command) {
        consumer.accept(command);
    }
}
