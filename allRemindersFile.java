import java.text.SimpleDateFormat;
import java.util.Date;
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

            /*
            * @author Michelle Chan
            * Reads reminders from FileHandler
            */

            List<Reminder> reminderList = FileHandler.readReminder();
            // initialize the colums in the table.
            String[] columNames = new String[] { "Name", "Start Date", "End Date", "Time" };
            // initialize 2 dimensional array object, for the JFrameTable. The size of
            // elements array list would be 4 in this case.
            Object[][] userInputs = new Object[reminderList.size()][4];

            // populate the userInputs 2D array object using the reminder list from the FileHandler
            // the value of "i", is going the row value, and the second dimension is going
            // to affect the column value.
            int i = 0;
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            for (Reminder reminder : reminderList) {
                userInputs[i][0] = reminder.getName();
                Date startDate = reminder.getStartDate().getTime();
                Date endDate = reminder.getEndDate().getTime();
                userInputs[i][1] = df.format(startDate);
                userInputs[i][2] = df.format(endDate);
                userInputs[i][3] = reminder.getTime();
                i++;
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