package casestudyteam5.it7th.hal.ac.jp.spotin.util

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import com.karumi.dexter.Dexter
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

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

  class RequestBuilder private constructor(private val activity: Activity) {

    private var permission: String? = null
    private var permissions: List<String>? = null
    private var title: String? = null
    private var msg: String? = null
    private var onCancel: (() -> Unit)? = null
    private var onGranted: ((List<PermissionGrantedResponse>?) -> Unit)? = null
    private var onDenied: ((List<PermissionDeniedResponse>?) -> Unit)? = null

    companion object {
      fun withActivity(activity: Activity): RequestBuilder {
        return RequestBuilder(activity)
      }
    }

    fun permission(permission: String): RequestBuilder {
      this.permission = permission
      return this
    }

    fun permissions(permissions: List<String>): RequestBuilder {
      this.permissions = permissions
      return this
    }

    fun rationale(title: String, message: String, onCancel: () -> Unit): RequestBuilder {
      this.title = title
      this.msg = message
      this.onCancel = onCancel
      return this
    }

    fun onPermissionDenied(onDenied: (List<PermissionDeniedResponse>?) -> Unit): RequestBuilder {
      this.onDenied = onDenied
      return this
    }

    fun onPermissionGranted(onGranted: (List<PermissionGrantedResponse>?) -> Unit): RequestBuilder {
      this.onGranted = onGranted
      return this
    }

    fun build(): DexterBuilder {
      when {
        this.permission != null -> return Dexter.withActivity(activity)
          .withPermission(permission)
          .withListener(object : PermissionListener {
            override fun onPermissionRationaleShouldBeShown(p0: PermissionRequest?, token: PermissionToken?) {
              showDialog(token)
            }
            override fun onPermissionDenied(res: PermissionDeniedResponse?) {
              onDenied?.let { it(if(res != null)listOf(res) else listOf()) }
            }
            override fun onPermissionGranted(res: PermissionGrantedResponse?) {
              onGranted?.let { it(if(res != null)listOf(res) else listOf()) }
            }
          }).withErrorListener { e -> Log.e("ERR::PERMISSION", e.toString()) }

        this.permissions?.isNotEmpty() == true -> return Dexter.withActivity(activity)
          .withPermissions(permissions).withListener(object : MultiplePermissionsListener {
            override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken?) {
              showDialog(token)
            }
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
              if(report?.deniedPermissionResponses?.isNotEmpty() == true) onDenied?.let { it(report.deniedPermissionResponses) }
              else onGranted?.let { it(report?.grantedPermissionResponses) }
            }
          }).withErrorListener { e -> Log.e("ERR::PERMISSION", e.toString()) }
        else -> throw Error("Permission not selected")
      }
    }
    private fun showDialog(token: PermissionToken?) {
      AlertDialog.Builder(activity)
        .setTitle(title)
        .setMessage(msg)
        .setCancelable(false)
        .setNegativeButton(android.R.string.cancel) { dialog: DialogInterface, i: Int ->
          dialog.dismiss()
          token?.cancelPermissionRequest()
          onCancel?.let { it() }
        }
        .setPositiveButton(android.R.string.ok) { dialog: DialogInterface, _ ->
          dialog.dismiss()
          token?.continuePermissionRequest()
        }
        .show()
    }
  }
}
