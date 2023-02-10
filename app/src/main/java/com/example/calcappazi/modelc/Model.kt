package com.example.mvccalculator.model

import com.example.calcappazi.viewer.Viewer

class Model {

    private var isAddOperation: Boolean

    private var workingText: String

    private var viewer: Viewer

    private val calculatorRPN: CalculatorRPN

    private val errorMessage: String

    constructor(viewer: Viewer) {
        println("                Start Controller constructor( ).")
        this.viewer = viewer
        calculatorRPN = CalculatorRPN()
        workingText = String()
        isAddOperation = false
        errorMessage = "Ошибка ввода!"
        println("                I am Controller object.")
        println("                End Controller constructor().")
    }


    fun operationAction(operationSymbol: String) {
        if (isAddOperation) {
            workingText += operationSymbol
            viewer.workingTextUpdate(newText = workingText)
            isAddOperation = false
        }
    }

    fun numberAction(numberSymbol: String) {
        if (numberSymbol == "." && workingText.isEmpty()) return
        workingText += numberSymbol
        viewer.workingTextUpdate(newText = workingText)
        isAddOperation = true
    }


    fun backSpaceAction() {
        val length = workingText.length
        if (length > 0) {
            workingText = workingText.subSequence(0, length - 1).toString()
            viewer.workingTextUpdate(newText = workingText)
        }
    }

    fun calculateResults() {
        if (workingText.contains("x") || workingText.contains("+") ||
            workingText.contains("-") || workingText.contains("/")
        ) {
            val expressionToRPN =
                calculatorRPN.expressionToRPN(expression = workingText, errorMessage = errorMessage)

            if (expressionToRPN == errorMessage) {
                viewer.resultTextUpdate(newText = errorMessage)
                return
            }

            val rpnResult =
                calculatorRPN.rpnToAnswer(rpn = expressionToRPN, errorMessage = errorMessage)

            val result = if (rpnResult == errorMessage) errorMessage
            else if (rpnResult.contains(".0")) rpnResult.toDouble().toInt().toString()
            else rpnResult

            viewer.resultTextUpdate(result)

        } else viewer.resultTextUpdate(newText = workingText)
    }


    fun clearData() {
        workingText = String()
        viewer.clearAllText()
    }
}