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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Quiz
import com.example.vitaeapp.classes.Ranking
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Path

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

    val respostaTatuagem = remember {
        mutableStateOf(true)
    }
    val respostaRelacao = remember {
        mutableStateOf(true)
    }
    val respostaDesconforto = remember {
        mutableStateOf(true)
    }
    val respostaMedicamento = remember {
        mutableStateOf(true)
    }
    val respostaDst = remember {
        mutableStateOf(true)
    }
    val respostaVacina = remember {
        mutableStateOf(true)
    }

    val quiz = remember {
        mutableStateOf(Quiz())
    }

    val erroApi = remember { mutableStateOf("") }

    val apiQuiz = RetrofitServices.getApiQuiz()

    val put = apiQuiz.put(quiz.value, perguntas.value)

    put.enqueue(object : retrofit2.Callback<Quiz> {
        // esta função é invocada caso:
        // a chamada ao endpoint ocorra sem problemas
        // o corpo da resposta foi convertido para o tipo indicado
        override fun onResponse(call: Call<Quiz>, response: Response<Quiz>) {
            if (response.isSuccessful) { // testando se a resposta não é 4xx nem 5xx
                val lista = response.body() // recuperando o corpo da resposta
                if (lista != null) {

                }
            } else {
                erroApi.value = response.errorBody().toString()
            }
        }

        // esta função é invocada caso:
        // não seja possivel chamar a API (rede fora, por exemplo)
        // não seja possivel converter o corpo da resposta no tipo esperado
        override fun onFailure(call: Call<List<Quiz>>, t: Throwable) {
            erroApi.value = t.message!!
        }

    })

    Column(Modifier.padding(30.dp, 70.dp)) {
        Text(
            "QUIZ DE APTIDÃO",
            style = TextStyle(fontFamily = fontFamilyRowdiesBold),
        )
        when (perguntas.value) {
            1 -> perguntaAltura()
            2 -> perguntaPeso()
            3 -> perguntaTatuagem(perguntas, respostaTatuagem)
            4 -> perguntaRelacao(perguntas, respostaRelacao)
            5 -> perguntaDesconforto(perguntas, respostaDesconforto)
            6 -> perguntaMedicamento(perguntas, respostaMedicamento)
            7 -> perguntaDst(perguntas, respostaDst)
            8 -> perguntaVacina(perguntas, respostaVacina)
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

    val quiz = remember {
        mutableStateOf(Quiz())
    }

    if (quiz.value.altura!! < 0) {
        println("Altura não pode ser negativa.");
    }

    if (quiz.value.peso!! < 0) {
        println("Peso não pode ser negativo.");
    }
//
//    if (quiz.value.peso.) {
//        modalErro("Por favor, preencha todos os campos do formulário.");
//        return;
//    }
//
//    if (parseFloat(altura) <= 1 || parseFloat(altura) > 3) {
//        modalErro("Altura inválida. Por favor, insira uma altura válida.");
//        return;
//    }

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
fun perguntaAltura(onClick: () -> Unit) {
    val altura = remember { mutableStateOf("") }

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
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
fun perguntaPeso(onClick: () -> Unit) {
    val peso = remember { mutableStateOf("") }

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
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
fun perguntaTatuagem(onClick: () -> Unit, perguntas: MutableState<Int>, resposta: MutableState<Boolean>) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
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
fun perguntaRelacao(onClick: () -> Unit, perguntas: MutableState<Int>, resposta: MutableState<Boolean>) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
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
fun perguntaDesconforto(onClick: () -> Unit, perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {

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
fun perguntaMedicamento(onClick: () -> Unit, perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
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
fun perguntaDst(onClick: () -> Unit, perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {

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
fun perguntaVacina(onClick: () -> Unit, perguntas:MutableState<Int>, resposta: MutableState<Boolean>) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = onClick
    ) {
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