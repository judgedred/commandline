import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CommandRunner
{
    private Map<String, Command> commandMap;
    private String encoding;

    public CommandRunner(String encoding)
    {
        commandMap = new HashMap<>();
        Command cmd = new CommandLs();
        commandMap.put(cmd.getName(), cmd);
        cmd = new CommandExit();
        commandMap.put(cmd.getName(), cmd);
        cmd = new CommandPwd();
        commandMap.put(cmd.getName(), cmd);
        cmd = new CommandPs();
        commandMap.put(cmd.getName(), cmd);
        this.encoding = encoding;
    }

    public void run() throws IOException
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in, encoding));
        boolean result = true;
        do
        {
            System.out.print(">");
            String line = br.readLine();
            ParsedCommand parsedCommand = new ParsedCommand(line);
            Command cmd = commandMap.get(parsedCommand.getCommandName());
            if(cmd == null)
            {
                System.out.println("No such command found.");
            }
            else
            {
                result = cmd.execute(parsedCommand.getArgs());
            }
        }
        while(result);
    }

    public static void main(String[] args)
    {
        try
        {
            CommandRunner commandRunner = new CommandRunner("cp866");
            commandRunner.run();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
