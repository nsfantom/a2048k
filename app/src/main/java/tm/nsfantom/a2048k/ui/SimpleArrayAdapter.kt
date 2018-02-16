package tm.nsfantom.a2048k.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.item_stats.view.*
import tm.nsfantom.a2048k.R
import tm.nsfantom.a2048k.model.StatItem
import java.util.*
import android.text.format.DateUtils
import java.text.SimpleDateFormat


class SimpleArrayAdapter(internal var context: Context, internal var statItems: ArrayList<StatItem>) : ArrayAdapter<StatItem>(context, -1, statItems) {

    fun updateData(statItems: ArrayList<StatItem>){
        this.statItems = statItems
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.item_stats, parent, false)
        val statItem = statItems[position]
        rowView.tvResult.text = context.getString(R.string.score_label,statItem.result)
        rowView.tvDate.text = formateDate(statItem.date)//statItem.date.toString()
        rowView.tvPosition.text = statItem.rank.toString()
        return rowView
    }

    private fun formateDate(millis: Long):String{
        val sdf = SimpleDateFormat("dd-MM-yyyy\nHH:mm")
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        val now = System.currentTimeMillis()
        if ((now-millis)/1000 > 60*60*24*2)
            return sdf.format(Date(millis))
        return DateUtils.getRelativeTimeSpanString(millis, now, DateUtils.MINUTE_IN_MILLIS).toString()
    }
}
