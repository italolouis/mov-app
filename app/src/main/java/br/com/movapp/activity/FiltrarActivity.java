package br.com.movapp.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import br.com.movapp.R;
import br.com.movapp.activity.fragments.ExerciciosFragment;
import br.com.movapp.activity.fragments.HomeFragment;
import br.com.movapp.adapter.CategoriaViewAdapter;
import br.com.movapp.helper.CategoriaHelper;

public class FiltrarActivity extends AppCompatActivity implements View.OnClickListener {
    private CategoriaViewAdapter adapter;
    private ExpandableListView categoriesList;
    private ArrayList<CategoriaHelper> categories;
    protected Context mContext;
    private ImageView imgViewFecharFiltro;
    private Fragment mFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar);

        new CategoriaHelper(this);
        mContext = this;

        imgViewFecharFiltro = (ImageView)findViewById(R.id.imgViewFecharFiltro);
        imgViewFecharFiltro.setOnClickListener(this);

        categoriesList = (ExpandableListView)findViewById(R.id.categories);
        categories = CategoriaHelper.getCategories();
        adapter = new CategoriaViewAdapter(this, categories, categoriesList);
        categoriesList.setAdapter(adapter);

        categoriesList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                return viewSubcategorias(v, groupPosition);
            }
        });
    }

    private boolean viewSubcategorias(View v, int groupPosition) {
        CheckedTextView checkbox = (CheckedTextView)v.findViewById(R.id.list_item_text_child);
        checkbox.toggle();

        View parentView = categoriesList.findViewWithTag(categories.get(groupPosition).name);
        if(parentView != null) {
            TextView sub = (TextView)parentView.findViewById(R.id.list_item_text_subscriptions);

            if(sub != null) {
                CategoriaHelper category = categories.get(groupPosition);
                if(checkbox.isChecked()) {
                    category.selection.add(checkbox.getText().toString());
                    Collections.sort(category.selection, new CustomComparator());
                }
                else {
                    category.selection.remove(checkbox.getText().toString());
                }

                sub.setText(category.selection.toString());
            }
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        if(v == imgViewFecharFiltro){
            Intent mainIntent = new Intent(FiltrarActivity.this, MainActivity.class);
            mainIntent.putExtra("FRAGMENT", 2);
            FiltrarActivity.this.startActivity(mainIntent);
        }

    }

    public class CustomComparator implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.compareTo(o2);
        }
    }

}
