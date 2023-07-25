 package com.example.calculator
 //ViewBinding,DataBinding,ButterKnife
 //For exp4j dependency which calculates maths expression uses different way to add dependency in the gradle file

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import net.objecthunter.exp4j.ExpressionBuilder


 class CalculatorApp : AppCompatActivity() {
     private lateinit var animationDrawable: AnimationDrawable

     lateinit var zero:TextView
     lateinit var one:TextView
     lateinit var two:TextView
     lateinit var three:TextView
     lateinit var four:TextView
     lateinit var five:TextView
     lateinit var six:TextView
     lateinit var seven:TextView
     lateinit var eight:TextView
     lateinit var nine:TextView

     lateinit var plus:TextView
     lateinit var minus:TextView
     lateinit var multiply:TextView
     lateinit var divide:TextView
     lateinit var modulo:TextView

     lateinit var changeSign:TextView
     lateinit var equals:TextView
     lateinit var decimal:TextView
     lateinit var AC:TextView
     lateinit var back:ImageView

     lateinit var expression:TextView
     lateinit var result:TextView


     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator_app)
         val container = findViewById<androidx.constraintlayout.widget.ConstraintLayout >(R.id.container)
         // Set the animated background to the container view
         container.setBackgroundResource(R.drawable.gradient_list)

         // Get the AnimationDrawable from the background
         animationDrawable = container.background as AnimationDrawable
         animationDrawable.setEnterFadeDuration(1500);
         animationDrawable.setExitFadeDuration(2000);
         animationDrawable.start();

         zero=findViewById(R.id.zero)
         one=findViewById(R.id.one)
         two=findViewById(R.id.two)
         three=findViewById(R.id.three)
         four=findViewById(R.id.four)
         five=findViewById(R.id.five)
         six=findViewById(R.id.six)
         seven=findViewById(R.id.seven)
         eight=findViewById(R.id.eight)
         nine=findViewById(R.id.nine)
         zero=findViewById(R.id.zero)
         zero=findViewById(R.id.zero)
         zero=findViewById(R.id.zero)
         zero=findViewById(R.id.zero)

         plus=findViewById(R.id.plus)
         minus=findViewById(R.id.minus)
         multiply=findViewById(R.id.multiply)
         divide=findViewById(R.id.divide)
         modulo=findViewById(R.id.modulo)

         changeSign=findViewById(R.id.changeSign)
         equals=findViewById(R.id.equals)
         decimal=findViewById(R.id.decimal)
         AC=findViewById(R.id.AC)
         back=findViewById(R.id.back)

         expression=findViewById(R.id.expression)
         result=findViewById(R.id.result)

//         one.setOnClickListener{
//             appendText(1,true)
//         }this is true as equals to appendhelper
         //Instead of this we are using appendhlper which is doing the same thing

         appendHelper(zero,"0",true)
         appendHelper(one,"1",true)
         appendHelper(two,"2",true)
         appendHelper(three,"3",true)
         appendHelper(four,"4",true)
         appendHelper(five,"5",true)
         appendHelper(six,"6",true)
         appendHelper(seven,"7",true)
         appendHelper(eight,"8",true)
         appendHelper(nine,"9",true)

         appendHelper(plus,"+",false)
         appendHelper(minus,"-",false)
         appendHelper(multiply,"*",false)
         appendHelper(modulo,"%",false)
         appendHelper(divide,"/",false)

         appendHelper(decimal,".",true)

         listenToClickOnEqualsOption()

         back.setOnClickListener{
             result.text=""
             result.hint=""

             //1234*34 --> 1234*3
             if(expression.text.isNotBlank()){
                 expression.text= expression.text.dropLast(1)
//                 expression.text=expression.text.substring(0..(expression.text.length-2))
                 calculate(isAfterbackSpace = true)
             }
         }
         AC.setOnClickListener{
             result.hint=""
             expression.text=""
             result.text=""
         }
         changeSign.setOnClickListener{
             result.text=""
             result.hint=""
             //-24 --> 24
             //24 -->-24

             if(expression.text.isNotBlank() && expression.text[0]=='-'){
                 expression.text=expression.text.substring(1)
             }else if(expression.text.isNotBlank()){
                 expression.text="-(${expression.text})"
             }
         }






    }

     private fun listenToClickOnEqualsOption(){
         equals.setOnClickListener{
            calculate()
         }
     }
     //5*+10  will give error
     private fun calculate(isAfterbackSpace: Boolean=false){
         if(expression.text.isBlank())return
         try {
             val expr=ExpressionBuilder(expression.text.toString()).build()
             val answer=expr.evaluate()//24.6789-->24.68//Makes roundoff//Answer is in double//Double 4.2
//                 val ansWithTwoDigits= String.format("%.2f",answer)
             val answerInt=answer.toInt()//Answer is in integer//Int 4
             val diff=answer-answerInt//Difference 0.2
             if(diff==0.0){
                 result.text=answerInt.toString() //4
             }
             else{
                 result.text=answer.toString()    //4.2
             }
//                 result.text=ansWithTwoDigits
         }catch (exception:Exception){
                if(isAfterbackSpace){
                    result.hint=""
                    result.text=""
                }else{
                    result.text=exception.message
                }

         }
     }
     private fun appendHelper(view:TextView,value:String,tobeCleared:Boolean){
         view.setOnClickListener(){
             appendText(value,tobeCleared)
         }
     }
     private fun appendText(value: String,tobeCleared: Boolean){
         if(result.text!=""){
             expression.text=""
         }
         if(tobeCleared){
             //85+10=95
             //press 2
             //Result will be cleared
             //Expression will contain 2
             result.text=""
             expression.append(value)
         }else{
             //25 as result and user presses multiply *
             //Expression becomes 25*
             //result->blank

             expression.append(result.text)
             expression.append(value)
             result.text=""
         }
         result.hint=expression.text
     }
 }