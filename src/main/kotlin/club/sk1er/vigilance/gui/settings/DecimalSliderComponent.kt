package club.sk1er.vigilance.gui.settings

import gg.essential.elementa.components.UIText
import gg.essential.elementa.constraints.*
import gg.essential.elementa.dsl.boundTo
import gg.essential.elementa.dsl.childOf
import gg.essential.elementa.dsl.constrain
import gg.essential.elementa.dsl.pixels
import gg.essential.elementa.state.toConstraint
import club.sk1er.vigilance.gui.VigilancePalette
import java.util.*

class DecimalSliderComponent(
    initialValue: Float,
    min: Float,
    max: Float, decimalPlaces: Int = 1
) : AbstractSliderComponent() {
    init {
        UIText(min.toString()).constrain {
            y = CenterConstraint()
            color = VigilancePalette.midTextState.toConstraint()
        } childOf this
    }

    override val slider = Slider((initialValue - min) / (max - min)).constrain {
        x = SiblingConstraint()
        width = 60.pixels()
        height = 12.pixels()
    } childOf this

    init {
        UIText(max.toString()).constrain {
            x = SiblingConstraint()
            y = CenterConstraint()
            color = VigilancePalette.midTextState.toConstraint()
        } childOf this
    }

    private val currentValueText = UIText(initialValue.toString()).constrain {
        x = CenterConstraint() boundTo slider.grabBox
        y = RelativeConstraint(1.5f)
        color = VigilancePalette.midTextState.toConstraint()
    } childOf slider

    init {
        slider.onValueChange { newPercentage ->
            val newValue = "%.${decimalPlaces}f".format(Locale.US,min + (newPercentage * (max - min)))
            changeValue(newValue.toFloat())
            currentValueText.setText(newValue)
        }

        sliderInit()
    }
}