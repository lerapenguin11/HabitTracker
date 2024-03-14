package com.example.habittracker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.google.android.material.card.MaterialCardView

class ColorPicker(
    var widthBtn : Int = 0,
    var heightBtn : Int = 0,
    val margin : Int,
    val context: Context,
    val colorLayout: LinearLayout)  {

    private val param = LinearLayout.LayoutParams(
        widthBtn,
        heightBtn
    )
    private var selectedColor : Int = Color.parseColor("#F1F6EB")

    private fun createColorPalette(): MutableList<MaterialCardView> {
        val cardList : MutableList<MaterialCardView> = mutableListOf()
        for (i in 0..15) {
            val card = MaterialCardView(context)
            val param = LinearLayout.LayoutParams(widthBtn, heightBtn)
            param.setMargins(margin, margin, margin, margin)
            card.layoutParams = param
            cardList.add(card)
        }

        return cardList
    }

    fun createColorCard(rgb: TextView, hsv: TextView) {
        val listColor = arrayListOf<Int>()
        val cardList = createColorPalette()
        rgb.setText(getColorRGBText())
        hsv.setText(getColorHSVText())
        for (positionCard in 0 until cardList.size) {
            val card = cardList[positionCard]
            card.cardElevation = 5F
            card.strokeWidth = 0
            val widthGradient = (param.width + param.leftMargin + param.rightMargin) * cardList.size
            val heightGradient = param.height + param.bottomMargin + param.topMargin
            val bitmap = colorLayout.background.toBitmap(
                widthGradient,
                heightGradient,
                Bitmap.Config.ARGB_8888
            )

            val middleColor = bitmap.getPixel(
                (widthBtn * positionCard / 2 +
                        (card.marginLeft * positionCard) + positionCard * card.marginRight),
                bitmap.height / 2
            )

            listColor.add(middleColor)

            card.setCardBackgroundColor(middleColor)
            colorLayout.addView(card)

            card.setOnClickListener {
                cardSelectionHandler(cardList, card, listColor, positionCard)
                rgb.setText(getColorRGBText())
                hsv.setText(getColorHSVText())
            }
        }
    }

    private fun cardSelectionHandler(
        cardList: MutableList<MaterialCardView>,
        card1: MaterialCardView,
        listColor: ArrayList<Int>,
        positionCard: Int
    ) {
        for(i in 0 until  cardList.size){
            val card = cardList[i]
            card1.strokeWidth = 2
            card1.strokeColor = Color.GRAY
            card1.elevation = 0F
            card.elevation = 5F
            card.strokeWidth = 0
        }
        selectedColor = listColor.get(positionCard)
    }

    fun getCardColor(): Int {
        return selectedColor
    }

    private fun hexToRGB(hex: Int): IntArray {
        val r = hex and 0xFF0000 shr 16
        val g = hex and 0xFF00 shr 8
        val b = hex and 0xFF
        return intArrayOf(r, g, b)
    }

    private fun hexToHSV(hex: Int): FloatArray {
        val hsv = FloatArray(3)
        Color.colorToHSV(hex, hsv)
        return hsv
    }

    private fun getColorHSVText() : String {
        val red = hexToHSV(getCardColor()).get(0)
        val green = hexToHSV(getCardColor()).get(1)
        val blue = hexToHSV(getCardColor()).get(2)

        return "HSV - (${red}, ${green}, ${blue})"
    }

    private fun getColorRGBText() : String{
        val red = hexToRGB(getCardColor()).get(0)
        val green = hexToRGB(getCardColor()).get(1)
        val blue = hexToRGB(getCardColor()).get(2)
        return "RGB - (${red}, ${green}, ${blue})"
    }

}