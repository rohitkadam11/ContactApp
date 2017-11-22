package rohitkadam.contactapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 170840521012 on 16-11-2017.
 */

    public class ContactAdapter extends BaseAdapter {
        ArrayList<Contact> list;
        Context context;
    public ContactAdapter(Context context,ArrayList<Contact> l) {
        this.context=context;
        list=l;
    }

    @Override
    public int getCount() {
         return  list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    public  void addContact(Contact contact){
        list.add(contact);
    }

    public  void  deleteContact(int contact){
        list.remove(contact);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Contact contact=list.get(i);
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View listView=layoutInflater.inflate(R.layout.contact_item_row,viewGroup,false);

        TextView textViewName=listView.findViewById(R.id.textViewName);
        TextView textViewNumber=listView.findViewById(R.id.textViewNumber);

        textViewName.setText(contact.getName());
        textViewNumber.setText(contact.getNumber());
        return listView;
    }
}
