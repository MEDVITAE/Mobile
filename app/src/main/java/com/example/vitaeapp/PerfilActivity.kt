package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.R.*
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.UsuarioPerfil
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response

@Composable
fun TelaPerfil() {
    var isLoading by remember { mutableStateOf(true) }
    var nome = remember { mutableStateOf("") }
    var peso = remember { mutableStateOf("") }
    var altura = remember { mutableStateOf("") }
    var cpf = remember { mutableStateOf("") }
    var quantidadeDoacao = remember { mutableStateOf(0) }
    var apto = remember { mutableStateOf(false) }
    var tipoSangue = remember { mutableStateOf("") }
    val apiPerfil = RetrofitServices.getDetalhesUser()
    val get = apiPerfil.getDetalhesUsuario(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImFtYXJlbG9AZ21haWwuY29tIiwiZXhwIjoxNzE1MTg1OTcxfQ.VOii79Nb1JPO5W2MzdK3KZgzkdlojkoFApIwJIuZ3nQ",
        1
    )
    val erroApi = remember { mutableStateOf("") }
    LaunchedEffect(Unit) { // Executa quando o componente é iniciado
        get.enqueue(object : retrofit2.Callback<UsuarioPerfil> {
            override fun onResponse(
                call: Call<UsuarioPerfil>,
                response: Response<UsuarioPerfil>
            ) {
                if (response.isSuccessful) {
                    val obj = response.body()
                    if (obj != null) {
                        nome.value = obj.nome
                        altura.value = obj.altura
                        peso.value = obj.peso
                        cpf.value = obj.cpf
                        tipoSangue.value = obj.tipo
                        quantidadeDoacao.value = obj.numero
                        apto.value = obj.apto
                    } else {
                        erroApi.value = "Erro ao buscar detalhes"
                    }
                } else {
                    erroApi.value = "Erro na solicitação: ${response.code()}"
                }
                isLoading = false // Indica que os dados foram carregados
            }

            override fun onFailure(call: Call<UsuarioPerfil>, t: Throwable) {
                // Lógica para lidar com falha na solicitação
                isLoading = false // Indica que os dados foram carregados
            }
        })
    }

    if (isLoading) {
        // Exibe mensagem de carregamento enquanto os dados estão sendo carregados
        Text(text = "Carregando dados...")
    } else {
        // Exibe os campos com os valores recebidos da API
        Column {
            AtributoUsuario(stringResource(id = (string.ola)) + nome.value, 70, 15)
            QuadradoInfo(apto.value, quantidadeDoacao.value, tipoSangue.value)
            AtributoUsuario(stringResource(id = (string.title_input_nome)), 12, 5)
            InputGetInfo(valor = nome.value) { nome.value = it }
            AtributoUsuario(stringResource(id = (string.title_input_cpf)), 12, 10)
            InputGetInfo(valor = cpf.value) { cpf.value = it }
            AtributoUsuario(stringResource(id = (string.title_input_peso)), 12, 10)
            InputGetInfo(valor = peso.value) { peso.value = it }
            AtributoUsuario(stringResource(id = (string.title_input_altura)), 12, 10)
            InputGetInfo(valor = altura.value) { altura.value = it }
            BotaoEditar("Editar")
        }
    }
}


@Composable
fun AtributoUsuario(valor: String, paddingTop: Int, paddingBottom: Int) {
    Column {
        Text(
            valor,
            fontSize = 20.sp,
            fontFamily = fontRobotoBold,
            modifier = Modifier.padding(
                top = paddingTop.dp,
                start = 15.dp,
                end = 20.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputGetInfo(valor: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .width(450.dp)
            .padding(start = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp)
                .background(Color.Transparent)
        ) {
            Text(
                valor,
                fontSize = 17.sp,
                fontFamily = fontRobotoBold,
                color = Color.Black,
                modifier = Modifier.width(350.dp)
            )
        }
        Divider(color = Color.Black, modifier = Modifier.height(1.dp))
    }
}


@Composable
fun QuadradoComTexto(imagemTexto: Int, textoQuadrado: String) {

    Column {
        Row {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .border(
                        color = Color.Black,
                        width = 1.dp,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,

                ) {

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(textoQuadrado, fontSize = 14.sp, fontFamily = fontRobotoRegular)
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,

                        ) {
                        Text("${imagemTexto}", fontSize = 30.sp, fontFamily = fontFamilyRowdies)
                    }

                }
            }

        }

    }
}


@Composable
fun QuadradoComImagem(imagemId: Int, textoQuadrado: String) {

    Column {
        Row {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .border(
                        color = Color.Black,
                        width = 1.dp,
                        shape = RoundedCornerShape(15.dp)
                    )
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center,

                ) {

                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(textoQuadrado, fontSize = 14.sp, fontFamily = fontRobotoRegular)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = imagemId),
                            contentDescription = null,
                            modifier = Modifier
                                .size(50.dp)

                        )
                    }

                }
            }

        }

    }
}


@Composable
fun QuadradoInfo(apto: Boolean, quantidadeDoacao: Int, tipoSangue: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
        ) {
            var ImagemApto: Int = mipmap.check
            var ImagemTipo = mipmap.seminfo
            if (!apto) {
                ImagemApto = mipmap.naoapto
            }
            if (tipoSangue == "AB") {
                ImagemTipo = mipmap.ab
            } else if (tipoSangue == "-AB") {
                ImagemTipo = mipmap.abnegativo
            }
            else if (tipoSangue == "-A") {
                ImagemTipo = mipmap.anegativo
            }else if(tipoSangue == "A"){
                 ImagemTipo = mipmap.a
            }
            QuadradoComImagem(
                ImagemApto,
                stringResource(id = (string.title_perfil_apto_a_doacao))
            )
            Spacer(modifier = Modifier.width(10.dp))
            QuadradoComTexto(
                quantidadeDoacao,
                stringResource(id = (string.title_perfil_quantidade_doacao))
            )

            Spacer(modifier = Modifier.width(10.dp))
            QuadradoComImagem(
                ImagemTipo,
                stringResource(id = (string.title_perfil_tipo_sangue))
            )
        }

    }
}

@Composable
fun BotaoEditar(valor: String) {
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
            onClick = {
            }
        ) {
            Row(
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp)
                    .background(
                        color = colorResource(id = color.vermelho_rosado),
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
                    valor, fontSize = 18.sp, fontFamily = fontRobotoBold
                )
                Image(
                    painter = painterResource(id = mipmap.seta_direita),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromPerfil() {
    VitaeAppTheme {
        TelaPerfil()
    }
}