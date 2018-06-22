package casestudyteam5.it7th.hal.ac.jp.spotin.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog

class PermissionUtil {
  companion object {
    fun isPermissionGranted(context: Context, permission: String): Boolean {
      return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
    private fun showAlertDialog(
      context: Context,
      title: String,
      msg: String,
      callback: DialogInterface.OnClickListener
    ) {
      AlertDialog.Builder(context)
        .setTitle(title)
        .setMessage(msg)
        .setPositiveButton(android.R.string.ok, callback)
        .create().show()
    }

    fun requestPermission(
      activity: Activity,
      permission: String,
      requestCode: Int,
      reasonTitle: String,
      reason: String
    ) {
      fun requestPermission() {
        ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
      }
      if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
        showAlertDialog(activity, reasonTitle, reason,
          DialogInterface.OnClickListener { _, _ -> requestPermission() })
      } else requestPermission()
    }
    fun requestPermission(
      fragment: Fragment,
      permission: String,
      requestCode: Int,
      reasonTitle: String,
      reason: String
    ) {
      fun requestPermission() {
        fragment.requestPermissions(arrayOf(permission), requestCode)
      }
      val context = fragment.requireContext()
      if (!isPermissionGranted(context, permission)) {
        if (fragment.shouldShowRequestPermissionRationale(permission)) {
          showAlertDialog(context, reasonTitle, reason,
            DialogInterface.OnClickListener { _, _ ->
              requestPermission()
            })
        } else requestPermission()
      }
    }
  }
}
