import java.io.File;


public class CommandExit implements Command
{
    @Override
    public boolean execute(String[] args)
    {
        return false;
    }

    @Override
    public String getName()
    {
        return "exit";
    }
}
