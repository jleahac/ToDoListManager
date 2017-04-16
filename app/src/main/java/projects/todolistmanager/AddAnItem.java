package projects.todolistmanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AddAnItem extends AppCompatActivity {
    Button add;
    Button cancel;
    public static final String EXTRA_MESSAGE = "MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_an_item);
        final Intent intents = new Intent(this, MainActivity.class);

        // Get the Intent that started this activity and extract the string
        final Intent intent = getIntent();
        // Capture the layout's TextView and set the string as its text
        final EditText editText = (EditText) findViewById(R.id.add_task);
        add = (Button)findViewById(R.id.adder);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String item = editText.getText().toString();
                DatePicker datePicker = (DatePicker) findViewById(R.id.datePicker);
                int day = datePicker.getDayOfMonth();
                int month = datePicker.getMonth();
                int year = datePicker.getYear();
                Calendar cal = Calendar.getInstance();
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, day);
                Date date = cal.getTime();
                intent.putExtra("dueDate", date);

                intent.putExtra(EXTRA_MESSAGE, item);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                setResult(RESULT_CANCELED);
                finish();
            }

    });
    }
}

