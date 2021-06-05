import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * @Author : Teja
 * @version: 1.56.1
 */
public class allRemindersFile {
    /**
     * @Author : Teja
     * @version: 1.56.1
     *           <p>
     * @Description: This method reads the reminder.csv file, and populates the data
     *               into a swing table, and displays the table.
     *
     *               <p>
     * @peram none
     * @return: none
     */
    public void chartMaker() {
        try {
            JFrame reminderFrame = new JFrame();
            // reads the reminder.csv file
            BufferedReader reader = new BufferedReader(new FileReader(new File(".\\reminder.csv")));
            List<String[]> elements = new ArrayList<String[]>();
            String line = null;
            // while the csv has a line
            while ((line = reader.readLine()) != null) {
                // stores all the values of the csv file in an array list called "elements".
                // Useful to use an array list, instead of array
                // because an array list doesn't have defined index value, so there won't be an
                // "index out of bound" exception if the user
                // adds more rows to the csv file
                String[] splitted = line.split(",");
                elements.add(splitted);
            }

            reader.close();
            // initialize the colums in the table.
            String[] columNames = new String[] { "Name", "Start Date", "End Date", "Time" };
            // initialize 2 dimensional array object, for the JFrameTable. The size of
            // elements array list would be 4 in this case.
            Object[][] userInputs = new Object[elements.size()][4];

            // populate the userInputs 2D array object using the elements array list
            // the value of "i", is going the row value, and the second dimension is going
            // to affect the column value.
            for (int i = 0; i < elements.size(); i++) {
                userInputs[i][0] = elements.get(i)[0];
                userInputs[i][1] = elements.get(i)[1];
                userInputs[i][2] = elements.get(i)[2];
                userInputs[i][3] = elements.get(i)[3];
            }
            // initialize the swing table
            JTable table = new JTable(userInputs, columNames);
            // initialize a scroll pane for the user for more accessibility of the table,
            // incase the csv contains a lot of lines
            JScrollPane scrollPane = new JScrollPane(table);
            reminderFrame.add(scrollPane);
            reminderFrame.setSize(1000, 1000);
            // display the table
            reminderFrame.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}