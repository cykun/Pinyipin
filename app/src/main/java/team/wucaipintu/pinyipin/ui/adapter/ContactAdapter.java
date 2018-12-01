package team.wucaipintu.pinyipin.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import team.wucaipintu.pinyipin.R;
import team.wucaipintu.pinyipin.bean.Contact;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder>{

    public class ContactViewHolder extends RecyclerView.ViewHolder{
        private ImageView headIV;
        private TextView nameTV;

        public ContactViewHolder(View view){
            super(view);
            headIV=view.findViewById(R.id.iv_head);
            nameTV=view.findViewById(R.id.tv_name);
        }
    }

    private List<Contact> contacts;

    public ContactAdapter(List<Contact> contactList){
        this.contacts=contactList;
    }

    @Override
    public void onBindViewHolder(ContactViewHolder holder,int position){
        Contact contact=contacts.get(position);
        holder.headIV.setImageResource(R.drawable.defaulthead);
        holder.nameTV.setText(contact.nikeName);

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    @Override
    public int getItemCount(){
        return contacts.size();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }
}
