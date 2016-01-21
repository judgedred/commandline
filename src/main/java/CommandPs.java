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
        CharSequence cs = "windows";
        String encoding;
        if(os.contains(cs))
        {
            encoding = "cp866";
            p = Runtime.getRuntime().exec("tasklist");
        }
        else
        {
            encoding = "UTF-8";
            p = Runtime.getRuntime().exec("ps -e");
        }
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
