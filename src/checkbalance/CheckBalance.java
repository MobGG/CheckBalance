package checkbalance;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CheckBalance {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        String[] data = readAndPrintFile("d:\\promotion2.log").split("\n");
        int count = data.length;
        String[][] splitData = new String[count][];
        long diff,diffSeconds,diffMinutes,diffHours;
        for (int i = 0; i < count; i++) {
            splitData[i] = data[i].split("\\|");
            String callStart = splitData[i][0] + " " + splitData[i][1];
            String callEnd = splitData[i][0] + " " + splitData[i][2];
            Date start, end;
            double payment = 0;
            try {
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                start = format.parse(callStart);
                end = format.parse(callEnd);

                diff = end.getTime() - start.getTime();
                diffSeconds = diff / 1000 % 60;
                diffMinutes = diff / (60 * 1000) % 60;
                diffHours = diff / (60 * 60 * 1000) % 24;

                switch (splitData[i][4]) {
                    case "P1":
                        payment = (3 + (diffHours * 60) + (diffMinutes - 1) + (diffSeconds * 0.0166666666666667));
                        break;
                    case "P2":
                        payment = (((diffHours * 60) + diffMinutes) * 2) + (diffSeconds * 0.0333333333333333);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.printf(" วันที่ " + splitData[i][0] + " เริ่มโทรเวลา " + splitData[i][1] + " ถึง " + splitData[i][2] + " หมายเลข " + splitData[i][3] + " โปรโมชั่น " + splitData[i][4] + " ค่าบริการ %.2f\n", payment);
        }
    }

    public static String readAndPrintFile(String fileName) {
        String phoneBalance = "";
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(fileName);
            bufferedReader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                phoneBalance += line + "\n";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileReader != null) {
                try {
                    fileReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return phoneBalance;
    }
}
