package oose.logic;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import oose.DB.DBC;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Path;
import java.sql.ResultSet;

public class report {

    public void generateFullData(int i) {
        Path path = Path.of(System.getProperty("user.dir"), "src", "main", "java", "oose", "reports");
        DBC dbc = new DBC();
        ResultSet rs;
        switch (i) {
            case 1:
                rs = dbc.retrieveData("Select * from items;");
                generate(rs, Path.of(path.toString(), "items").toString());
                break;
            case 2:
                rs = dbc.retrieveData("Select * from members;");
                generate(rs, Path.of(path.toString(), "members").toString());
                break;
            case 3:
                rs = dbc.retrieveData("Select * from transaction;");
                generate(rs, Path.of(path.toString(), "transaction").toString());
                break;
        }
    }

    public void generate(ResultSet rs, String filepath) {
        try {
            String outputFile = filepath + ".csv";
            FileWriter fw = new FileWriter(outputFile);
            int cols = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= cols; i++) {
                fw.append(rs.getMetaData().getColumnLabel(i));
                if (i < cols) fw.append(',');
                else fw.append('\n');
            }
            while (rs.next()) {
                for (int i = 1; i <= cols; i++) {
                    fw.append(rs.getString(i));
                    if (i < cols) fw.append(',');
                }
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            convertToPdf(filepath);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void convertToPdf(String filePath) throws Exception {
        Workbook workbook = new Workbook(filePath + ".csv");
        workbook.save(filePath + ".xlsx");
        Worksheet worksheet = workbook.getWorksheets().get(0);
        Workbook workbook1 = new Workbook();
        worksheet.autoFitColumns();
        worksheet.autoFitRows();
        workbook1.getWorksheets().add(worksheet);
        workbook1.save(filePath + ".pdf");
        File file = new File(filePath + ".csv");
        file.delete();
        String filepath_pdf = filePath + ".pdf";
        Runtime.getRuntime().exec("cmd /c start \"\" " + filepath_pdf + "\"");
    }
}
