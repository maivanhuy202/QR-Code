package com.example.bottom_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

public class CreateFragment extends Fragment {


    ImageView imgContact, imgMessage, imgUrl, imgPhoneNumber, imgText, imgEmail;
    TableLayout tableLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       tableLayout = (TableLayout) container.findViewById(R.id.tablelayout);
       imgContact  = (ImageView) container.findViewById(R.id.logoContact);
       imgMessage  = (ImageView)  container.findViewById(R.id.logoMessage);
       imgEmail    =(ImageView)  container.findViewById(R.id.logoEmail);
       imgPhoneNumber = (ImageView)  container.findViewById(R.id.logoPhoneNumber);
       imgUrl = (ImageView) container.findViewById(R.id.logoUrl);
       imgText = (ImageView) container.findViewById(R.id.logoText);
        return inflater.inflate(R.layout.fragment_create, container, false);
    }





}