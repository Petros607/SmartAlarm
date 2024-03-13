package com.example.smartalarm.Guide.Model.StopwatchGuide

import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
//import com.example.smartalarm.activities.SimpleActivity
import com.example.smartalarm.databinding.ItemLapBinding
import com.example.smartalarm.Guide.Model.StopwatchGuide.Lap
//import com.example.smartalarm.commons.MyRecyclerViewAdapter
import com.example.smartalarm.commons.MyRecyclerView
import java.util.concurrent.TimeUnit

const val SORT_BY_LAP = 1
const val SORT_BY_LAP_TIME = 2
const val SORT_BY_TOTAL_TIME = 4
//
//fun Long.formatStopwatchTime(useLongerMSFormat: Boolean): String {
//    val MSFormat = if (useLongerMSFormat) "%03d" else "%01d"
//    val hours = TimeUnit.MILLISECONDS.toHours(this)
//    val minutes = TimeUnit.MILLISECONDS.toMinutes(this) - TimeUnit.HOURS.toMinutes(hours)
//    val seconds = TimeUnit.MILLISECONDS.toSeconds(this) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(this))
//    var ms = this % 1000
//    if (!useLongerMSFormat) {
//        ms /= 100
//    }
//
//    return when {
//        hours > 0 -> {
//            val format = "%02d:%02d:%02d.$MSFormat"
//            String.format(format, hours, minutes, seconds, ms)
//        }
//
//        minutes > 0 -> {
//            val format = "%02d:%02d.$MSFormat"
//            String.format(format, minutes, seconds, ms)
//        }
//
//        else -> {
//            val format = "%d.$MSFormat"
//            String.format(format, seconds, ms)
//        }
//    }
//}
//class StopwatchAdapter(activity: SimpleActivity, var laps: ArrayList<Lap>, recyclerView: MyRecyclerView, itemClick: (Any) -> Unit) :
//    MyRecyclerViewAdapter(activity, recyclerView, itemClick) {
//    private var lastLapTimeView: TextView? = null
//    private var lastTotalTimeView: TextView? = null
//    private var lastLapId = 0
//
//    override fun getActionMenuId() = 0
//
//    override fun prepareActionMode(menu: Menu) {}
//
//    override fun actionItemPressed(id: Int) {}
//
//    override fun getSelectableItemCount() = laps.size
//
//    override fun getIsItemSelectable(position: Int) = false
//
//    override fun getItemSelectionKey(position: Int) = laps.getOrNull(position)?.id
//
//    override fun getItemKeyPosition(key: Int) = laps.indexOfFirst { it.id == key }
//
//    override fun onActionModeCreated() {}
//
//    override fun onActionModeDestroyed() {}
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return createViewHolder(ItemLapBinding.inflate(layoutInflater, parent, false).root)
//    }
//
//    override fun onBindViewHolder(holder: MyRecyclerViewAdapter.ViewHolder, position: Int) {
//        val lap = laps[position]
//        holder.bindView(lap, false, false) { itemView, layoutPosition ->
//            setupView(itemView, lap)
//        }
//        bindViewHolder(holder)
//    }
//
//    override fun getItemCount() = laps.size
//
//    fun updateItems(newItems: ArrayList<Lap>) {
//        lastLapId = 0
//        laps = newItems.clone() as ArrayList<Lap>
//        laps.sort()
//        notifyDataSetChanged()
//        finishActMode()
//    }
//
//    fun updateLastField(lapTime: Long, totalTime: Long) {
//        lastLapTimeView?.text = lapTime.formatStopwatchTime(false)
//        lastTotalTimeView?.text = totalTime.formatStopwatchTime(false)
//    }
//
//    private fun setupView(view: View, lap: Lap) {
//        ItemLapBinding.bind(view).apply {
//            lapOrder.text = lap.id.toString()
//            lapOrder.setTextColor(textColor)
//            lapOrder.setOnClickListener {
//                itemClick(SORT_BY_LAP)
//            }
//
//            lapLapTime.text = lap.lapTime.formatStopwatchTime(false)
//            lapLapTime.setTextColor(textColor)
//            lapLapTime.setOnClickListener {
//                itemClick(SORT_BY_LAP_TIME)
//            }
//
//            lapTotalTime.text = lap.totalTime.formatStopwatchTime(false)
//            lapTotalTime.setTextColor(textColor)
//            lapTotalTime.setOnClickListener {
//                itemClick(SORT_BY_TOTAL_TIME)
//            }
//
//            if (lap.id > lastLapId) {
//                lastLapTimeView = lapLapTime
//                lastTotalTimeView = lapTotalTime
//                lastLapId = lap.id
//            }
//        }
//    }
//}
