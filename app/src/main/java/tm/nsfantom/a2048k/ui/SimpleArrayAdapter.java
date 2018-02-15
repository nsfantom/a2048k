package tm.nsfantom.a2048k.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tm.nsfantom.a2048k.R;
import tm.nsfantom.a2048k.model.StatItem;

/**
 * Created by user on 2/15/18.
 */

public class SimpleArrayAdapter extends ArrayAdapter<StatItem> {
    ArrayList<StatItem> statItems;
    Context context;

    public SimpleArrayAdapter(@NonNull Context context, @NonNull ArrayList<StatItem> objects) {
        super(context, -1, objects);
        this.statItems = objects;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_stats, parent, false);
        TextView resultView = (TextView) rowView.findViewById(R.id.tvResult);
        TextView dateView = (TextView) rowView.findViewById(R.id.tvDate);
        TextView positionView = (TextView) rowView.findViewById(R.id.tvPosition);
        StatItem statItem = statItems.get(position);
        resultView.setText(String.valueOf(statItem.getResult()));
        // change the icon for Windows and iPhone
        dateView.setText(String.valueOf(statItem.getDate()));
        positionView.setText(String.valueOf(position+1));

        return rowView;
    }
}
