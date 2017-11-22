package rohitkadam.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AddActivity extends AppCompatActivity {
    TextView editTextName,editTextNumber;
    Button buttonAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        editTextName=findViewById(R.id.editTextName);
        editTextNumber=findViewById(R.id.editTextNumber);
        buttonAdd=findViewById(R.id.buttonAdd);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String a=editTextName.getText().toString();
                String b=editTextNumber.getText().toString();
                Intent intent=new Intent();
                intent.putExtra("name",a);
                intent.putExtra("number",b);

                Contact contact=new Contact();
                contact.setName(a);
                contact.setNumber(b);
                MyDatabase myDatabase=new MyDatabase(AddActivity.this);
                long insertid=myDatabase.onInsert(contact);
                setResult(RESULT_OK,intent);
                Log.d("activity_add","contact inserted "+insertid);
                finish();


            }
        });

    }
}
