package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import org.w3c.dom.Text
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.roundToInt

class CalculatorActivity : AppCompatActivity() {
    lateinit var zero : TextView
    lateinit var one : TextView
    lateinit var two : TextView
    lateinit var three : TextView
    lateinit var four : TextView
    lateinit var five : TextView
    lateinit var six : TextView
    lateinit var seven : TextView
    lateinit var eight : TextView
    lateinit var nine : TextView

    lateinit var add : TextView
    lateinit var minus : TextView
    lateinit var divide : TextView
    lateinit var multiply : TextView

    lateinit var modulo : TextView
    lateinit var equal : TextView
    lateinit var changeSign : TextView
    lateinit var decimal : TextView

    lateinit var ac : TextView
    lateinit var back : ImageView

    lateinit var result : TextView
    lateinit var expression : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        one = findViewById(R.id.one)
        two = findViewById(R.id.two)
        three = findViewById(R.id.three)
        four = findViewById(R.id.four)
        five = findViewById(R.id.five)
        six = findViewById(R.id.six)
        seven = findViewById(R.id.seven)
        eight = findViewById(R.id.eight)
        nine = findViewById(R.id.nine)
        zero = findViewById(R.id.zero)

        add = findViewById(R.id.add)
        minus = findViewById(R.id.subtract)
        multiply = findViewById(R.id.multiply)
        divide = findViewById(R.id.divide)

        modulo = findViewById(R.id.modulo)
        equal = findViewById(R.id.equals)
        changeSign = findViewById(R.id.change_sign)
        decimal = findViewById(R.id.decimal)

        ac = findViewById(R.id.ac)
        back = findViewById(R.id.back)

        result = findViewById(R.id.result)
        expression = findViewById(R.id.expression)

        appendHelper(one,"1",true)
        appendHelper(two,"2",true)
        appendHelper(three, "3",true)
        appendHelper(four,"4",true)
        appendHelper(five,"5",true)
        appendHelper(six,"6",true)
        appendHelper(seven,"7",true)
        appendHelper(eight, "8",true)
        appendHelper(nine, "9",true)
        appendHelper(zero,"0",true)

        appendHelper(add,"+",false)
        appendHelper(minus,"-",false)
        appendHelper(multiply, "*",false)
        appendHelper(divide,"/",false)
        appendHelper(modulo, "%", false)

        appendHelper(decimal, ".", true)

        equal.setOnClickListener{
           try {
               val expr = ExpressionBuilder(expression.text.toString()).build()
               var ans = expr.evaluate()
               ans = roundOffDecimal(ans)
               result.text = "$ans"
           }catch (exception : Exception){
               result.text = exception.message
           }
        }

        back.setOnClickListener{
            result.text = ""
            result.hint = ""

            if(expression.text.isNotEmpty()){
                expression.text = expression.text.substring(0, expression.length()-1)
            }
        }

        ac.setOnClickListener{
            result.text = ""
            expression.text = ""
            result.hint = ""
        }

        changeSign.setOnClickListener{
            result.text = ""
            result.hint = ""

            if (expression.text.isNotEmpty() && expression.text[0] == '-') {
                expression.text = expression.text.substring(1)
            } else {
                expression.text = "-${expression.text}"
            }
        }
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toDouble()
    }
    private fun appendHelper(view: TextView, value: String, toBeCleared: Boolean) {
        view.setOnClickListener {
            appendText(value, toBeCleared)
        }
    }



    private fun appendText(value: String, toBeCleared: Boolean)
    {
        if(result.text!="")
        {
            expression.text=""
        }

        if (toBeCleared)
        {
            result.text=""
            expression.append(value);
        }
        else
        {
            expression.append(result.text)
            expression.append(value)
            result.text=""
        }

        result.hint = expression.text
    }
}