import java.io.File;

public class CommandPwd implements Command
{
    @Override
    public boolean execute(String[] args)
    {
        System.out.println(new File(".").getAbsoluteFile());
        return true;
    }

    @Override
    public String getName()
    {
        return "pwd";
    }
}
