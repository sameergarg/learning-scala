package katas.designpatterns.command.oo;

/**
 *
 */
public class DinnerCommand implements Command {

    private Dinner dinner;

    public DinnerCommand(Dinner dinner) {
        this.dinner = dinner;
    }

    @Override
    public void execute() {
        System.out.println("I am having dinner");
    }
}
