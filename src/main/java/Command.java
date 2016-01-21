import java.io.File;
import java.io.IOException;

public interface Command
{
    public boolean execute(String[] args) throws IOException;
    public String getName();
}
