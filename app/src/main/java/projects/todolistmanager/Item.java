package projects.todolistmanager;

/**
 * Created by Lea on 13/04/2017.
 */
import java.util.Calendar;
import java.util.Date;
public class Item {

    private String title;
    private Date date;
    private Calendar calendar;

    Item( String title, Date date) {
        this.title = title;
        this.date = date;
        calendar = Calendar.getInstance();
        calendar.setTime(date);
    }
    public String getTitle() {
        return this.title;
    }
    public Date getDate() {
        return this.date;
    }
    public String getStringDate() {
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "/" + String.format("%02d", calendar.get(Calendar.MONTH)+1)  +"/" + String.valueOf(calendar.get(Calendar.YEAR));
    }
    public int getDay(){
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
    public int getMonth(){
        return calendar.get(Calendar.MONTH);
    }
    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }
}
