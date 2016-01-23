import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

public class CommandRunner
{
    private Map<String, String> commandMap;
    private String encoding;

    public CommandRunner(String encoding)
    {
        try
        {
            commandMap = new HashMap<>();
            File conf = new File("src/main/resources/commands.xml");
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(conf);
            document.getDocumentElement().normalize();
            NodeList nodeList = document.getElementsByTagName("command");
            for(int i = 0; i < nodeList.getLength(); i++)
            {
                Node node = nodeList.item(i);
                if(node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element)node;
                    String commandName = element.getElementsByTagName("name").item(0).getTextContent();
                    String commandClassStr = element.getElementsByTagName("class").item(0).getTextContent();
                    commandMap.put(commandName, commandClassStr);
                }
            }
            this.encoding = encoding;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in, encoding)))
        {
            boolean result = true;
            do
            {
                System.out.print(">");
                String line = br.readLine();
                ParsedCommand parsedCommand = new ParsedCommand(line);
                if(parsedCommand.getCommandName() == null || parsedCommand.getCommandName().equals(""))
                {
                    continue;
                }
                String commandClassStr = commandMap.get(parsedCommand.getCommandName());
                if(commandClassStr == null)
                {
                    System.out.println("No such command found.");
                }
                else
                {
                    Class commandClass = Class.forName(commandClassStr);
                    Constructor constructor = commandClass.getConstructor();
                    Command cmd = (Command) constructor.newInstance();
                    result = cmd.execute(parsedCommand.getArgs());
                }
            }
            while(result);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        CommandRunner commandRunner = new CommandRunner("UTF-8");
        commandRunner.run();
    }
}
