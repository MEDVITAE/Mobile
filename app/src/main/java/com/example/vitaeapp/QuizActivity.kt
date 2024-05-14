package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Quiz
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Response

@Composable
fun TelaQuiz(token: String, id: Int) {
    val perguntas = remember { mutableStateOf(1) }
    val altura = remember { mutableStateOf("") }
    val peso = remember { mutableStateOf("") }
    val perguntaTatuagem = remember { mutableStateOf(false) }
    val perguntaRelacao = remember { mutableStateOf(false) }
    val perguntaDesconforto = remember { mutableStateOf(false) }
    val perguntaMedicamento = remember { mutableStateOf(false) }
    val perguntaDst = remember { mutableStateOf(false) }
    val perguntaVacina = remember { mutableStateOf(false) }
    val apto = remember { mutableStateOf(false) }

    Column {
        Questionario(
            perguntas, altura, peso, perguntaTatuagem,
            perguntaRelacao, perguntaDesconforto, perguntaMedicamento,
            perguntaDst, perguntaVacina, apto, token = token, id = id
        )
    }
}

@Composable
fun Questionario(
    perguntas: MutableState<Int>,
    altura: MutableState<String>,
    peso: MutableState<String>,
    perguntaTatuagem: MutableState<Boolean>,
    perguntaRelacao: MutableState<Boolean>,
    perguntaDesconforto: MutableState<Boolean>,
    perguntaMedicamento: MutableState<Boolean>,
    perguntaDst: MutableState<Boolean>,
    perguntaVacina: MutableState<Boolean>,
    apto: MutableState<Boolean>,
    token: String, id: Int
) {

    Column(Modifier.padding(30.dp, 70.dp)) {
        Text(
            "QUIZ DE APTIDÃO",
            style = TextStyle(fontFamily = fontFamilyRowdiesBold),
        )
        when (perguntas.value) {
            1 -> perguntaAltura(pergunta = altura)
            2 -> perguntaPeso(pergunta = peso)
            3 -> perguntaTatuagem(perguntas = perguntas, valorPergunta = perguntaTatuagem)
            4 -> perguntaRelacao(perguntas = perguntas, valorPergunta = perguntaRelacao)
            5 -> perguntaDesconforto(perguntas = perguntas, valorPergunta = perguntaDesconforto)
            6 -> perguntaMedicamento(perguntas = perguntas, valorPergunta = perguntaMedicamento)
            7 -> perguntaDst(perguntas = perguntas, valorPergunta = perguntaDst)
            8 -> perguntaVacina(perguntas = perguntas, valorPergunta = perguntaVacina)
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

            if (perguntas.value == 2 || perguntas.value == 8) {
                Spacer(modifier = Modifier.width(20.dp))
            }

            if (perguntas.value < 8) {
                BotaoAvancar(onClick = { perguntas.value++ })
            }

            if (perguntas.value == 9) {
                BotaoFinalizar(
                    perguntaTatuagem, perguntaRelacao, perguntaDesconforto, perguntaMedicamento,
                    perguntaDst, perguntaVacina, apto, altura, peso, token =  token, id = id
                )
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
fun BotaoFinalizar(
    perguntaTatuagem: MutableState<Boolean>,
    perguntaRelacao: MutableState<Boolean>,
    perguntaDesconforto: MutableState<Boolean>,
    perguntaMedicamento: MutableState<Boolean>,
    perguntaDst: MutableState<Boolean>,
    perguntaVacina: MutableState<Boolean>,
    apto: MutableState<Boolean>,
    altura: MutableState<String>,
    peso: MutableState<String>,
    token: String, id: Int
) {

    IconButton(
        modifier = Modifier
            .width(150.dp)
            .height(45.dp),
        onClick = {
            validarFuncoes(
                perguntaTatuagem,
                perguntaRelacao, perguntaDesconforto, perguntaMedicamento,
                perguntaDst, perguntaVacina, apto, token = token, id = id
            )
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
fun perguntaAltura(pergunta: MutableState<String>) {

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
            value = pergunta.value,
            onValueChange = { pergunta.value = it },
            singleLine = true, // Define o TextField como uma única linha
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Text("1/8")
        }
    }
}

@Composable
fun perguntaPeso(pergunta: MutableState<String>) {

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
            value = pergunta.value,
            onValueChange = { pergunta.value = it },
            singleLine = true, // Define o TextField como uma única linha
            modifier = Modifier.fillMaxWidth()
        )
        Row {
            Text("2/8")
        }
    }
}

@Composable
fun perguntaTatuagem(perguntas: MutableState<Int>, valorPergunta: MutableState<Boolean>) {
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
                valorPergunta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                valorPergunta.value = false
                perguntas.value++
            })
        }
        Row {
            Text("3/8")
        }
    }
}

@Composable
fun perguntaRelacao(perguntas: MutableState<Int>, valorPergunta: MutableState<Boolean>) {
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
                valorPergunta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                valorPergunta.value = false
                perguntas.value++
            })
        }
        Row {
            Text("4/8")
        }
    }
}

@Composable
fun perguntaDesconforto(perguntas: MutableState<Int>, valorPergunta: MutableState<Boolean>) {
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
                valorPergunta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                valorPergunta.value = false
                perguntas.value++
            })
        }
        Row {
            Text("5/8")
        }
    }
}

@Composable
fun perguntaMedicamento(perguntas: MutableState<Int>, valorPergunta: MutableState<Boolean>) {
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
                valorPergunta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                valorPergunta.value = false
                perguntas.value++
            })
        }
        Row {
            Text("6/8")
        }
    }
}

@Composable
fun perguntaDst(perguntas: MutableState<Int>, valorPergunta: MutableState<Boolean>) {
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
                valorPergunta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                valorPergunta.value = false
                perguntas.value++
            })
        }
        Row {
            Text("7/8")
        }
    }
}

@Composable
fun perguntaVacina(perguntas: MutableState<Int>, valorPergunta: MutableState<Boolean>) {
    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Tomou alguma vacina contra a COVID-19 recentemente?",
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
                valorPergunta.value = true
                perguntas.value++
            })
            BotaoNao(onClick = {
                valorPergunta.value = false
                perguntas.value++
            })
        }
        Row {
            Text("8/8")
        }
    }
}

fun validarFuncoes(
    perguntaTatuagem: MutableState<Boolean>,
    perguntaRelacao: MutableState<Boolean>,
    perguntaDesconforto: MutableState<Boolean>,
    perguntaMedicamento: MutableState<Boolean>,
    perguntaDst: MutableState<Boolean>,
    perguntaVacina: MutableState<Boolean>,
    apto: MutableState<Boolean>,
    token: String, id: Int
) {
    if (perguntaTatuagem.value || perguntaRelacao.value || perguntaDesconforto.value ||
        perguntaMedicamento.value || perguntaDst.value || perguntaVacina.value
    ) {
        conectarBanco(false, token = token, id = id)
    }
    else {
        conectarBanco(true, token = token, id = id)
    }
}

fun conectarBanco(validarFuncoes: Boolean, token: String, id: Int) {

    var erroApi = ""

    val apiQuiz = RetrofitServices.getApiQuiz()

    val quiz = Quiz(altura = null, peso = null, validarFuncoes)

    val put = apiQuiz.put(quiz, id, token)

    put.enqueue(object : retrofit2.Callback<Quiz> {
        // esta função é invocada caso:
        // a chamada ao endpoint ocorra sem problemas
        // o corpo da resposta foi convertido para o tipo indicado
        override fun onResponse(call: retrofit2.Call<Quiz>, response: Response<Quiz>) {
            if (response.isSuccessful) { // testando se a resposta não é 4xx nem 5xx
                val lista = response.body() // recuperando o corpo da resposta
                if (lista != null) {
                    println("Ok")
                }
            } else {
                erroApi = response.errorBody().toString()
            }
        }

        // esta função é invocada caso:
        // não seja possivel chamar a API (rede fora, por exemplo)
        // não seja possivel converter o corpo da resposta no tipo esperado
        override fun onFailure(call: retrofit2.Call<Quiz>, t: Throwable) {
            erroApi = t.message!!
        }

    })

}
