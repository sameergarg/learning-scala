package katas.designpatterns.command.oo;

/**
 *
 */
public class CommandClient {

    public void create() {
        CommandInvoker dinnerCommandInvoker = new CommandInvoker(new DinnerCommand(new Dinner()));
        dinnerCommandInvoker.invokeCommand();

        CommandInvoker lunchCommandInvoker = new CommandInvoker(new LunchCommand(new Lunch()));
        lunchCommandInvoker.invokeCommand();
    }
}
