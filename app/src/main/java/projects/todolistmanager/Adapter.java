package projects.todolistmanager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import projects.todolistmanager.R;

public class Adapter extends ArrayAdapter {

    private ArrayList<Item> itemsList;

    public Adapter(Context context, int resource, ArrayList<Item> itemsList) {
        super(context, resource, itemsList);
        this.itemsList = itemsList;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.item_to_add, null);
        }

        // Retrieving the title and date from the to-do list
        TextView title = (TextView) view.findViewById(R.id.item);
        TextView date = (TextView) view.findViewById(R.id.txtTodoDueDate);
        title.setText(itemsList.get(position).getTitle());
        Date itemDate = itemsList.get(position).getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date.setText(dateFormat.format(itemDate));

        Calendar myCalendar = Calendar.getInstance();
        Calendar currentCalendar = Calendar.getInstance();

        Date myDate = itemDate;
        Date currentDate = new Date();

        // We init all the information except the day. We want to know if the due date has passed.
        myCalendar.setTime(myDate);
        myCalendar.set(Calendar.HOUR_OF_DAY, 0);
        myCalendar.set(Calendar.MINUTE, 0);
        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);

        currentCalendar.setTime(currentDate);
        currentCalendar.set(Calendar.HOUR_OF_DAY, 0);
        currentCalendar.set(Calendar.MINUTE, 0);
        currentCalendar.set(Calendar.SECOND, 0);
        currentCalendar.set(Calendar.MILLISECOND, 0);

        // If the due date has passed, we color the title and date in red
        if (myCalendar.before(currentCalendar)) {
            title.setTextColor(Color.RED);
            date.setTextColor(Color.RED);
        }
        else {
            title.setTextColor(Color.BLUE);
            date.setTextColor(Color.BLUE);
        }



        title.setTextSize(20);
        return view;
    }

}