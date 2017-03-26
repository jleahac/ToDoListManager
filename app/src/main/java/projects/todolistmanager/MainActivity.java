package projects.todolistmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> list_of_item;
    private ArrayAdapter<String> list_adapter;
    private ListView mList;
    Intent intent;
    private static final String TAG = "MainActivity";
    String message ;
    public final static int RESULT = 0;
    static int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_of_item = new ArrayList<String>();
        list_adapter = new Adapter(this, R.layout.item_to_add, list_of_item );
        mList = (ListView)  findViewById(R.id.list);
        mList.setLongClickable(true);
        mList.setAdapter(list_adapter);
        list_adapter.notifyDataSetChanged();
        mList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {

                list_of_item.remove(position);

                list_adapter.notifyDataSetChanged();

                Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_LONG).show();

                return true;
            }

        });
    }
    public void sendMessage(View view) {
        intent = new Intent(this, AddAnItem.class);
        startActivityForResult(intent, RESULT);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menuItemAdd) {
            sendMessage(findViewById(R.id.add_task));


        }
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == RESULT){
            if (resultCode == RESULT_OK){
                message = data.getStringExtra(AddAnItem.EXTRA_MESSAGE);
                list_adapter.add(message);
                list_adapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // Save custom values into the bundle
        // Always call the superclass so it can save the view hierarchy state
        for (int i = 0 ; i < list_adapter.getCount() ; i++){
            outState.putString(Integer.toString(i) ,list_adapter.getItem(i) );
        }
        index =list_adapter.getCount();
        outState.putInt("int",index);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void  onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        int index2 = savedInstanceState.getInt("int");
        for (int i = 0; i < index2; i++ ){
            String item_to_restore= savedInstanceState.getString(Integer.toString(i) );
            list_adapter.add(item_to_restore);
            list_adapter.notifyDataSetChanged();
        }
    }

}
