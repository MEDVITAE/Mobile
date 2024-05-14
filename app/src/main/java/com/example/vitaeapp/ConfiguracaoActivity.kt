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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.vitaeapp.classes.UserConfig
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromConfig() {
    VitaeAppTheme {
//        var config = getBanco()
        TelaDeConfiguracao(rememberNavController())
    }
}

// Variáveis para resposta da api, seja um sucesso, ou não
var erroApi = ""
var acertoApi = ""

val apiConfig = RetrofitServices.getConfigUsuario()
val apiConfigDadosUser = RetrofitServices.putConfigUser()
val apiConfigEndereco = RetrofitServices.putConfigCep()
val apiConfigData = RetrofitServices.putConfigData()


@Composable
fun TelaDeConfiguracao(navController: NavHostController) {
    var pk =
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6Im1pbmlvbkBnbWFpbC5jb20iLCJleHAiOjE3MTU2NTA4NDV9.DoVz73pKb1hduVGg_KaL0irrGGq_0JsyB41M8dXFIBQ"



    var nome by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var dataNasc by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var conecta by remember { mutableStateOf(true) }
    val getUser = apiConfig.getConfigDadosUser(pk, id = 1)

    if (conecta) {
        getUser.enqueue(object : Callback<Configuracao> {
            override fun onResponse(
                call: Call<Configuracao>,
                response: Response<Configuracao>
            ) {
                if (response.isSuccessful) {
                    val resposta = response.body()
                    if (resposta != null) {
                        acertoApi = "Usuário verificado"
                        nome = resposta.nome.toString()
                        email = resposta.email.toString()
                        cep = resposta.cep.toString()
                        dataNasc = resposta.nascimento.toString()
                        senha = "marcos"
                        conecta = false

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
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        AtributoUsuarioConfig("CONFIGURAÇÕES", 70, 15)
        nome?.let { CampoDeEntrada(label = "Nome:", valor = it) { novoNome -> nome = novoNome } }

        email?.let {
            CampoDeEntrada(label = "E-mail:", valor = it) { novoEmail ->
                email = novoEmail
            }
        }

        cep?.let { CampoDeEntrada(label = "CEP:", valor = it) { novoCep -> cep = novoCep } }

        dataNasc?.let {
            CampoDeEntrada(label = "Nascimento:", valor = it) { novaData ->
                dataNasc = novaData
            }
        }

        senha?.let {
            CampoDeEntrada(label = "Senha:", valor = it) { novaSenha ->
                senha = novaSenha
            }
        }

        BotaoSalvar(navController, valor = "Salvar") {
            navController.navigate("Login")

            val putUserData = apiConfigData.putConfigData(
                token = pk,
                id = 1,
                config = Configuracao(
                    nascimento = dataNasc
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
                                dataNasc
                            )

                            val putUserCep = apiConfigEndereco.putConfigEnde(
                                token = pk,
                                id = 1,
                                config = Configuracao(
                                    cep = cep
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
                                                cep
                                            )

                                            val putUserDados = apiConfigDadosUser.putConfigUser(
                                                token = pk,
                                                id = 1,
                                                config = UserConfig(
                                                    email = email,
                                                    nome = nome,
                                                    role = "PACIENTE",
                                                    senha = senha
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
                                                            acertoApi =
                                                                "Dados do Usuário cadastrado"

                                                        } else {
                                                            // Não foi possível achar usuário
                                                            erroApi = "Erro ao verificar usuário"
                                                        }
                                                    } else {
                                                        // Algo passado pode estar errado
                                                        erroApi =
                                                            "Erro na solicitação: ${response.code()}"
                                                    }
                                                }

                                                override fun onFailure(
                                                    call: Call<Configuracao>,
                                                    t: Throwable
                                                ) {
                                                    // Não foi possível conectar na api
                                                    erroApi = "Falha na solicitação: ${t.message}"
                                                }
                                            })

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
        }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampoDeEntrada(label: String, valor: String, onValueChange: (String) -> Unit) {
    val valorInput = remember { mutableStateOf(valor) }// Declare como uma variável mutável

    Column(
        modifier = Modifier
            .width(450.dp)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(55.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    fontSize = 20.sp,
                    fontFamily = fontRobotoBold,
                    color = Color.Black,
                    modifier = Modifier.padding(start = 16.dp)
                )
                TextField(
                    value = valor,
                    onValueChange = { onValueChange(it); valorInput.value = it },
                    singleLine = true,
                    textStyle = TextStyle(
                        fontSize = 17.sp,
                        fontFamily = fontRobotoBold,
                        color = Color.Black
                    ),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent
                    ),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun BotaoSalvar(navController: NavHostController, valor: String, onClick: () -> Unit) {
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