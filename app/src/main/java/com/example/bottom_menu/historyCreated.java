package com.example.bottom_menu;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class historyCreated extends Fragment {
    ListView lvCreated;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_created, container, false);
        lvCreated = view.findViewById(R.id.listView);
        DatabaseHelper databaseHelper = new DatabaseHelper(this.requireContext());
        List<QrModel> allQr = databaseHelper.getAll();
        ArrayAdapter<QrModel> qrArrayAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, allQr);
        List<ItemList> allItem = new ArrayList<>();
        for (int i = 0 ; i < allQr.size() ; i++){
            ItemList itemList = new ItemList();
            itemList.setItemList(allQr.get(i).getContent(),"aaa");
            allItem.add(itemList);
        }
        ArrayAdapter<ItemList> adapter = new ArrayAdapter<>(this.getContext(), R.layout.item_list, allItem);
        lvCreated.setAdapter(adapter);
        return view;
    }
}

