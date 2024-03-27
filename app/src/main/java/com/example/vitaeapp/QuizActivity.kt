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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.ui.theme.VitaeAppTheme

@Composable
fun TelaQuiz() {
    Column {
        Questionario()
    }
}

@Composable
fun Questionario() {
    val perguntas = remember { mutableStateOf(1) }
    val resposta = remember { mutableStateOf(true) }

    Column(Modifier.padding(30.dp, 70.dp)) {
        Text(
            "QUIZ DE APTIDÃO",
            style = TextStyle(fontFamily = fontFamilyRowdiesBold),
        )
        when (perguntas.value) {
            1 -> perguntaAltura()
            2 -> perguntaPeso()
            3 -> perguntaTatuagem(perguntas, resposta)
            4 -> perguntaRelacao(perguntas, resposta)
            5 -> perguntaDesconforto(perguntas, resposta)
            6 -> perguntaMedicamento(perguntas, resposta)
            7 -> perguntaDst(perguntas, resposta)
            8 -> perguntaVacina(perguntas, resposta)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (perguntas.value > 1) {
                BotaoVoltar(onClick = { perguntas.value-- })
            }

            if (perguntas.value === 2 || perguntas.value === 8) {
                Spacer(modifier = Modifier.width(20.dp))
            }

            if (perguntas.value < 3) {
                BotaoAvancar(onClick = { perguntas.value++ })
            }

            if (perguntas.value === 8) {
                BotaoFinalizar()
            }
        }
    }
}

@Composable
fun BotaoAvancar(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .width(140.dp)
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                "Avançar", fontSize = 18.sp, fontFamily = fontRobotoBold
            )
            Spacer(modifier = Modifier.width(10.dp))
            Image(
                painter = painterResource(id = R.mipmap.seta_direita),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
        }
    }
}

@Composable
fun BotaoVoltar(onClick: () -> Unit) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .width(140.dp)
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.mipmap.seta_esquerda),
                contentDescription = null,
                modifier = Modifier
                    .size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                "Voltar", fontSize = 18.sp, fontFamily = fontRobotoBold
            )
        }
    }
}

@Composable
fun BotaoFinalizar() {
    val contexto = LocalContext.current

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = {
        }
    ) {
        Row(
            modifier = Modifier
                .width(140.dp)
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                "Finalizar", fontSize = 18.sp, fontFamily = fontRobotoBold
            )
        }
    }
}

@Composable
fun BotaoSim(onClick: () -> Unit) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick,
    ) {
        Row(
            modifier = Modifier
                .width(120.dp)
                .height(45.dp)
                .background(
                    color = colorResource(id = R.color.vermelho_rosado),
                    shape = RoundedCornerShape(8.dp)
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

@Composable
fun BotaoNao(onClick: () -> Unit) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .width(120.dp)
                .height(45.dp)
                .background(
                    color = colorResource(id = R.color.vermelho_rosado),
                    shape = RoundedCornerShape(8.dp)
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

@Composable
fun perguntaAltura() {
    val altura = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Informe sua altura:",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(fontFamily = fontFamilyRowdies, fontSize = 20.sp)
        )
        TextField(
            value = altura.value,
            onValueChange = { altura.value = it },
            singleLine = true, // Define o TextField como uma única linha
            modifier = Modifier
                .fillMaxWidth()
        )
        Row {
            Text("1/8")
        }
    }
}

@Composable
fun perguntaPeso() {
    val peso = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Informe seu peso:",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(fontFamily = fontFamilyRowdies, fontSize = 20.sp)
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
fun perguntaTatuagem(perguntas: MutableState<Int>, resposta: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Fez alguma tatuagem nos últimos 6 meses?",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(
                fontFamily = fontFamilyRowdies,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 20.dp)
        ) {
            BotaoSim(onClick = {
                resposta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                resposta.value = true
                perguntas.value++
            })
        }
        Row {
            Text("3/8")
        }
    }
}

@Composable
fun perguntaRelacao(perguntas: MutableState<Int>, resposta: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Teve algum tipo de relação sexual recentemente?",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(
                fontFamily = fontFamilyRowdies,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 20.dp)
        ) {
            BotaoSim(onClick = {
                resposta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                resposta.value = true
                perguntas.value++
            })
        }
        Row {
            Text("4/8")
        }
    }
}

@Composable
fun perguntaDesconforto(perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Sente algum desconforto, ou dor na barriga?",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(
                fontFamily = fontFamilyRowdies,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 20.dp)
        ) {
            BotaoSim(onClick = {
                resposta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                resposta.value = true
                perguntas.value++
            })
        }
        Row {
            Text("5/8")
        }
    }
}

@Composable
fun perguntaMedicamento(perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {

    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Faz ou fez uso de algum tipo de medicamento?",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(
                fontFamily = fontFamilyRowdies,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 20.dp)
        ) {
            BotaoSim(onClick = {
                resposta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                resposta.value = true
                perguntas.value++
            })
        }
        Row {
            Text("6/8")
        }
    }
}

@Composable
fun perguntaDst(perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Tem algum tipo de DST?",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(
                fontFamily = fontFamilyRowdies,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 20.dp)
        ) {
            BotaoSim(onClick = {
                resposta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                resposta.value = true
                perguntas.value++
            })
        }
        Row {
            Text("7/8")
        }
    }
}

@Composable
fun perguntaVacina(perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {

    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Tomou alguma vacina contra a COVID-19 recentimente?",
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(
                fontFamily = fontFamilyRowdies,
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )
        )
        Row(
            modifier = Modifier
                .padding(0.dp, 5.dp, 0.dp, 20.dp)
        ) {
            BotaoSim(onClick = {
                resposta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                resposta.value = true
                perguntas.value++
            })
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