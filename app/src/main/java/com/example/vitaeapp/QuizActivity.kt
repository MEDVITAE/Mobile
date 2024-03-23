package com.example.vitaeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.ui.theme.VitaeAppTheme

class QuizActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.azul_claro)
                ) {
                    TelaQuiz()
                }
            }
        }
    }
}

@Composable
fun TelaQuiz() {
    Logo()
    Column {

    }
    Menu()
}

@Composable
fun Questionario() {
    val pergunta = remember {
        mutableStateListOf(
            "Informe seu peso:",
            "Informe sua altura:",
            "Fez alguma tatuagem nos últimos 6 meses?",
            "Teve algum tipo de relação sexual recentemente?",
            "Sente algum desconforto, ou dor na barriga?",
            "Faz ou fez uso de algum tipo de medicamento?",
            "Tem algum tipo de DST?",
            "Tomou alguma vacina contra a COVID-19 recentimente? :",
        )
    }

    Text(
        "QUIZ DE APTIDÃO",
        style = TextStyle(fontFamily = fontFamilyRowdiesBold)
    )
    Column {

    }
}

@Composable
fun BotaoQuiz(valor:String){
    Box(  modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(top = 10.dp)
        .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        Row(

            modifier = Modifier
                .width(200.dp)
                .height(45.dp)
                .background(color = colorResource(id = R.color.vermelho_rosado), shape = RoundedCornerShape(16.dp))
                .border(
                    color = Color.Black,
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,

            ){

            Text(
                valor, fontSize = 18.sp, fontFamily = fontRobotoBold
            )
            Image(
                painter = painterResource(id = R.mipmap.seta_direita),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromQuiz() {
    VitaeAppTheme {
        TelaQuiz()
    }
}