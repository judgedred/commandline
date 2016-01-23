import java.io.File;

public class CommandPwd implements Command
{
    @Override
    public boolean execute(String[] args)
    {
        if(args != null)
        {
            for(String a : args)
            {
//               do something
            }
        }
        else
        {
            System.out.println(new File(".").getAbsoluteFile());
        }
        return true;
    }
}
