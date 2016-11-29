package tycho.tychobuisman_pset5;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;


public class sublistAcitivity extends AppCompatActivity {
    private DBManager dbManager;

    private ListView lv;

    private MyCustomAdapter adapter;

    private ArrayList<String> Items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublist);

        dbManager = new DBManager(this);
        dbManager.open();

        lv = (ListView) findViewById(R.id.list);
        lv.setEmptyView(findViewById(R.id.empty));

        Items = viewAll(1);

        final ListView lv = (ListView) findViewById(R.id.list);
        adapter = new MyCustomAdapter(Items, this);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<String> IdList = viewAll(0);

                String _id = IdList.get(position);
                long l = Long.parseLong(_id);
                int result = dbManager.deleteSubTable(l);

                if(result > 0){
                    Toast.makeText(getApplicationContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                    Items.remove(position);

                    ListView lv = (ListView) findViewById(R.id.list);
                    adapter = new MyCustomAdapter(Items, getApplicationContext());
                    lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Item not deleted", Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    // on click
    public void addItem(View view){
        EditText text = (EditText)findViewById(R.id.editText);
        String item = text.getText().toString();
        dbManager.insertSubTable(item);
        Items.add(item);
        Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
        text.setText("");
        ArrayList<String> Items = viewAll(1);
        ListView lv = (ListView) findViewById(R.id.list);
        adapter = new MyCustomAdapter(Items, this);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

    public ArrayList<String> viewAll(int column){
        ArrayList<String> List = new ArrayList<String>();
        String key = getIntent().getExtras().getString("key");
        Cursor cursor = dbManager.fetchSubTable();
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0) == key) {
                    List.add(cursor.getString(column));
                }
            } while (cursor.moveToNext());
        }
        return List;
    }
}