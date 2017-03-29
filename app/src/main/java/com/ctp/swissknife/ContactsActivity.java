package com.ctp.swissknife;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class ContactsActivity extends AppCompatActivity {

    private List<Contact> contactList;
    private RecyclerView recyclerView;
    private static List<Contact> filteredList;

    private final String[] projection=
            {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};

    private final String selectionClause = ContactsContract.Contacts.DISPLAY_NAME + " like ?";

    private SearchView searchView;
    private ContactViewAdapter contactViewAdapter;
    private TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        MyApplication.setContactsContext(ContactsActivity.this);
        MyApplication.setContactsFM(getFragmentManager());

        contactList = new ArrayList<Contact>();
        searchView = (SearchView) findViewById(R.id.searchView);
        getContacts();
        populateFilterList("");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
        contactViewAdapter = new ContactViewAdapter(contactList,ContactsActivity.this);
        recyclerView.setAdapter(contactViewAdapter);

        searchView.setOnQueryTextListener(mySearchListener);

    }


    SearchView.OnQueryTextListener mySearchListener = new SearchView.OnQueryTextListener() {

        @Override
        public boolean onQueryTextSubmit(String query) {

            return false;
        }

        @Override
        public boolean onQueryTextChange(String query) {
            populateFilterList(query);
            recyclerView.setLayoutManager(new LinearLayoutManager(ContactsActivity.this));
            contactViewAdapter = new ContactViewAdapter(filteredList,ContactsActivity.this);
            recyclerView.setAdapter(contactViewAdapter);
            contactViewAdapter.notifyDataSetChanged();
            return true;
        }
    };

    private void filter(String text){
        List<Contact> temp = new ArrayList();
        for(int i=0;i<contactList.size();i++){
            if(contactList.get(i).getName().contains(text)){
                temp.add(contactList.get(i));
            }
        }
        //update recyclerview
        contactViewAdapter.updateList(temp);
    }

    private void getContacts(){

        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;

        Cursor people = getContentResolver().query(uri, projection, null, null, null);

        int indexName = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
        int indexNumber = people.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

        if(people.moveToFirst()) {
            do {
                String name   = people.getString(indexName);
                String number = people.getString(indexNumber);

               contactList.add(new Contact(name,number));



            } while (people.moveToNext());
        }
        people.close();
    }

    public void populateFilterList(String query){
        if(query == null || query.equalsIgnoreCase(""))
        {
            filteredList = new ArrayList<Contact>();
            for(int i=0;i<contactList.size();i++)
            {
                filteredList.add(contactList.get(i));
            }
        }
        else {
            filteredList = new ArrayList<Contact>();
            query = query.toLowerCase();
            for (int i = 0; i < contactList.size(); i++) {
                String temp = contactList.get(i).getName().toLowerCase();
                if (temp.contains(query) && temp.startsWith(query)) {
                    filteredList.add(contactList.get(i));
                }
            }
        }
    }

    public static List<Contact> getFilteredList(){
        return filteredList;
    }

}
