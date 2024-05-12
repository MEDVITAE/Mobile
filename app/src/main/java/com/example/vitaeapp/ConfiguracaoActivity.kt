package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row 
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Configuracao
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromConfig() {
    VitaeAppTheme {
        var config = getBanco()
        TelaDeConfiguracao(rememberNavController(), config)
    }
}

// Variáveis para resposta da api, seja um sucesso, ou não
var erroApi = ""
var acertoApi = ""

val apiConfig = RetrofitServices.getConfigUsuario()
val apiConfigDadosUser = RetrofitServices.putConfigUser()
val apiConfigEndereco = RetrofitServices.putConfigCep()
val apiConfigData = RetrofitServices.putConfigData()

var pk =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImFudG9uaW9AZ21haWwuY29tIiwiZXhwIjoxNzE1NTM3MDI3fQ.h5QKzyLIcjibaYrGTqNPNmxJX_ge4nBb3LCaufi8SWI"

fun getBanco(): Configuracao {

    val getUser = apiConfig.getConfigDadosUser(pk, id = 85)
    getUser.enqueue(object : Callback<Configuracao> {
        override fun onResponse(
            call: Call<Configuracao>,
            response: Response<Configuracao>
        ) {
            if (response.isSuccessful) {
                val resposta = response.body()
                if (resposta != null) {
                    acertoApi = "Usuário verificado"
//                    return
                    Configuracao(
                        nome = resposta.nome,
                        email = resposta.email,
                        cep = resposta.cep,
                        dataNasc = resposta.dataNasc,
                        senha = "********"
                    )
                } else {
                    // Não foi possível achar usuário
                    erroApi = "Erro ao verificar usuário"
                }
            } else {
                // Algo passado pode estar errado
                erroApi = "Erro na solicitação: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<Configuracao>, t: Throwable) {
            // Não foi possível conectar na api
            erroApi = "Falha na solicitação: ${t.message}"
        }
    })
    return Configuracao("", "", "", "", "", "")
}

@Composable
fun TelaDeConfiguracao(navController: NavHostController, config: Configuracao) {
    // Valores iniciais dos campos de entrada
    var nome = remember { mutableStateOf(config.nome) }
    var email = remember { mutableStateOf(config.email) }
    var cep = remember { mutableStateOf(config.cep) }
    var dataNasc = remember { mutableStateOf(config.dataNasc) }
    var senha = remember { mutableStateOf(config.senha) }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "${nome.value}," +
                    "${email.value}" +
                    "${cep.value}" +
                    "${dataNasc.value}" +
                    "${senha.value}"
        )

        AtributoUsuarioConfig("CONFIGURAÇÕES", 70, 15)
        nome.value?.let { CampoDeEntrada(label = "Nome:", valor = it) { nome.value = it } }
        email.value?.let { CampoDeEntrada(label = "E-mail:", valor = it) { email.value = it } }
        cep.value?.let { CampoDeEntrada(label = "CEP:", valor = it) { cep.value = it } }
        dataNasc.value?.let {data ->
            // Aqui 'data' é garantido de não ser nulo
            val formattedDate = data.toString() // Ou outra lógica de formatação
            CampoDeEntrada(label = "Data de Nascimento:", valor = formattedDate) {
                dataNasc.value = it
            }
        }
        senha.value?.let { CampoDeEntrada(label = "Senha:", valor = it) { senha.value = it } }

        BotaoSalvar(valor = "Salvar", onClick = {

            val putUserDados = apiConfigDadosUser.putConfigUser(
                token = pk,
                id = 85,
                config = Configuracao(
                    nome.value,
                    email.value,
                    cep.value,
                    dataNasc.value,
                    senha.value
                )
            )

            putUserDados.enqueue(object : Callback<Configuracao> {
                override fun onResponse(
                    call: Call<Configuracao>,
                    response: Response<Configuracao>
                ) {
                    if (response.isSuccessful) {
                        val resposta = response.body()
                        if (resposta != null) {
                            acertoApi = "Dados do Usuário cadastrado"
                            Configuracao(
                                config.nome,
                                config.email,
                                config.token,
                                config.cep,
                                config.dataNasc,
                                config.senha,
                            )
                        } else {
                            // Não foi possível achar usuário
                            erroApi = "Erro ao verificar usuário"
                        }
                    } else {
                        // Algo passado pode estar errado
                        erroApi = "Erro na solicitação: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Configuracao>, t: Throwable) {
                    // Não foi possível conectar na api
                    erroApi = "Falha na solicitação: ${t.message}"
                }
            })

            val putUserCep = apiConfigEndereco.putConfigEnde(
                token = pk,
                id = 85,
                config = Configuracao(
                   cep = cep.value
                )
            )

            putUserCep.enqueue(object : Callback<Configuracao> {
                override fun onResponse(
                    call: Call<Configuracao>,
                    response: Response<Configuracao>
                ) {
                    if (response.isSuccessful) {
                        val resposta = response.body()
                        if (resposta != null) {
                            acertoApi = "Dados do Usuário cadastrado"
                            Configuracao(
                                config.cep
                            )
                        } else {
                            // Não foi possível achar usuário
                            erroApi = "Erro ao verificar usuário"
                        }
                    } else {
                        // Algo passado pode estar errado
                        erroApi = "Erro na solicitação: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Configuracao>, t: Throwable) {
                    // Não foi possível conectar na api
                    erroApi = "Falha na solicitação: ${t.message}"
                }
            })

            val putUserData = apiConfigData.putConfigData(
                token = pk,
                id = 85,
                config = Configuracao(
                    dataNasc = dataNasc.value
                )
            )

            putUserData.enqueue(object : Callback<Configuracao> {
                override fun onResponse(
                    call: Call<Configuracao>,
                    response: Response<Configuracao>
                ) {
                    if (response.isSuccessful) {
                        val resposta = response.body()
                        if (resposta != null) {
                            acertoApi = "Dados do Usuário cadastrado"
                            Configuracao(
                                config.dataNasc
                            )
                        } else {
                            // Não foi possível achar usuário
                            erroApi = "Erro ao verificar usuário"
                        }
                    } else {
                        // Algo passado pode estar errado
                        erroApi = "Erro na solicitação: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<Configuracao>, t: Throwable) {
                    // Não foi possível conectar na api
                    erroApi = "Falha na solicitação: ${t.message}"
                }
            })
        })
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun AtributoUsuarioConfig(valor: String, paddingTop: Int, paddingBottom: Int) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            valor, fontSize = 30.sp, fontFamily = fontRobotoBold, modifier =
            Modifier.padding(
                top = paddingTop.dp,
                start = 20.dp,
                end = 15.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun CampoDeEntrada(label: String, valor: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,

        ) {
        var isHintVisible = valor.isEmpty()

        BasicTextField(
            value = valor,
            onValueChange = {
                onValueChange(it)
                isHintVisible = it.isEmpty()
            },
            textStyle = TextStyle(fontFamily = Rowdies, color = Color.Black, fontSize = 16.sp),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .width(350.dp)
                        .height(50.dp)
                        .background(Color.White, shape = RoundedCornerShape(7.dp))
                        .border(1.2.dp, Color.Black, RoundedCornerShape(6.dp)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (!isHintVisible) {
                            innerTextField()
                        } else {
                            Text(
                                text = label,
                                style = TextStyle(color = Color.Black),
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }
                    }
                }
            },
            modifier = Modifier
                .padding(8.dp)
                .shadow(10.dp, shape = RoundedCornerShape(7.dp))
        )
    }
}

@Composable
fun BotaoSalvar(valor: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(top = 10.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,
    ) {
        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = onClick
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
                        width = 1.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    valor, fontSize = 18.sp, fontFamily = fontRobotoBold
                )
                Image(
                    painter = painterResource(id = R.mipmap.seta_direita),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                )
            }
        }
    }
}
