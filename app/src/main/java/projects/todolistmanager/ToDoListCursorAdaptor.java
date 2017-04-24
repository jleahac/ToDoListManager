package projects.todolistmanager;

/**
 * Created by Lea on 20/04/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static projects.todolistmanager.DbHelper.TASK_DUE_DATE_INDEX;
import static projects.todolistmanager.DbHelper.TITLE_INDEX;

public class ToDoListCursorAdaptor extends SimpleCursorAdapter {

    private static final int adaptor_flag = 0;

    public ToDoListCursorAdaptor(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to, adaptor_flag);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return inflater.inflate(R.layout.item_to_add, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        // Retrieving the title and date from the to-do list
        TextView title = (TextView) view.findViewById(R.id.txtTodoTitle);
        TextView date = (TextView) view.findViewById(R.id.txtTodoDueDate);

        title.setText(cursor.getString(TITLE_INDEX));

        Date itemDate = new Date(cursor.getLong(TASK_DUE_DATE_INDEX));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        date.setText(dateFormat.format(itemDate));

        Calendar myCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();

        Date currentDate = new Date();

        myCalendar.setTime(itemDate);
        myCalendar.set(Calendar.HOUR_OF_DAY, 0);
        myCalendar.set(Calendar.MINUTE, 0);
        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);

        currentCalendar.setTime(currentDate);
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);

        if (myCalendar.before(currentCalendar)) {
            title.setTextColor(Color.RED);
            date.setTextColor(Color.RED);
        }
        else {
            title.setTextColor(Color.BLUE);
            date.setTextColor(Color.BLUE);
        }
    }
}

