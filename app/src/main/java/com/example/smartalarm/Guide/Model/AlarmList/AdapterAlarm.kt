package com.example.smartalarm.Guide.Model.AlarmList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.databinding.ItemalarmBinding
import com.example.smartalarm.Guide.Model.AlarmList.OnToggleListener
class AdapterAlarm(var onToggleL: OnToggleListener) : RecyclerView.Adapter<AdapterAlarm.AlarmViewHolder>(){

    private var mList = ArrayList<Alarm>()

    inner class AlarmViewHolder(var binding: ItemalarmBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(alarm: Alarm) {
            binding.txtTime.text = alarm.getTime()
            binding.swStart.isChecked = alarm.start
            binding.swStart.setOnCheckedChangeListener{
                btnView,isCheck ->
                alarm.start = isCheck
                onToggleL.onToggle(alarm)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        return AlarmViewHolder(ItemalarmBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(mList.get(position))
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setData(it: List<Alarm>?) {
        mList = it as ArrayList<Alarm>
        notifyDataSetChanged()

    }
}