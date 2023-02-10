package com.example.mvccalculator.model

import java.lang.Exception
import java.util.*

class CalculatorRPN {

    fun expressionToRPN(expression: String, errorMessage: String): String = try {
        val stack = Stack<Char>()
        var calculateText = String()

        for (element in expression) {
            val priority = getPriority(token = element)

            if (priority == 0) calculateText += element
            else if (priority == 1) stack.push(element)
            else if (priority > 1) {
                calculateText += ' '
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority) calculateText += stack.pop() else break
                }
                stack.push(element)
            } else if (priority == -1) {
                calculateText += ' '
                while (getPriority(token = stack.peek()) != 1) calculateText += stack.pop()
                stack.pop()
            }
        }

        while (!stack.empty()) calculateText += stack.pop()

        calculateText
    } catch (e: Exception) { errorMessage }


    fun rpnToAnswer(rpn: String, errorMessage: String): String = try {
        var operand = String()
        val stack = Stack<Double>()
        var i = 0
        while (i < rpn.length) {
            if (rpn[i] == ' ') {
                i++
                continue
            }
            if (getPriority(rpn[i]) == 0) {
                while (rpn[i] != ' ' && getPriority(rpn[i]) == 0) {
                    operand += rpn[i++]
                    if (i == rpn.length) break
                }
                stack.push(operand.toDouble())
                operand = String()
            }
            if (getPriority(rpn[i]) > 1) {
                val a = stack.pop()
                val b = stack.pop()
                if (rpn[i] == '+') stack.push(b + a)
                if (rpn[i] == '-') stack.push(b - a)
                if (rpn[i] == 'x') stack.push(b * a)
                if (rpn[i] == '/') stack.push(b / a)
            }
            i++
        }
        stack.pop().toString()


    } catch (e: Exception) { errorMessage }

    private fun getPriority(token: Char): Int = when (token) {
        'x', '/' -> 3
        '+', '-' -> 2
        '(' -> 1
        ')' -> -1
        else -> 0
    }

}