import java.util.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
//This class logs transactions in a text file along with documenting Portfolio Value
public class Tax_log
{
    private Tax_window mainWindow;
    double neval;
    public Tax_log(Tax_window window)
    {
        mainWindow = window;
        neval = 0.0;
    }
    public void do_log(CharSequence logData) throws IOException
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Stocks_log.txt", true)))
        {
            writer.append(logData); 
        }
    }
    public double Portfolio_value(String args) 
    {
        Path path = Paths.get("Portfolio_value_log.txt");
        try {
            if (!Files.exists(path)) {
                Files.writeString(path, "0.0");
            }
            String content = Files.readString(path).trim();
            if (content.isEmpty()) content = "0.0";
            neval = Double.parseDouble(args) + Double.parseDouble(content);
            Files.writeString(path, Double.toString(neval));
        } catch (IOException e) {
            e.printStackTrace();
            return 0.0;
        }
        return neval;
    }
}
