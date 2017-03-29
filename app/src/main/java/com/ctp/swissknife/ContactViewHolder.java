package com.ctp.swissknife;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by CTP on 2/5/2017.
 */

public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    protected ImageView photo;
    protected TextView contactName;

    public ContactViewHolder(View itemView) {
        super(itemView);

        photo= (ImageView) itemView.findViewById(R.id.thumbnail);
        contactName = (TextView) itemView.findViewById(R.id.contactName);
        contactName.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getAdapterPosition();
        List<Contact> temp = ContactsActivity.getFilteredList();
        Log.d("clicked","this position"+ temp.get(position).getName());
        Contact thisContact = temp.get(position);
        openDialog(thisContact.getName(),thisContact.getPhoneNumber());

    }

    private void openDialog(String name, String number){
        MyApplication.setName(name);
        MyApplication.setNumber(number);
        CallOrMessageDialog myDialog = new CallOrMessageDialog();
        myDialog.show(MyApplication.getContactsFM(),"CallOrMessage");
    }
}
