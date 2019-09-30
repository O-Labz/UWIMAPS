package com.labz04.omri.uwimaps.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.labz04.omri.uwimaps.R;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/1/2015.
 */
public class DataAdapter extends BaseExpandableListAdapter
{
    private Context ctx;
    private HashMap<String,List<String>> Faculty_Category;
    private List<String> Faculties_List;

    public DataAdapter(Context ctx, HashMap<String, List<String>> Faculty_Category, List<String> Faculties_List)
    {
        this.ctx = ctx;
        this.Faculty_Category = Faculty_Category;
        this.Faculties_List = Faculties_List;
    }

    @Override
    public Object getChild(int parent, int child) {
        return Faculty_Category.get(Faculties_List.get(parent)).get(child);
    }

    @Override
    public long getChildId(int parent, int child) {
        return child;
    }


    @Override
    public View getChildView(int parent, int child, boolean lastChild, View convertview, ViewGroup parentview)
    {
        String child_title = (String)getChild(parent, child);
        if(convertview == null)
        {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.child_layout, parentview, false);
        }

        TextView child_textView = (TextView)convertview.findViewById(R.id.child_text);
        child_textView.setText(child_title);

        return convertview;
    }

    @Override
    public int getChildrenCount(int arg0)
    {

        return Faculty_Category.get(Faculties_List.get(arg0)).size();
    }

    @Override
    public Object getGroup(int arg0)
    {
        return Faculties_List.get(arg0);
    }

    @Override
    public int getGroupCount() {
        return Faculties_List.size();
    }


    @Override
    public long getGroupId(int arg0) {
        return arg0;
    }

    @Override
    public View getGroupView(int parent, boolean isExpanded, View convertview, ViewGroup parentview)
    {
        String group_title = (String)getGroup(parent);
        if (convertview == null)
        {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertview = inflater.inflate(R.layout.parent_layout, parentview, false);
        }

        TextView parent_textView = (TextView)convertview.findViewById(R.id.parent_text);
        parent_textView.setTypeface(null, Typeface.BOLD);
        parent_textView.setText(group_title);
        return convertview;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
