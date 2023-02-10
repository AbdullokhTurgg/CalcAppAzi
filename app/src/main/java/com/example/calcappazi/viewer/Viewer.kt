package com.example.calcappazi.viewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.calcappazi.R
import com.example.calcappazi.control.Controller


class Viewer : AppCompatActivity() {

    private var controller: Controller

    private val workingsTV: TextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.workingsTV)
    }

    private val resultsTV: TextView by lazy(LazyThreadSafetyMode.NONE) {
        findViewById(R.id.resultsTV)
    }

    init {
        println(" Start Viewer constructor().")
        controller = Controller(viewer = this)
        println(" I am Viewer object.")
        println(" End Viewer constructor().")
    }


    @SuppressLint("DiscouragedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState != null) {
            workingsTV.text = savedInstanceState.getString(WORKING_TEXT_SAVE_KEY)
            resultsTV.text = savedInstanceState.getString(RESULT_TEXT_SAVE_KEY)
        }

        for (i in 0..12) {
            val buttonOperandId = resources.getIdentifier("btn$i", "id", packageName)
            val buttonOperand = findViewById<View>(buttonOperandId) as Button
            buttonOperand.setOnClickListener(controller)
        }

        for (i in 0..3) {
            val buttonOperandId = resources.getIdentifier("btnOperation$i", "id", packageName)
            val buttonOperand = findViewById<View>(buttonOperandId) as Button
            buttonOperand.setOnClickListener(controller)
        }

        findViewById<Button>(R.id.allClearAction).setOnClickListener(controller)

        findViewById<Button>(R.id.backSpaceAction).setOnClickListener(controller)

        findViewById<Button>(R.id.equals).setOnClickListener(controller)
    }

    fun workingTextUpdate(newText: String) = newText.also { text -> workingsTV.text = text }

    fun resultTextUpdate(newText: String) = newText.also { text -> resultsTV.text = text }

    fun clearAllText() {
        workingsTV.text = String()
        resultsTV.text = String()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(WORKING_TEXT_SAVE_KEY, workingsTV.text.toString())
        outState.putString(RESULT_TEXT_SAVE_KEY, resultsTV.text.toString())
        super.onSaveInstanceState(outState)
    }

    companion object {
        const val WORKING_TEXT_SAVE_KEY = "WORKING_TEXT_SAVE_KEY"
        const val RESULT_TEXT_SAVE_KEY = "RESULT_TEXT_SAVE_KEY"
    }

}