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
    private Map<String, Command> commandMap;
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
                    Class commandClass = Class.forName(commandClassStr);
                    Constructor constructor = commandClass.getConstructor();
                    Command cmd = (Command)constructor.newInstance();
                    commandMap.put(commandName, cmd);
                }
            }


            /*Command cmd = new CommandLs();
            commandMap.put(cmd.getName(), cmd);
            cmd = new CommandExit();
            commandMap.put(cmd.getName(), cmd);
            cmd = new CommandPwd();
            commandMap.put(cmd.getName(), cmd);
            cmd = new CommandPs();
            commandMap.put(cmd.getName(), cmd);*/
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
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception
    {
        /*File conf = new File("src/main/resources/commands.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(conf);
        document.getDocumentElement().normalize();
        System.out.println("Root Element: " + document.getDocumentElement().getNodeName());
        NodeList nodeList = document.getElementsByTagName("command");
        System.out.println("OK");
        for(int i = 0; i < nodeList.getLength(); i++)
        {
            Node node = nodeList.item(i);
            System.out.println("Current element: " + node.getNodeName());
            if(node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element)node;
                System.out.println("Name: " + element.getElementsByTagName("name").item(0).getTextContent());
                System.out.println("Class: " + element.getElementsByTagName("class").item(0).getTextContent());
            }
        }*/
        CommandRunner commandRunner = new CommandRunner("UTF-8");
        commandRunner.run();
    }
}
