package com.example.audiovideocall


import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.audiovideocall.R.id.back_Btn
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService
import com.zegocloud.uikit.service.defines.ZegoUIKitUser

class ChatActivity : AppCompatActivity() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference
    private lateinit var profileName: TextView
    private lateinit var backButton: ImageButton
    private lateinit var audio: ImageButton
    private lateinit var video: ImageButton

    var receiverRoom : String? = null
    var senderRoom : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        profileName = findViewById(R.id.User_name)

        // Extract only the first word from the name
        val firstName = name?.split(" ")?.get(0) ?: name


        profileName.text = firstName

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        // senderuid to text
        val textsenderuid = senderUid.toString()


        chatRecyclerView = findViewById(R.id.chatRecyclerView)
        messageBox = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.sentButton)
        backButton = findViewById(R.id.back_Btn)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this,messageList)
        audio = findViewById(R.id.audio)
        video = findViewById(R.id.video)

        val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
        ZegoUIKitPrebuiltCallService.init(
            application,
            AppConstants.appId,
            AppConstants.appSign,
            textsenderuid,  // Replace with your unique user ID
            textsenderuid, // Replace with your display name
            callInvitationConfig
        )

        backButton.setOnClickListener {
            val intent = Intent(this@ChatActivity,MainActivity::class.java)
            finish()
            startActivity(intent)
        }

        // Set click listener to initiate the call
//        audio.setOnClickListener {
//            val targetUserId = receiverUid.toString() // Replace with the recipient's user ID
//            ZegoUIKitPrebuiltCallService.init(
//                listOf(targetUserId),
//                ZegoUIKitPrebuiltCallService.CallType.VIDEO // Use AUDIO or VIDEO
//            )
//        }

        //we have set mesaage also in chat recyclerview
        chatRecyclerView.layoutManager = LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        //logic for adding data in recyclerview
        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    //we have to clear previous value of message list
                    messageList.clear()

                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(p0: DatabaseError) {

                }

            })


        //adding the message to database
        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val messageObject = Message(message,senderUid)

            mDbRef.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    mDbRef.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject).addOnSuccessListener {

                        }
                }
            messageBox.setText("")
        }
    }
}