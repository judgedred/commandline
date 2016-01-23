import java.io.File;


public class CommandLs implements Command
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
            File currentDirectory = new File(".").getAbsoluteFile();
            printLs(currentDirectory);
        }
        return true;
    }

    public void printLs(File file)
    {
        File[] files = file.listFiles();
        if(files != null)
        {
            for(File f : files)
            {
                System.out.println(f.getName());
            }
        }
    }
}
