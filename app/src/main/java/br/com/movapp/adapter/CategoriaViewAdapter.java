package br.com.movapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.movapp.R;
import br.com.movapp.helper.CategoriaHelper;

public class CategoriaViewAdapter extends BaseExpandableListAdapter {
    private LayoutInflater inflater;
    private ArrayList<CategoriaHelper> mParent;
    private ExpandableListView accordion;
    public int lastExpandedGroupPosition;


    public CategoriaViewAdapter(Context context, ArrayList<CategoriaHelper> parent, ExpandableListView accordion) {
        mParent = parent;
        inflater = LayoutInflater.from(context);
        this.accordion = accordion;

    }


    @Override
    //counts the number of group/parent items so the list knows how many times calls getGroupView() method
    public int getGroupCount() {
        return mParent.size();
    }

    @Override
    //counts the number of children items so the list knows how many times calls getChildView() method
    public int getChildrenCount(int i) {
        return mParent.get(i).children.size();
    }

    @Override
    //gets the title of each parent/group
    public Object getGroup(int i) {
        return mParent.get(i).name;
    }

    @Override
    //gets the name of each item
    public Object getChild(int i, int i1) {
        return mParent.get(i).children.get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    //in this method you must set the text to see the parent/group on the list
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if (view == null) {
            view = inflater.inflate(R.layout.view_categorias, viewGroup,false);
        }
        // set category name as tag so view can be found view later
        view.setTag(getGroup(i).toString());

        TextView textView = (TextView) view.findViewById(R.id.list_item_text_view);

        //"i" is the position of the parent/group in the list
        textView.setText(getGroup(i).toString());

        TextView sub = (TextView) view.findViewById(R.id.list_item_text_subscriptions);

        if(mParent.get(i).selection.size()>0) {
            sub.setText(mParent.get(i).selection.toString());
        }
        else {
            sub.setText("");
        }

        return view;
    }


    @Override
    //in this method you must set the text to see the children on the list
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.view_subcategorias, viewGroup,false);
        }


        CheckedTextView textView = (CheckedTextView) view.findViewById(R.id.list_item_text_child);

        //"i" is the position of the parent/group in the list and
        //"i1" is the position of the child
        textView.setText(mParent.get(i).children.get(i1).name);

        // set checked if parent category selection contains child category
        if(mParent.get(i).selection.contains(textView.getText().toString())) {
            textView.setChecked(true);
        }
        else {
            textView.setChecked(false);
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void onGroupExpanded(int groupPosition) {

        if(groupPosition != lastExpandedGroupPosition){
            accordion.collapseGroup(lastExpandedGroupPosition);
        }

        super.onGroupExpanded(groupPosition);

        lastExpandedGroupPosition = groupPosition;

    }

}
