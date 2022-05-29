package com.example.bottom_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ItemList extends Fragment {
    ImageView imgLogo;
    TextView title;
    TextView dateTime;
    String strTitle;
    String strDateTime;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_list, container, false);

        imgLogo = view.findViewById(R.id.imageViewLogo);
        title = view.findViewById(R.id.textViewTitle);
        dateTime = view.findViewById(R.id.textViewTime);
        title.setText(strTitle);
        dateTime.setText(strDateTime);
        switch (id) {
            case 1:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_contact));
                break;
            case 2:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.email));
                break;
            case 3:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_message));
                break;
            case 4:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_url));
                break;
            case 5:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_text));
                break;
            case 6:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_phonenumber));
                break;
            default:
                imgLogo.setImageDrawable(getResources().getDrawable(R.drawable.ic_unknown));

        }


        return view;
    }

    public void setItemList(String title, String time) {
        strTitle = title;
        strDateTime = time;
    }
}