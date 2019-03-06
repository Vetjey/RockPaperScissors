package com.vetjey.rockpaperscissors

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    private var numberCheckBox = 0
    private var userScore = 0
    private var botScore = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val animationLeft: Animation = AnimationUtils.loadAnimation(this, R.anim.left_up)
        val animationRight: Animation = AnimationUtils.loadAnimation(this, R.anim.right_up)

        if(savedInstanceState!=null){
            userScore = savedInstanceState.getInt("userScore", 0)
            botScore = savedInstanceState.getInt("botScore", 0)
            numberCheckBox = savedInstanceState.getInt("numberCheckBox", 0)

        }
        updateScore()
        checkBoxPressed(numberCheckBox)

        rock_image.setOnClickListener{
            checkBoxPressed(1)
            numberCheckBox = 1
        }

        scissors_image.setOnClickListener{
            checkBoxPressed(2)
            numberCheckBox = 2
        }

        paper_image.setOnClickListener{
            checkBoxPressed(3)
            numberCheckBox = 3
        }

        play_button.setOnClickListener{
            if (numberCheckBox==0){
                Toast.makeText(this, R.string.warning_toast, Toast.LENGTH_LONG).show()
            }
            if (numberCheckBox in 1..3) {
                left_hand.startAnimation(animationLeft)
                right_hand.startAnimation(animationRight)
                Handler().postDelayed({playGame()},840)
            }
        }

        refresh_button.setOnClickListener {
            userScore = 0
            you_score.text = userScore.toString()
            botScore = 0
            bot_score.text = botScore.toString()
            checkBoxPressed(0)
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("userScore", userScore)
        outState?.putInt("botScore", botScore)
        outState?.putInt("numberCheckBox", numberCheckBox)

    }

    private fun checkBoxPressed(numberCheckBox:Int){
        win_lose_text.visibility = View.INVISIBLE
        left_hand.setImageResource(R.drawable.rock_hand_left)
        right_hand.setImageResource(R.drawable.rock_hand_right)
        if (numberCheckBox == 1){
            rock_image.isChecked =true
            scissors_image.isChecked = false
            paper_image.isChecked = false
        }
        if (numberCheckBox == 2){
            scissors_image.isChecked = true
            rock_image.isChecked = false
            paper_image.isChecked = false
        }
        if (numberCheckBox == 3){
            paper_image.isChecked = true
            rock_image.isChecked = false
            scissors_image.isChecked = false
        }
    }

    private fun playGame (){
        val userHand = numberCheckBox
        val botHand = Random().nextInt(3)+1
        val odds = userHand-botHand
        when(odds){
            -2,1 -> lose()
            -1,2 -> win()
            else -> deadHeat()
        }
        left_hand.setImageResource(when(userHand){
            2-> R.drawable.scissors_hand_left
            3-> R.drawable.paper_hand_left
            else->R.drawable.rock_hand_left
        })
        right_hand.setImageResource(when(botHand){
            2-> R.drawable.scissors_hand_right
            3-> R.drawable.paper_hand_right
            else->R.drawable.rock_hand_right
        })
        updateScore()
        numberCheckBox = 0
        paper_image.isChecked = false
        rock_image.isChecked = false
        scissors_image.isChecked = false
    }

    private fun win(){
        userScore++
        win_lose_text.setText(R.string.you_win)
        win_lose_text.visibility = View.VISIBLE
    }

    private fun lose(){
        botScore++
        win_lose_text.setText(R.string.you_lose)
        win_lose_text.visibility = View.VISIBLE
    }

    private fun deadHeat(){
        win_lose_text.setText(R.string.dead_heat)
        win_lose_text.visibility = View.VISIBLE
    }

    fun updateScore(){
        you_score.text = userScore.toString()
        bot_score.text = botScore.toString()
    }
}
