package rohitkadam.contactapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by 170840521012 on 21-11-2017.
 */

public class MyDatabase extends SQLiteOpenHelper {

    String DbName = "contact.db";
    String tableName = "contacts";
    String column_name = "contact_name";
    String column_number = "contact_number";
    String column_id = "id";
    String create_query = "create table contacts(id integer primary key,contact_name text,contact_number integer)";
    String delete_query="delete from contacts ";

    public MyDatabase(Context context) {
        super(context, "contact.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long onInsert(Contact contact) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(column_name, contact.getName());
        contentValues.put(column_number, contact.getNumber());
        long insertid = database.insert(tableName, null, contentValues);
        return insertid;
    }

    public ArrayList<Contact> getallContact() {
        ArrayList<Contact> list=new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query(tableName, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (true) {
            if (cursor.isAfterLast())
                break;
            String name=cursor.getString(cursor.getColumnIndex(column_name));
            String number=cursor.getString(cursor.getColumnIndex(column_number));
            long id=cursor.getLong(cursor.getColumnIndex(column_id));

            Contact contact=new Contact();
            contact.setName(name);
            contact.setNumber(number);
            contact.setId(id);
            list.add(contact);
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public int onDeleteAll(){
        SQLiteDatabase database=getWritableDatabase();
        int i=database.delete(tableName,null,null);
        return i;
    }
    public  int onDelete(long id){
        //Contact contact=new Contact();
        SQLiteDatabase database=getWritableDatabase();
        //String i= String.valueOf(contact.getId());
        String[] args={id+""};
        int rows=database.delete(tableName,"id= ?",args);
        return rows;
    }
}
