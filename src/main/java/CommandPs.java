import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandPs implements Command
{
    @Override
    public boolean execute(String[] args) throws IOException
    {
        Process p;
        String os = System.getProperty("os.name").toLowerCase();
        CharSequence windows = "windows";
        String encoding;
        String commandLine;
        if(os.contains(windows))
        {
            encoding = "cp866";
            commandLine = "tasklist";
        }
        else
        {
            encoding = "UTF-8";
            commandLine = "ps aux";
        }
        if(args != null)
        {
            for(String a : args)
            {
                String[] arg = a.split("=");
                if(arg.length == 2 && arg[1].length() > 2)
                {
                    String argName = arg[0];
                    String argValue = arg[1].substring(1, arg[1].length() - 1);
                    CharSequence sequence = "pname";
                    if(argName.contains(sequence) && os.contains(windows))
                    {
                        commandLine += " /FI \"IMAGENAME eq \"" + argValue + "\"";
//                            p = Runtime.getRuntime().exec("tasklist FI \"IMAGENAME eq " + argValue + "\"");
                    }
                    else if(argName.contains(sequence))
                    {
                        commandLine += " | grep " + argValue;
                    }
                }
                else
                {
                    System.out.println("Wrong attribute.");
                    return true;
                }
            }
        }
        p = Runtime.getRuntime().exec(commandLine);

        String line;
        if(p != null)
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream(), encoding));
            while((line = input.readLine()) != null)
            {
                System.out.println(line);
            }
            input.close();
        }
        return true;
    }

    @Override
    public String getName()
    {
        return "ps";
    }
}
