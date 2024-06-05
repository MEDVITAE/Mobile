package com.example.vitaeapp

import android.util.Log
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Quiz
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Response

@Composable
fun TelaQuiz(navController: NavHostController, token: String, id: Int) {
    val contador = remember { mutableStateOf(1) }
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
            navController, contador, altura, peso, perguntaTatuagem,
            perguntaRelacao, perguntaDesconforto, perguntaMedicamento,
            perguntaDst, perguntaVacina, apto, token, id
        )
    }
}

@Composable
fun Questionario(
    navController: NavHostController,
    contador: MutableState<Int>,
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

    Column(Modifier.padding(30.dp, 90.dp)) {
        Text(
            stringResource(id = R.string.title_quiz),
            style = TextStyle(fontFamily = fontFamilyRowdiesBold, fontSize = 18.sp),
        )
        when (contador.value) {
            1 -> perguntaAltura(pergunta = altura, onValueChange = { altura.value = it })
            2 -> perguntaPeso(pergunta = peso, onValueChange = { peso.value = it })
            3 -> perguntaTatuagem(perguntas = contador, valorPergunta = perguntaTatuagem)
            4 -> perguntaRelacao(perguntas = contador, valorPergunta = perguntaRelacao)
            5 -> perguntaDesconforto(perguntas = contador, valorPergunta = perguntaDesconforto)
            6 -> perguntaMedicamento(perguntas = contador, valorPergunta = perguntaMedicamento)
            7 -> perguntaDst(perguntas = contador, valorPergunta = perguntaDst)
            8 -> perguntaVacina(perguntas = contador, valorPergunta = perguntaVacina)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 100.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            if (contador.value > 1 && contador.value < 9) {
                BotaoVoltar(onClick = { contador.value-- })
            }

            if (contador.value == 2) {
                Spacer(modifier = Modifier.width(20.dp))
            }

            if (contador.value > 0 && contador.value < 3) {
                BotaoAvancar(onClick = { contador.value++ })
            }
            if (contador.value == 9) {
                Column {
                    Text(
                        "Deseja finalizar o Quiz de Aptidão?",
                        fontSize = 18.sp,
                        fontFamily = fontRobotoBold
                    )
                    Spacer(modifier = Modifier.height(70.dp))
                    Row {
                        BotaoVoltar(onClick = { contador.value-- })
                        Spacer(modifier = Modifier.width(10.dp))
                        BotaoFinalizar(
                            navController,
                            perguntaTatuagem,
                            perguntaRelacao,
                            perguntaDesconforto,
                            perguntaMedicamento,
                            perguntaDst,
                            perguntaVacina,
                            apto,
                            altura,
                            peso,
                            token = token,
                            id = id
                        )
                    }
                }
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
                stringResource(id = R.string.btn_avancar),
                fontSize = 18.sp,
                fontFamily = fontRobotoBold
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
                stringResource(id = R.string.btn_voltar),
                fontSize = 18.sp,
                fontFamily = fontRobotoBold
            )
        }
    }
}

@Composable
fun BotaoFinalizar(
    navController: NavHostController,
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
            ValidarFuncoes(
                navController = navController, perguntaTatuagem,
                perguntaRelacao, perguntaDesconforto, perguntaMedicamento,
                perguntaDst, perguntaVacina, apto, altura, peso, token = token, id = id
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
                stringResource(id = R.string.btn_finalizar),
                fontSize = 18.sp,
                fontFamily = fontRobotoBold
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
                stringResource(id = R.string.btn_sim), fontSize = 18.sp, fontFamily = fontRobotoBold
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
                stringResource(id = R.string.btn_nao), fontSize = 18.sp, fontFamily = fontRobotoBold
            )
        }
    }
}

@Composable
fun perguntaAltura(pergunta: MutableState<String>, onValueChange: (String) -> Unit) {

    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.title_quiz_sub_pergunta_1),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(fontFamily = fontFamilyRowdies, fontSize = 20.sp),
        )
        BasicTextField(
            value = pergunta.value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.background(color = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
            )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
        Row {
            Text("1/8")
        }
        if (pergunta.value.isEmpty() || pergunta.value.toDouble() <= 0.0 || pergunta.value.toDouble() > 3.0) {
            Text("Altura Inválida")
        }
    }
}


@Composable
fun perguntaPeso(pergunta: MutableState<String>, onValueChange: (String) -> Unit) {

    Column(
        modifier = Modifier
            .padding(0.dp, 100.dp, 0.dp, 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(id = R.string.title_quiz_sub_pergunta_2),
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 20.dp),
            style = TextStyle(fontFamily = fontFamilyRowdies, fontSize = 20.sp)
        )
        BasicTextField(
            value = pergunta.value,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.background(color = Color.Transparent),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            )
            )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
        if (pergunta.value.isEmpty() || pergunta.value.toDouble() <= 0.0) {
            Text("Peso Inválido")
        }
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
            stringResource(id = R.string.title_quiz_sub_pergunta_3),
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
            stringResource(id = R.string.title_quiz_sub_pergunta_4),
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
            stringResource(id = R.string.title_quiz_sub_pergunta_5),
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
            stringResource(id = R.string.title_quiz_sub_pergunta_6),
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
            stringResource(id = R.string.title_quiz_sub_pergunta_8),
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
            stringResource(id = R.string.title_quiz_sub_pergunta_8),
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

fun ValidarFuncoes(
    navController: NavHostController,
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
    if (perguntaTatuagem.value || perguntaRelacao.value || perguntaDesconforto.value ||
        perguntaMedicamento.value || perguntaDst.value || perguntaVacina.value
    ) {
        conectarBanco(false, altura, peso, navController, token, id)
    } else {
        conectarBanco(true, altura, peso, navController, token, id)
    }
}

fun conectarBanco(
    validarFuncoes: Boolean,
    altura: MutableState<String>,
    peso: MutableState<String>,
    navController: NavHostController,
    token: String,
    id: Int
) {
    var erroApi = ""

    val apiQuiz = RetrofitServices.getApiQuiz()

    val quiz = Quiz(altura = altura.value, peso = peso.value, validarFuncoes)

    val put = apiQuiz.put(
        quiz = quiz,
        id = id,
        token = token
    )

    put.enqueue(object : retrofit2.Callback<Quiz> {
        override fun onResponse(call: retrofit2.Call<Quiz>, response: Response<Quiz>) {
            if (response.isSuccessful) {
                val lista = response.body()
                if (lista != null) {
                    // Processar a resposta
                }
            } else {
                erroApi = response.errorBody()?.string() ?: "Erro desconhecido"
            }
        }

        override fun onFailure(call: retrofit2.Call<Quiz>, t: Throwable) {
            erroApi = t.message ?: "Erro desconhecido"
        }
    })

    if (erroApi.isEmpty()) {
        navController.navigate("Perfil/${token}/${id}")
    } else {
        // Tratar erro, talvez mostrar uma mensagem para o usuário
        Log.e("QuizActivity", "Erro na API: $erroApi")
    }
}