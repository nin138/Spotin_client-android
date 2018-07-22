package casestudyteam5.it7th.hal.ac.jp.spotin.view.records

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.Calendar

class CustomDialogFlagment : DialogFragment() {

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val calendar = Calendar.getInstance()

    return DatePickerDialog(activity,
      DatePickerDialog.OnDateSetListener { _: DatePicker?, year, month, dayOfMonth ->
        //月は0-11なので+1している
        val strMonth = String.format("%02d", month + 1)
        val value = "$year/$strMonth/$dayOfMonth"
        val date = SimpleDateFormat("yyyy/MM/dd").parse(value)
        Log.d("calender", date.toString())
        val travelRecordListActivity = activity as TravelRecordListActivity
        //データソート呼び出し
        travelRecordListActivity.showSortDays(date)
      },
      calendar.get(Calendar.YEAR), // 初期選択年
      calendar.get(Calendar.MONTH), // 初期選択月
      calendar.get(Calendar.DAY_OF_MONTH) // 初期選択日
    )
  }
}
