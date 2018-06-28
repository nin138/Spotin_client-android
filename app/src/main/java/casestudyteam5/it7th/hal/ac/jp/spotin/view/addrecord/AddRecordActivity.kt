package casestudyteam5.it7th.hal.ac.jp.spotin.view.addrecord

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

import casestudyteam5.it7th.hal.ac.jp.spotin.R
import casestudyteam5.it7th.hal.ac.jp.spotin.casestudyteam5.it7th.hal.ac.jp.spotin.addrecord.AddRecordRecyclerAdapter
import casestudyteam5.it7th.hal.ac.jp.spotin.data.DBFactory
import casestudyteam5.it7th.hal.ac.jp.spotin.data.TravelRecord
import casestudyteam5.it7th.hal.ac.jp.spotin.util.PermissionUtil
import kotlinx.android.synthetic.main.activity_add_record.*

class AddRecordActivity :
  AppCompatActivity(),
  AddRecordContract.View,
  AddRecordRecyclerAdapter.ViewHolder.OnItemClickListener {

  lateinit var presenter: AddRecordPresenter
  val place_id: String by lazy { intent.extras.getString("place_id") }
  val place_name: String by lazy { intent.extras.getString("place_name") }
  var comment: String = ""
  private var imageUri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_add_record)

    presenter = AddRecordPresenter(this, DBFactory.provideSpotRepository(this))
    val placeName: TextView = findViewById(R.id.placeName)
    placeName.text = place_name

    showImageList(presenter.getImageList())

    val chooseBtn: Button = findViewById(R.id.takeImage)
    chooseBtn.setOnClickListener {
      //permission確認後カメラ起動
      selfcheckPermmision()
    }
    val decisionbtn: Button = findViewById(R.id.decision)
    decisionbtn.setOnClickListener { decision() }
  }

  override val isUpdate: Boolean
    get() = intent.extras.getBoolean("update")//遷移元から更新か新規かのフラグを受け取る

  override fun selfcheckPermmision() {
    if (PermissionUtil.isPermissionGranted(this, Manifest.permission.CAMERA)) {
      if (PermissionUtil.isPermissionGranted(this, Manifest.permission_group.STORAGE)) takeImage()
      else PermissionUtil.requestPermission(this, Manifest.permission_group.STORAGE, CAMERAPERMISSIONS,
        "get Permission Error",
        "To retry please press the OK button again")
      PermissionUtil.requestPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE, CAMERAPERMISSIONS,
        "get Permission Error",
        "To retry please press the OK button again")
    } else PermissionUtil.requestPermission(this, Manifest.permission.CAMERA, CAMERAPERMISSIONS,
      "get Permission Error",
      "To retry please press the OK button again")
    /*
    // パーミッションの確認
    if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
      if (ContextCompat.checkSelfPermission(this,
          Manifest.permission_group.STORAGE) == PackageManager.PERMISSION_GRANTED) {
        takeImage()
        return
      }
    }

    // 過去にリクエストが蹴られている場合
    if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
      //パーミッションをリクエストします。
      AlertDialog.Builder(this)
        .setPositiveButton(android.R.string.ok) { _, _ ->
          // OKボタンがタップしパーミッションをリクエスト
          ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.CAMERA), CAMERAPERMISSIONS)
        }
        .show()
      return
    }

    // パーミッションをリクエスト
    ActivityCompat.requestPermissions(this,
      arrayOf(Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE), CAMERAPERMISSIONS)
        */
  }

  override fun takeImage() {
    //カメラ,ギャラリー呼び出し
    imageUri = presenter.getcameraUri(context = this)
    imageUri?.let {
      val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply { putExtra(MediaStore.EXTRA_OUTPUT, it) }
      val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
        addCategory(Intent.CATEGORY_OPENABLE)
        type = "image/*"
      }
      val intent = Intent.createChooser(cameraIntent, "take Image").apply {
        putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(galleryIntent)) }
      startActivityForResult(intent, CHOOSERS)
    }
  }

  override fun showImageList(imagepassList: List<TravelRecord.SpotImage>) {
    recycleAddImageView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    recycleAddImageView.adapter = AddRecordRecyclerAdapter(this, this, imagepassList)
  }

  override fun onItemClick(view: View, position: Int) {
    presenter.deleteListPositon(position)
  }

  override fun editComment() {
    val editText: EditText = findViewById(R.id.editComment)
    editText.text?.let { this.comment = it.toString() }
  }

  override fun showEmpryError() {
    Toast.makeText(this, "empty", Toast.LENGTH_SHORT).show()
  }

  override fun cancel() {
    editComment()
    if (!this.comment.isEmpty() || this.imageUri != null) {
      AlertDialog.Builder(this)
        .setTitle("Editing in progress")
        .setMessage("Would you like to cancel editing?")
        .setPositiveButton(android.R.string.ok) { _, _ ->
          //TODO:遷移元に戻る
          finish()
        }
        .create()
        .show()
    }
  }

  override fun decision() {
    //TODO:決定ボタン押下後空チェック後遷移　
    editComment()
    if (this.comment.isEmpty() && this.imageUri == null) {
      showEmpryError()
    } else {
      presenter.saveRecord(this.place_id, this.comment, this.place_name)
      Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
      finish()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == CHOOSERS) {
      data?.data?.let { imageUri = it }
      //TODO: presenterにリスト捜査用のメソッド追加
      presenter.editImageList(presenter.createImageSpot(place_id, imageUri))
    }
  }

  override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
    if (requestCode == CAMERAPERMISSIONS &&
      grantResults.isNotEmpty() &&
      grantResults[0] == PackageManager.PERMISSION_GRANTED) {
      //takeImage()
      selfcheckPermmision()
    } else return
    /*if (requestCode == CAMERAPERMISSIONS) {
      if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
          AlertDialog.Builder(this)
            .setTitle("get Permission Error")
            .setMessage("To retry please press the OK button again")
            .setPositiveButton(android.R.string.ok) { _, _ ->
              ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA), CAMERAPERMISSIONS)
            }
            .create()
            .show()
        } else {
          AlertDialog.Builder(this)
            .setTitle("get Permission Error")
            .setMessage("I will not allow it in the future but it was selected.")
            .setPositiveButton(android.R.string.ok) { _, _ ->
              //TODO:設定画面への遷移
            }
            .create()
            .show()
        }
      } else {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        takeImage()
      }
    }*/
  }

  companion object {
    const val CHOOSERS = 1000
    const val CAMERAPERMISSIONS = 2000
  }
}
