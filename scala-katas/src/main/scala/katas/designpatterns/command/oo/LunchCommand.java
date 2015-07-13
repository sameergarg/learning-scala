package katas.designpatterns.command.oo;

/**
 *
 */
public class LunchCommand implements Command {

    private Lunch lunch;

    public LunchCommand(Lunch lunch) {
        this.lunch = lunch;
    }

    @Override
    public void execute() {
        System.out.println("I am having lunch");
    }
}
