package com.example.audiovideocall

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatBotViewModel: ViewModel(){

    val messageList by lazy {
        mutableStateListOf<MessageModel>()
    }

    val generativeModel : GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey =  Constants.apiKey
    )
    fun sendMessage(question : String){
        //Log.i("In ChatViewModel",question)

        viewModelScope.launch {

            try{
                val chat = generativeModel.startChat(
                    //passing history
                    history = messageList.map {
                        content(it.role){text(it.message)}
                    }.toList()
                )
                // user sending question
                messageList.add(MessageModel(question,"user"))
                //messageList.add(MessageModel("Typing.....","model"))

                //model response
                val response = chat.sendMessage(question)

                messageList.add(MessageModel(response.text.toString(),"model"))
//                if (messageList.isNotEmpty()) {
//                    messageList.removeAt(messageList.size - 1) // Remove "Typing..."
//                }
                //Log.i("Response from Gemini",response.text.toString())
            }catch (e : Exception){
//                if (messageList.isNotEmpty()) {
//                    messageList.removeAt(messageList.size - 1) // Remove "Typing..."
//                }
                messageList.add(MessageModel("Error :" +e.message.toString(),"model"))
            }

        }
    }
}
