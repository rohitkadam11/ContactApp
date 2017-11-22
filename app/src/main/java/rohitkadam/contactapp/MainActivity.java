package rohitkadam.contactapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    TextView textViewName,textViewNumber;
    ContactAdapter contactAdapter;
    ArrayList<Contact> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName=findViewById(R.id.textViewName);
        textViewNumber=findViewById(R.id.textViewNumber);

        listView=findViewById(R.id.listViewContact);

      /*  Contact contact=new Contact();
        contact.setName("Rohit");
        contact.setNumber("9833900916");
        Contact secondContact=new Contact();
        secondContact.setName("Piyush");
        secondContact.setNumber("9865789658");*/

        MyDatabase database=new MyDatabase(MainActivity.this);
        //ArrayList<Contact> contacts=new ArrayList<>();
       // database.getallContact();

        /*list.add(contact);
        list.add(secondContact);*/

        contactAdapter=new ContactAdapter(MainActivity.this,database.getallContact());
        listView.setAdapter(contactAdapter);

        registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showPopupMenu(view,i);
            }
        });
        

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //munuinflater converts menu xml file into menu java object
        getMenuInflater().inflate(R.menu.menuoption,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem=item.getItemId();
        
        if(selectedItem == R.id.actionAdd){
            addContact();
            Toast.makeText(this, "Add option is selected", Toast.LENGTH_SHORT).show();

        }
        else if (selectedItem == R.id.actionSetting){
            Toast.makeText(this, "Settings option is selected", Toast.LENGTH_SHORT).show();
        }
        else if(selectedItem == R.id.optionSearch){
            Toast.makeText(this, "Search option selected", Toast.LENGTH_SHORT).show();
        }
        else if(selectedItem ==R.id.menuDelete){
            MyDatabase myDatabase=new MyDatabase(MainActivity.this);
            int i=myDatabase.onDeleteAll();
            Toast.makeText(this, "Delete is selected", Toast.LENGTH_SHORT).show();
            Log.d("MainActivity","Rows Deleted"+i);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu,menu);
        menu.setHeaderTitle("Delete Option");
      
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                int pos =adapterContextMenuInfo.position;
        Contact c= (Contact) contactAdapter.getItem(pos);

        MyDatabase myDatabase=new MyDatabase(MainActivity.this);
        int rows=myDatabase.onDelete(c.getId());

        if(rows > 0){
            contactAdapter.deleteContact(pos);
            contactAdapter.notifyDataSetChanged();
        }

        int selectedItem=item.getItemId();
        if(selectedItem == R.id.actionDelete){
            Toast.makeText(this, "Delete Item Selected", Toast.LENGTH_SHORT).show();

        }
        return super.onContextItemSelected(item);
    }
    
    public void  showPopupMenu(final View view, final int pos){
        final PopupMenu popupMenu=new PopupMenu(MainActivity.this,view);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {



            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int selectedItem=menuItem.getItemId();
                if(selectedItem== R.id.actionView){
                    Contact contact =( Contact)contactAdapter.getItem(pos);
                    viewContact(contact);

                    Toast.makeText(MainActivity.this, "Action ", Toast.LENGTH_SHORT).show();
                    AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo)menuItem.getMenuInfo();

                }
                else if(selectedItem== R.id.actionCall){
                    Contact contact= (Contact)contactAdapter.getItem(pos);
                    call(contact);
                    Toast.makeText(MainActivity.this, "Call", Toast.LENGTH_SHORT).show();
                    //AdapterView.AdapterContextMenuInfo adapterContextMenuInfo= (AdapterView.AdapterContextMenuInfo)menuItem.getMenuInfo();
                }
                else if(selectedItem== R.id.actionedit){
                    Contact contact=list.get(pos);
                    viewContact(contact);
                    Toast.makeText(MainActivity.this, "View", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
        popupMenu.show();
    }
    public void addContact(){
        Intent viewIntent=new Intent(MainActivity.this,AddActivity.class);
        startActivityForResult(viewIntent,1);
    }
    public  void  viewContact(Contact contact){
        String name=contact.getName();
        String number=contact.getNumber();


        Intent intent=new Intent(MainActivity.this,ViewContact.class);
        intent.putExtra("n",name);
        intent.putExtra("nb",number);
        startActivity(intent);

    }
    public void call(Contact contact) {

        String number = contact.getNumber();

        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+number));

        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            String premissions[] = {Manifest.permission.CALL_PHONE};
            ActivityCompat.requestPermissions(MainActivity.this, premissions, 123);

        }
        startActivity(intent);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==1){
            if(resultCode== RESULT_OK){
                String name=data.getStringExtra("name");
                String number=data.getStringExtra("number");

                Contact contact=new Contact();
                contact.setName(name);
                contact.setNumber(number);

                contactAdapter.addContact(contact);
                contactAdapter.notifyDataSetChanged();
            }
        }
    }
}
