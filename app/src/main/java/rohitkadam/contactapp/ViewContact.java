package rohitkadam.contactapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewContact extends AppCompatActivity {
    TextView textViewName,textViewNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contact);

        textViewName=findViewById(R.id.textViewName);
        textViewNumber=findViewById(R.id.textViewNumber);

        String name=getIntent().getStringExtra("n");
        String number=getIntent().getStringExtra("nb");

        textViewName.setText(name);
        textViewNumber.setText(number);


    }
}
