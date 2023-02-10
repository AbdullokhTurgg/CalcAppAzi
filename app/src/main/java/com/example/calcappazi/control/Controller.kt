package com.example.calcappazi.control

import android.view.View
import android.widget.Button
import com.example.calcappazi.R
import com.example.mvccalculator.model.Model
import com.example.calcappazi.viewer.Viewer

class Controller : View.OnClickListener {

    private var model: Model

    constructor(viewer: Viewer) {
        println("       Start Controller constructor().")
        model = Model(viewer = viewer)
        println("       I am Controller object.")
        println("       End Controller constructor().")

    }

    override fun onClick(view: View) {
        val button = view as Button
        val context = view.context

        when (button.id) {
            R.id.allClearAction -> model.clearData()
            R.id.backSpaceAction -> model.backSpaceAction()
            R.id.equals -> model.calculateResults()
        }

        for (i in 0..3) {
            val tmpOperandId =
                context.resources.getIdentifier("btnOperation$i", "id", context.packageName)
            if (tmpOperandId == button.id) model.operationAction(operationSymbol = button.text.toString())
        }

        for (i in 0..12) {
            val tmpOperandId = context.resources.getIdentifier("btn$i", "id", context.packageName)
            if (tmpOperandId == button.id) model.numberAction(numberSymbol = button.text.toString())

        }
    }
}