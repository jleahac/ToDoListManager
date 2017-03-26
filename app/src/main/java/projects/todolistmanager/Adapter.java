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

    private ArrayList<String> itemsList;

    public Adapter(Context context, int resource, ArrayList<String> itemsList) {
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

        title.setText(itemsList.get(position));

        // Retrieving the date with the desired format (dd/MM/yyy)
        // We init all the information except the day. We want to know if the due date has passed.

        // If the due date has passed, we color the title and date in red
        if (position %2 == 0) {
            title.setTextColor(Color.RED);
        }
        else {
            title.setTextColor(Color.BLUE);
        }
        title.setTextSize(20);
        return view;
    }

}