package katas.designpatterns.command.oo;

/**
 *
 */
public class CommandInvoker {

    private Command command;

    public CommandInvoker(Command command) {
        this.command = command;
    }

    public void invokeCommand(){
        command.execute();
    }
}
