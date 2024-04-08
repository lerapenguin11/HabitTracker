package com.example.habittracker

import android.content.Context
import android.graphics.Color
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.presentation.adapter.ColorPickerAdapter
import com.google.android.material.card.MaterialCardView

class ColorPicker(
    var widthBtn: Int = 0,
    var heightBtn: Int = 0, //плохо
    val margin: Int,
    val context: Context, //плохо
    val colorLayout: LinearLayout,
    rvColorPicker: RecyclerView
)  {

    private val param = LinearLayout.LayoutParams(
        widthBtn,
        heightBtn
    )
    private var selectedColor : Int = Color.parseColor("#F1F6EB")
    private val adapter = ColorPickerAdapter()

    private fun createColorPalette(): MutableList<Int> {
        val cardList : MutableList<MaterialCardView> = mutableListOf()

        val list : MutableList<Int> = mutableListOf()
        for (i in 0..15) {
            val card = MaterialCardView(context)
            val param = LinearLayout.LayoutParams(widthBtn, heightBtn)
            param.setMargins(margin, margin, margin, margin)
            card.layoutParams = param
            cardList.add(card)
        }

        return list
    }

    fun createColorCard(rgb: TextView, hsv: TextView): MutableList<Int> {
        val listColor = arrayListOf<Int>()
        val cardList = createColorPalette()
        rgb.setText(getColorRGBText())
        hsv.setText(getColorHSVText())


        return cardList
        /*for (positionCard in 0 until cardList.size) {
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
        }*/
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