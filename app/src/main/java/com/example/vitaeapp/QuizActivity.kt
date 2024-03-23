package com.example.vitaeapp

import android.content.Intent
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
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
        Questionario()
    }
    Menu()
}

@Composable
fun Questionario() {
    val perguntas = remember { mutableStateOf(1) }

    Text(
        "QUIZ DE APTIDÃO",
        style = TextStyle(fontFamily = fontFamilyRowdiesBold)
    )
    Column {
        when (perguntas.value) {
            1 -> perguntaPeso()
            2 -> perguntaAltura()
            3 -> perguntaTatuagem()
            4 -> perguntaRelacao()
            5 -> perguntaDesconforto()
            6 -> perguntaMedicamento()
            7 -> perguntaDst()
            8 -> perguntaMedicamento()
        }

        if (perguntas.value < 8) {
            BotaoAvancar {
                perguntas.value++
            }
        }

        if (perguntas.value > 1) {
            BotaoVoltar {
                perguntas.value--
            }
        }
    }
}

@Composable
fun BotaoAvancar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 10.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                    .background(
                        color = colorResource(id = R.color.vermelho_rosado),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = Color.Black,
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    "Avançar", fontSize = 18.sp, fontFamily = fontRobotoBold
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
}

@Composable
fun BotaoVoltar(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 10.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                    .background(
                        color = colorResource(id = R.color.vermelho_rosado),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = Color.Black,
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    "Voltar", fontSize = 18.sp, fontFamily = fontRobotoBold
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
}

@Composable
fun BotaoSim() {
    val respostaSim = remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 10.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                    .background(
                        color = colorResource(id = R.color.vermelho_rosado),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = Color.Black,
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    "Sim", fontSize = 18.sp, fontFamily = fontRobotoBold
                )
            }
        }
    }
}

@Composable
fun BotaoNao() {
    val respostaNao = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(top = 10.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = { }
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                    .background(
                        color = colorResource(id = R.color.vermelho_rosado),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = Color.Black,
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    "Não", fontSize = 18.sp, fontFamily = fontRobotoBold
                )
            }
        }
    }
}

@Composable
fun perguntaAltura() {
    val altura = remember { mutableStateOf("") }

    Column {
        Text(
            "Informe sua altura:",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        TextField(
            value = altura.value,
            onValueChange = { altura.value = it },
            singleLine = true, // Define o TextField como uma única linha
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Text("1/8")
        }
    }
}

@Composable
fun perguntaPeso() {
    val peso = remember { mutableStateOf("") }

    Column {
        Text(
            "Informe seu peso:",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        TextField(
            value = peso.value,
            onValueChange = { peso.value = it },
            singleLine = true, // Define o TextField como uma única linha
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Text("2/8")
        }
    }
}

@Composable
fun perguntaTatuagem() {
    Column {
        Text(
            "Fez alguma tatuagem nos últimos 6 meses?",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        Row {
            BotaoNao()
            BotaoSim()
        }
        Row {
            Text("3/8")
        }
    }
}

@Composable
fun perguntaRelacao() {
    Column {
        Text(
            "Teve algum tipo de relação sexual recentemente?",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        Row {
            BotaoNao()
            BotaoSim()
        }
        Row {
            Text("4/8")
        }
    }
}

@Composable
fun perguntaDesconforto() {
    Column {
        Text(
            "Sente algum desconforto, ou dor na barriga?",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        Row {
            BotaoNao()
            BotaoSim()
        }
        Row {
            Text("5/8")
        }
    }
}

@Composable
fun perguntaMedicamento() {
    Column {
        Text(
            "Faz ou fez uso de algum tipo de medicamento?",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        Row {
            BotaoNao()
            BotaoSim()
        }
        Row {
            Text("6/8")
        }
    }
}

@Composable
fun perguntaDst() {
    Column {
        Text(
            "Tem algum tipo de DST?",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        Row {
            BotaoNao()
            BotaoSim()
        }
        Row {
            Text("7/8")
        }
    }
}

@Composable
fun perguntaVacina() {
    Column {
        Text(
            "Tomou alguma vacina contra a COVID-19 recentimente? :",
            style = TextStyle(fontFamily = fontRobotoRegular)
        )
        Row {
            BotaoNao()
            BotaoSim()
        }
        Row {
            Text("8/8")
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