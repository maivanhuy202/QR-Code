package com.example.bottom_menu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TableLayout;


public class CreateFragment extends Fragment {


    ImageView imgContact, imgMessage, imgUrl, imgPhoneNumber, imgText, imgEmail;
    GridLayout gridLayout;

    CreateQrText createQrText = new CreateQrText();
    CreateQrUrl createQrUrl = new CreateQrUrl();
    CreateQrPhoneNumber createQrPhoneNumber = new CreateQrPhoneNumber();
    CreateQrMessage createQrMessage = new CreateQrMessage();
    CreateQrEmail createQrEmail = new CreateQrEmail();
    CreateQrContact createQrContact = new CreateQrContact();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);
        gridLayout = (GridLayout) view.findViewById(R.id.gridLayout);
       imgContact  =  view.findViewById(R.id.logoContact);
       imgMessage  =   view.findViewById(R.id.logoMessage);
       imgEmail    =  view.findViewById(R.id.logoEmail);
       imgPhoneNumber =  view.findViewById(R.id.logoPhoneNumber);
       imgUrl = view.findViewById(R.id.logoUrl);
       imgText = view.findViewById(R.id.logoText);

       imgText.setOnClickListener(view14 -> {
           FragmentManager fragmentManager = getParentFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container,createQrText).addToBackStack(null).commit();

       });
       imgUrl.setOnClickListener(view13 -> {
           FragmentManager fragmentManager = getParentFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container,createQrUrl).addToBackStack(null).commit();
       });
       imgPhoneNumber.setOnClickListener(view12 -> {
           FragmentManager fragmentManager = getParentFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container,createQrPhoneNumber).addToBackStack(null).commit();
       });
       imgMessage.setOnClickListener(view1 -> {
           FragmentManager fragmentManager = getParentFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container,createQrMessage).addToBackStack(null).commit();
       });
       imgEmail.setOnClickListener(view1 -> {
           FragmentManager fragmentManager = getParentFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container,createQrEmail).addToBackStack(null).commit();
       });
       imgContact.setOnClickListener(view1 -> {
           FragmentManager fragmentManager = getParentFragmentManager();
           FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
           fragmentTransaction.replace(R.id.container,createQrContact).addToBackStack(null).commit();
       });
        return view;
    }








}