package com.example.audiovideocall

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.audiovideocall.ui.theme.ColorModelMessage
import com.example.audiovideocall.ui.theme.ColorUserMessage
import com.example.audiovideocall.ui.theme.Pink80

@Composable
fun ChatPage(modifier: Modifier = Modifier,viewModel: ChatBotViewModel) {
    //Text(text = "Chat Page")
    Column (
        modifier = modifier.fillMaxSize()
    ){
        AppHeader()
        Box(modifier = Modifier.weight(1f)){
            MessageList(messageList = viewModel.messageList)
        }

        MessageInput(onMessageSend = {
            viewModel.sendMessage(it)
        })

        }
    }

@Composable
fun MessageList(modifier: Modifier = Modifier,messageList: List<MessageModel>) {
    if(messageList.isEmpty()){
        Column (
            modifier = modifier.fillMaxSize(),
            horizontalAlignment =  Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Icon(
                modifier = Modifier.size(60.dp),
                painter = painterResource(R.drawable.question_answer),
                contentDescription = "Icon",
                tint = ColorUserMessage
            )
            Text(text = "Ask me anything", fontSize = 22.sp)
        }
    }else {
        LazyColumn(
            modifier = modifier,
            reverseLayout = true
        ) {
            items(messageList.reversed()) {
                MessageRow(messageModel = it)
            }
        }
    }
}

@Composable
fun MessageRow(messageModel : MessageModel) {

    //left and right for user and model
     val isModel = messageModel.role=="model"

    Row(
        verticalAlignment = Alignment.CenterVertically
    ){
        Box(modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.align(if(isModel) Alignment.BottomStart else Alignment.BottomEnd)
                        .padding(
                            // if user we adding first to padding
                            // if model we adding padding in end
                            start = if(isModel)8.dp else 70.dp,
                            end = if(isModel)70.dp else 8.dp,
                            top = 8.dp,
                            bottom = 8.dp
                        )
                    .clip(RoundedCornerShape(48f))
                    .background(if(isModel) ColorModelMessage else ColorUserMessage)
                    .padding(16.dp)
                )
                {
                // jate amra message gula copy krte pari
                SelectionContainer {
                    Text(
                        text = messageModel.message,
                        fontWeight = FontWeight.W500,
                        fontFamily = FontFamily.Monospace
                    )
                }

                }

        }
    }

}

@Composable
fun MessageInput(onMessageSend :(String)-> Unit) {

    var message by remember {
        mutableStateOf("")
    }
    Row (
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            modifier = Modifier.weight(1f),
            value= message,
            onValueChange ={
                message = it
            }
        )
        IconButton(onClick = {
            if(message.isNotEmpty()) {
                onMessageSend(message)
                message = ""
            }
        }) {
            Icon( imageVector = Icons.Default.Done,
                contentDescription = "Send")
        }
    }

}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ColorUserMessage)
    ){
        Text(modifier = Modifier.padding(16.dp),
            text = "Chat Bot",
            color = Color.White,
            fontSize = 22.sp
        )
    }
}

