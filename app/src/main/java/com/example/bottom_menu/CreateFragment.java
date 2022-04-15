package com.example.bottom_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

public class CreateFragment extends Fragment {


    ImageView imgContact, imgMessage, imgUrl, imgPhoneNumber, imgText, imgEmail;
    TableLayout tableLayout;
    CreateQrText createQrText = new CreateQrText();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        tableLayout = (TableLayout) view.findViewById(R.id.tablelayout);
       imgContact  =  view.findViewById(R.id.logoContact);
       imgMessage  =   view.findViewById(R.id.logoMessage);
       imgEmail    =  view.findViewById(R.id.logoEmail);
       imgPhoneNumber =  view.findViewById(R.id.logoPhoneNumber);
       imgUrl = view.findViewById(R.id.logoUrl);
       imgText = view.findViewById(R.id.logoText);
       imgText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               FragmentManager fragmentManager = getParentFragmentManager();
               FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
               fragmentTransaction.add(R.id.create_fragment,createQrText);
               fragmentTransaction.commit();

           }
       });
        return view;
    }








}