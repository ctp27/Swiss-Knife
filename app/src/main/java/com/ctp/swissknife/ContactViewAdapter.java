package com.ctp.swissknife;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by CTP on 2/5/2017.
 */

public class ContactViewAdapter extends RecyclerView.Adapter<ContactViewHolder> {

    private List<Contact> contactList;
    private Context context;

    public ContactViewAdapter(List<Contact> contactList, Context context) {
        this.contactList = contactList;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if(contactList==null)
            return 0;
        else
            return contactList.size();
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder, int position) {
        Contact contact = contactList.get(position);
//        Log.d("adapter","contact "+contact);
        holder.contactName.setText(contact.getName());
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_tile,null);
        ContactViewHolder contactViewHolder = new ContactViewHolder(view);      //creates the object of the image holder
        return contactViewHolder;

    }



        public void updateList(List<Contact> contactLists){
            contactList.clear();
            contactList.addAll(contactLists);
            notifyDataSetChanged();
        }

}
