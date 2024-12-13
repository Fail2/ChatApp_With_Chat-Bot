package com.example.audiovideocall

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.addTextChangedListener
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

class CallActivity : AppCompatActivity() {



    private lateinit var receiver: EditText
    private lateinit var voice: ZegoSendCallInvitationButton
    private lateinit var video: ZegoSendCallInvitationButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_call)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        receiver = findViewById(R.id.receiver_name)
        voice =findViewById(R.id.voice)
        video = findViewById(R.id.video)



        receiver.addTextChangedListener{
            val Name = receiver.text.toString()
            setUpVoice(Name)
            setUpVideo(Name)
        }
    }


    fun setUpVoice(name: String){
        voice.setIsVideoCall(false)
        voice.resourceID = "zego_uikit_call"
        voice.setInvitees(
            listOf<ZegoUIKitUser>(
                ZegoUIKitUser(
                    name,
                    name
                )
            ))
    }

    fun setUpVideo(name: String){
        video.setIsVideoCall(true)
        video.resourceID = "zego_uikit_call"
        video.setInvitees(
            listOf<ZegoUIKitUser>(
                ZegoUIKitUser(
                    name,
                    name
                )
            ))

    }

}