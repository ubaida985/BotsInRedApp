package com.example.botsinred.fragments.dose;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.botsinred.R;
import com.example.botsinred.adapters.CategoryAdapter;
import com.example.botsinred.database.Data;
import com.example.botsinred.models.CategoryModel;

import java.util.ArrayList;
import java.util.HashMap;

public class PillCategoryFragment extends Fragment implements CategoryAdapter.OnViewItemClickListener {

    private ArrayList<CategoryModel> categories;
    private RecyclerView recyclerViewCategories;
    private CategoryAdapter adapter;

    String doseName, doseTime, doseCategory;
    public PillCategoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        doseName = getArguments().getString("doseName");
        doseTime = getArguments().getString("doseTime");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pill_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize all class variables
        initializer();

        //set up adapters
        setUpCategoryAdapter();
    }

    private void setUpCategoryAdapter() {
        setUpCategories();

        adapter = new CategoryAdapter(categories, getActivity(), this);
        recyclerViewCategories.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerViewCategories.setAdapter(adapter);
    }

    private void setUpCategories() {
        categories.add(new CategoryModel( "Liver", new HashMap<>()));
        categories.add(new CategoryModel( "Stomach" , new HashMap<>()));
        categories.add(new CategoryModel( "Headache" , new HashMap<>()));
        categories.add(new CategoryModel( "Diabetes" , new HashMap<>()));
        categories.add(new CategoryModel( "CVD" , new HashMap<>()));
        categories.add(new CategoryModel( "Stone" , new HashMap<>()));
    }

    private void initializer() {
        categories = new ArrayList<>();
        recyclerViewCategories = getView().findViewById(R.id.recyclerViewCategories);
    }

    @Override
    public void onItemClick(int position) {
        Fragment fragment = new AddDoseFragment();
        Bundle data = new Bundle();
        data.putString("doseName", doseName);
        data.putString("doseTime", doseTime);
        data.putString("doseCategory", categories.get(position).getCategoryName());
        fragment.setArguments(data);
        loadFragment(fragment);
    }

    private void loadFragment(Fragment fragment) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, fragment)
                .commit();
    }

    private void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}