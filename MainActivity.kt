package com.example.timer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import com.example.timer.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    val binding by lazy{ ActivityMainBinding.inflate(layoutInflater)}
    var total = 0//전체 시간 저장
    var started = false//시작됐는지 체크
    val handler = object : Handler(Looper.getMainLooper()){
        override fun handleMessage(msg: Message) {
            val minute = String.format("%02d", total/60)
            val second = String.format("%02d", total%60)
            binding.textTimer.text = "$minute:$second"
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.buttonStart.setOnClickListener {
            started = true
            thread(start=true){
                while(started){
                    Thread.sleep(1000)//1초 동안 기다리기
                    if(started){
                        total += 1
                        handler?.sendEmptyMessage(0)//핸들러 호출하는 곳은 하나. -> 메시지에 0담기
                    }
                }
            }
        }

        binding.buttonStop.setOnClickListener {
            if(started){
                started =false
                total = 0
                binding.textTimer.text="00:00"
            }
        }
    }
}