package com.BreakingBytes.SifterReader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

/**
 * Extension of SimpleAdapter that will color the background of a list item based on the item's
 * position within the list. This is based on code by Ying Kit Yuen at:
 * http://eureka.ykyuen.info/2010/03/15/android-%E2%80%93-applying-alternate-row-color-in-listview-with-simpleadapter/
 *
 * Created by sbeitzel on 8/1/13.
 */
public class ColorItemAdapter extends SimpleAdapter {
    private static final int[] COLORS = new int[] {0x30FF0000, 0x300000FF}; // dark red, blue

    public ColorItemAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        int colorPos = position % COLORS.length;
        assert view != null;
        view.setBackgroundColor(COLORS[colorPos]);
        return view;
    }

}
