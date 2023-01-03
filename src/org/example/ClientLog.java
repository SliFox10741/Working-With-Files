package org.example;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class ClientLog {

    File fileCSV;
    protected void log(int productNum, int amount) {
        fileCSV = new File("log.txt");
        int correctNumber = productNum;
        try (FileWriter out = new FileWriter(fileCSV, true)) {
            out.write(correctNumber + ", " + amount + "\n");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void log(String action) {
        fileCSV = new File("log.txt");
        try (FileWriter out = new FileWriter(fileCSV, true)) {
            out.write(action + "\n");
        } catch (FileNotFoundException e) {
            throw new RuntimeException("File not found!!!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportAsCSV(File csvFile) throws IOException {
        Scanner scan = new Scanner(fileCSV);
        try (
                FileWriter writer = new FileWriter(csvFile);
        ) {
            File txtFile = new File("log.txt");
            txtFile.createNewFile();
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


    public void clearCSV () {

    }
}
