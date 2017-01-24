package sample;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Phoen on 12.01.2017.
 */
public class FileLog {
    public static void Write(File file, double value, double stackValue, String operator){
        Calendar calendar = Calendar.getInstance();
        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
            br.write( calendar.getTime().toString()+ "; " + stackValue + operator + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void Write(File file, String login, double value){
        try (BufferedWriter br = new BufferedWriter(new FileWriter((file), true))) {
            br.write("=" + value + "; " + login + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}