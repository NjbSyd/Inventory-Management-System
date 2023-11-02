package oose.logic;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import oose.DB.DBC;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;

public class report {

    public void generateFullData(int i) {
        DBC dbc = new DBC();
        switch (i) {
            case 1:
                ResultSet rs = dbc.retrieveData("Select * from items;");
                generate(rs, "items");
                break;
            case 2:
                rs = dbc.retrieveData("Select * from members;");
                generate(rs, "members");
                break;
            case 3:
                rs = dbc.retrieveData("Select * from transaction;");
                generate(rs, "transaction");
                break;
        }
    }

    public void generate(ResultSet rs, String filepath) {
        try {
            FileWriter fw = new FileWriter("F:\\OOSE\\InventoryMgmtSYS\\OutputFiles\\" + filepath + ".csv");
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

    public void convertToPdf(String filename) throws Exception {
        String outpath = "F:\\OOSE\\InventoryMgmtSYS\\OutputFiles\\";
        Workbook workbook = new Workbook(outpath + filename + ".csv");
        workbook.save(outpath + filename + ".xlsx");
        Worksheet worksheet = workbook.getWorksheets().get(0);
        Workbook workbook1 = new Workbook();
        worksheet.autoFitColumns();
        worksheet.autoFitRows();
        workbook1.getWorksheets().add(worksheet);
        workbook1.save(outpath + filename + ".pdf");
        File file = new File(outpath + filename + ".csv");
        file.delete();
        String filepath_pdf = "F:\\OOSE\\InventoryMgmtSYS\\OutputFiles\\" + filename + ".pdf";
        Runtime.getRuntime().exec("cmd /c start \"\" " + filepath_pdf + "\"");
    }
}
