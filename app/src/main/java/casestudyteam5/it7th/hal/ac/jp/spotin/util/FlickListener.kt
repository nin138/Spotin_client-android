package casestudyteam5.it7th.hal.ac.jp.spotin.util

import android.view.MotionEvent
import android.view.View

open class FlickListener
@JvmOverloads constructor(
  private val listener: Listener,
  private val reference_distance: Float = DEFAULT_PLAY
) : View.OnTouchListener {

  private var lastX: Float = 0f
  private var lastY: Float = 0f

  //フリックイベントのリスナー

  interface Listener {
    fun onFlickToLeft()
    fun onFlickToRight()
    fun onFlickToUp()
    fun onFlickToDown()
  }

  override fun onTouch(v: View, event: MotionEvent): Boolean {
    when (event.action) {
      MotionEvent.ACTION_DOWN -> touchDown(event)
      MotionEvent.ACTION_UP -> touchOff(event)
    }
    return true
  }

  private fun touchDown(event: MotionEvent) {
    lastX = event.x
    lastY = event.y
  }

  private fun touchOff(event: MotionEvent) {
    val currentX = event.x
    val currentY = event.y

    // x -> y の順で判定しているので、斜めにフリックした場合はLeft,Rightのイベントの方が優先される
    // Up,Downを優先したい場合は、条件判定の順序を入れ替えること
    when {
        currentX + reference_distance < lastX -> listener.onFlickToLeft()
        lastX < currentX - reference_distance -> listener.onFlickToRight()
        currentY + reference_distance < lastY -> listener.onFlickToUp()
        lastY < currentY - reference_distance -> listener.onFlickToDown()
    }
  }

  companion object {
    //フリック判定の遊び
    private val DEFAULT_PLAY = 80f
  }
}
