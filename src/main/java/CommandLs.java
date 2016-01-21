import java.io.File;


public class CommandLs implements Command
{
    @Override
    public boolean execute(String[] args)
    {
        File currentDirectory = new File(".").getAbsoluteFile();
        printLs(currentDirectory);
        return true;
    }

    @Override
    public String getName()
    {
        return "ls";
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
