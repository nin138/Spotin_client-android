package casestudyteam5.it7th.hal.ac.jp.spotin.view.addrecord

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import casestudyteam5.it7th.hal.ac.jp.spotin.R
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

    presenter = AddRecordPresenter(applicationContext, this, DBFactory.provideSpotRepository(this))
    val placeName: TextView = findViewById(R.id.placeName)
    placeName.text = place_name

    showImageList(presenter.getImageList())

    //決定ボタン押下
    decision.setOnClickListener { decision() }
    //キャンセルボタン押下
    canselBtn.setOnClickListener { finish() }
    //画像選択処理
    cameraBtn.setOnClickListener { permissionCheck(CAMERACODE) }
    storageBtn.setOnClickListener { permissionCheck(STORAGECODE) }
    //
  }

  private fun permissionCheck(checkCode: Int) {
    PermissionUtil.RequestBuilder
      .withActivity(this)
      .permissions(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE)
      .rationale("req", "sfhakle;ig[wjnegiubehgneokeroihgiowhiogq") { finish() } //todo
      .onPermissionDenied { finish() } //todo
      .onPermissionGranted { takeImage(checkCode) }
      .build()
      .check()
  }

  override val isUpdate: Boolean
    get() = intent.extras.getBoolean("update")//遷移元から更新か新規かのフラグを受け取る

  override fun takeImage(checkCode: Int) {
    when (checkCode) {
      CAMERACODE -> {
        imageUri = presenter.getCameraUri(context = this)
        imageUri?.let {
          val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).apply { putExtra(MediaStore.EXTRA_OUTPUT, it) }
          val intent = Intent.createChooser(cameraIntent, "take Image")
          startActivityForResult(intent, CHOOSERS)
        }
      }

      STORAGECODE -> {
        val galleryIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
          addCategory(Intent.CATEGORY_OPENABLE)
          type = "image/*"
        }
        val intent = Intent.createChooser(galleryIntent, "take Image")
        startActivityForResult(intent, CHOOSERS)
      }
    }
  }

  override fun showImageList(imagepassList: List<TravelRecord.SpotImage>) {
    recycleAddImageView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    recycleAddImageView.adapter = AddRecordRecyclerAdapter(this, this, imagepassList)
  }

  override fun onItemClick(view: View, position: Int) {
    presenter.deleteListPosition(position)
  }

  override fun editComment() {
    val editText: EditText = findViewById(R.id.editComment)
    editText.text?.let { this.comment = it.toString() }
  }

  override fun showEmptyError() {
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
      showEmptyError()
    } else {
      presenter.saveRecord(this.place_id, this.comment, this.place_name)
      Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
      finish()
    }
  }

  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == CHOOSERS) {
      data?.data?.let { imageUri = it } ?: kotlin.run { if (!presenter.isFileExist(imageUri)) return }
      //TODO: presenterにリスト捜査用のメソッド追加
      presenter.editImageList(presenter.createImageSpot(place_id, imageUri))
      presenter.showImage()
    }
  }

  companion object {
    const val CHOOSERS = 1000
    const val CAMERACODE = 100
    const val STORAGECODE = 200
  }
}
