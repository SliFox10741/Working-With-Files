package org.example;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClientLog {

    protected File file;
    protected void log(int productNum, int amount) {
        file = new File("log.txt");
        try (FileWriter out = new FileWriter(file, true)) {
            out.write(productNum-1 + ", " + amount + "\n");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void log(String action) {
        file = new File("log.txt");
        try (FileWriter out = new FileWriter(file, true)) {
            out.write(action + "\n");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportAsCSV(File csvFile) throws IOException {
        try (FileWriter writer = new FileWriter(csvFile)) {
            File txtFile = new File("log.txt");
            Scanner scan = new Scanner(txtFile);
            writer.append("productNum, amount" + "\n");
            while (scan.hasNext()) {
                String csv = scan.nextLine().replace("|", ",");
                writer.append(csv);
                writer.append("\n");
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
