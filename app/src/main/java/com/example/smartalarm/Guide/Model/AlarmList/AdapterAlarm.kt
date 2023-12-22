package com.example.smartalarm.Guide.Model.AlarmList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smartalarm.Guide.Model.Alarm
import com.example.smartalarm.databinding.ItemalarmBinding
import com.example.smartalarm.Guide.Model.AlarmList.OnToggleListener
class AdapterAlarm : RecyclerView.Adapter<AdapterAlarm.AlarmViewHolder>(){

    private var mList = ArrayList<Alarm>()
    private var OnToggleListener: OnToggleListener? = null
    private var onClickAlarmListener: OnClickAlarmListener?= null
    inner class AlarmViewHolder(var binding: ItemalarmBinding):
        RecyclerView.ViewHolder(binding.root),
            View.OnClickListener,
            View.OnLongClickListener
    {
        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        fun bind(alarm: Alarm) {
            binding.txtTime.text = alarm.getTime()
            binding.txtName.text = alarm.getAlarmTxtName()
            binding.swStart.isChecked = alarm.start
            binding.swStart.setOnCheckedChangeListener{
                btnView,isCheck ->
                alarm.start = isCheck
                OnToggleListener?.onToggle(alarm)
            }
        }

        override fun onClick(v: View?) {
            onClickAlarmListener?.onClick(mList.get(adapterPosition))
        }

        override fun onLongClick(v: View?): Boolean {
            onClickAlarmListener?.onLongClick(mList.get(adapterPosition))
            return true
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

    fun setName(it: List<Alarm>?) {
        mList = it as ArrayList<Alarm>
    }

    fun addOnToggleListener(onToggleListener: OnToggleListener) {
        this.OnToggleListener = onToggleListener
    }

    fun addOnClickAlarmListener(onClickAlarmListener: OnClickAlarmListener) {
        this.onClickAlarmListener = onClickAlarmListener
    }
}