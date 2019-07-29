package br.com.movapp.activity.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.movapp.R;
import br.com.movapp.activity.FiltrarActivity;
import br.com.movapp.activity.MainActivity;
import br.com.movapp.activity.TreinoDiaActivity;
import br.com.movapp.controller.TreinoController;
import br.com.movapp.retrofit.ImportFromService;
import br.com.movapp.timeline.TimelineAdapter;
import br.com.movapp.model.ListItem;

public class TreinoFragment extends Fragment implements TimelineAdapter.ItemListener, View.OnClickListener{
    private TreinoController treinoController;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_treino, container, false);

        treinoController = new TreinoController(getContext());

        int orientation = 1;
        RecyclerView recycler = (RecyclerView) view.findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(container.getContext(), orientation, false));

        TimelineAdapter adapter = new TimelineAdapter(orientation, getItems(), this);
        recycler.setAdapter(adapter);

        return view;
    }

    private List<ListItem> getItems() {
        List<String> days  = treinoController.getDiasTrino();

        if(days.isEmpty()){
           ImportFromService.getTreinoFromService(getContext());
        }

        List<ListItem> items = new ArrayList<>();
        Random random = new Random();

        for (String day: days){
            ListItem item = new ListItem();
            item.setName(day);
            item.setAddress("Treino de peito " + random.nextInt(100));
            items.add(item);
        }

        return items;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(TextView item) {
        Toast.makeText(getContext(), item.getText(), Toast.LENGTH_SHORT).show();
        Intent treinoDiaItent = new Intent(getActivity(), TreinoDiaActivity.class);
        treinoDiaItent.putExtra("DIA", item.getText());
        getActivity().startActivity(treinoDiaItent);

    }
}
