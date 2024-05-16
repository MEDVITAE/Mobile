package com.example.vitaeapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.UsuarioLogin
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response

@Composable
fun TelaLogin(navController: NavHostController, modifier: Modifier = Modifier) {

    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }


    val erroApi = remember { mutableStateOf("") }
    val acertoApi = remember { mutableStateOf("") }
    val loginClicado = remember { mutableStateOf(false) }



    Logo(logoPosicao = false)

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val validacao = remember {
            mutableStateOf(
                UsuarioLogin(0, "", "", "")
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {

            BtnIrParaCadastro(navController)

            Spacer(modifier = Modifier.height(40.dp))
            AtributoUsuarioLoginBemVindo(
                valor = "Bem-vindo de volta",
                paddingTop = 20,
                paddingBottom = 10,
                tamanho = 20
            )
            Spacer(modifier = Modifier.height(15.dp))

            AtributoUsuarioLogin(
                valor = "Email",
                paddingTop = 0,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoLogin(
                valorInput = email.value,
                exemplo = "email@gmail.com",
                onValueChange = { email.value = it },
                validationFunction = ::validarEmail,
                loginClicado = loginClicado.value
            )

            AtributoUsuarioLogin(
                valor = "Senha",
                paddingTop = 20,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoLogin(
                valorInput = senha.value,
                exemplo = "********",
                onValueChange = { senha.value = it },
                validationFunction = ::validarSenha,
                loginClicado = loginClicado.value
            )

        }

        Spacer(modifier = Modifier.height(5.dp))
        if (erroApi.value.isNotBlank()) {
            Text("Ouve um erro ao tentar efetuar login, verifique os valores preenchidos")
        }
        BotaoLogin("Entrar") {
            loginClicado.value = true
            if (validarEmail(email.value) &&
                validarSenha(senha.value)
            ) {
                val usuario = UsuarioLogin(email = email.value, senha = senha.value)
                val apiLogin = RetrofitServices.getLoginService()

                val postLog = apiLogin.postLogin(usuario)
                postLog.enqueue(object : retrofit2.Callback<UsuarioLogin> {
                    override fun onResponse(
                        call: Call<UsuarioLogin>,
                        response: Response<UsuarioLogin>
                    ) {
                        if (response.isSuccessful) {
                            val usuario = response.body()
                            if (usuario != null) {
                                acertoApi.value = "Usuário verificado"
                                validacao.value = UsuarioLogin(
                                    usuario.Id,
                                    usuario.nome,
                                    usuario.token
                                )
                                navController.navigate("Perfil")
                            } else {
                                erroApi.value = "Erro ao verificar usuário"
                            }
                        } else {
                            erroApi.value = "Erro na solicitação: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<UsuarioLogin>, t: Throwable) {
                        erroApi.value = "Falha na solicitação: ${t.message}"
                    }
                })
            } else {
                erroApi.value = "Por favor, corrija os campos incorretos."
            }
        }

    }
}

@Composable
fun BtnIrParaCadastro(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "Cadastre-se",
            fontSize = 16.sp,
            color = Color.Black,
            textDecoration = TextDecoration.Underline,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate("Cadastro")
                }
        )
    }
}


@Composable
fun AtributoUsuarioLoginBemVindo(valor: String, paddingTop: Int, paddingBottom: Int, tamanho: Int) {
    Column {
        Text(
            valor,
            fontSize = tamanho.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(
                top = paddingTop.dp,
                start = 15.dp,
                end = 20.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun AtributoUsuarioLogin(valor: String, paddingTop: Int, paddingBottom: Int, tamanho: Int) {
    Column {
        Text(
            valor,
            fontSize = tamanho.sp,
            modifier = Modifier.padding(
                top = paddingTop.dp,
                start = 15.dp,
                end = 20.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun InputGetInfoLogin(
    valorInput: String,
    exemplo: String,
    onValueChange: (String) -> Unit,
    validationFunction: (String) -> Boolean,
    loginClicado: Boolean
) {
    val isError = !validationFunction(valorInput) && valorInput.isNotBlank() && loginClicado
    val fieldColor = if (isError) Color.Red else Color.Black

    Column(
        modifier = Modifier
            .width(300.dp)
            .padding(start = 15.dp)
    ) {
        BasicTextField(
            value = valorInput,
            onValueChange = { onValueChange(it) },
            modifier = Modifier.background(color = Color.Transparent),
            textStyle = LocalTextStyle.current.copy(color = fieldColor),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(contentAlignment = Alignment.CenterStart) {
                    innerTextField()
                    if (valorInput.isEmpty()) {
                        Text(
                            text = exemplo,
                            color = Color.Black,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }
        )
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = fieldColor
        )
        Text(
            text = "",
            color = fieldColor,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 15.dp, top = if (isError) 8.dp else 8.dp)
        )
    }
}

@Composable
fun BotaoLogin(valor: String, onClick: () -> Unit) {
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
                        width = 2.dp,
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,

                ) {

                Text(
                    "Entrar", fontSize = 18.sp,
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


@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromLogin() {
    VitaeAppTheme {
        TelaLogin(rememberNavController())
    }
}