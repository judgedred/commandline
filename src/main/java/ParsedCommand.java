
public class ParsedCommand
{
    private String commandName;
    private String[] args;

    public String getCommandName()
    {
        return commandName;
    }

    public String[] getArgs()
    {
        return args;
    }

    public ParsedCommand(String line)
    {
        String[] inputs = line.split(" ");
        if(inputs.length > 0)
        {
            commandName = inputs[0];
            if(inputs.length > 1)
            {
                args = new String[inputs.length - 1];
                System.arraycopy(inputs, 1, args, 0, args.length);
            }
        }
    }
}
