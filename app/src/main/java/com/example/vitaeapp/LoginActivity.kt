package com.example.vitaeapp

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.ui.platform.LocalContext
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
    val context = LocalContext.current

    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }

    val emailError = remember { mutableStateOf("") }
    val senhaError = remember { mutableStateOf("") }

    val isEmailValid = remember { mutableStateOf(true) }
    val isSenhaValid = remember { mutableStateOf(true) }

    val erroApi = remember { mutableStateOf("") }
    val acertoApi = remember { mutableStateOf("") }

    val apiLogin = RetrofitServices.getLoginService()

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
                isError = emailError.value.isNotBlank(),
                errorMessage = emailError.value,
                dica = if (emailError.value.isBlank()) "Insira um email válido" else "",
                isFieldValid = isEmailValid.value
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
                isError = senhaError.value.isNotBlank(),
                errorMessage = senhaError.value,
                dica = if (senhaError.value.isBlank()) "A senha deve conter pelo menos 6 caracteres" else "",
                isFieldValid = isSenhaValid.value
            )

            if (emailError.value.isNotBlank() || senhaError.value.isNotBlank()) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Por favor, corrija os campos incorretos.",
                    color = Color.Red,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            BotaoLogin("Entrar") {

                val usuario =
                    UsuarioLogin(email = email.value, senha = senha.value)

                val post = apiLogin.postLogin(usuario)

                post.enqueue(object : retrofit2.Callback<UsuarioLogin> {
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

                            } else {
                                // Não foi possível achar usuário
                                erroApi.value = "Erro ao verificar usuário"
                            }
                        } else {
                            // Algo passado pode estar errado
                            erroApi.value = "Erro na solicitação: ${response.code()}"
                        }
                    }

                    override fun onFailure(call: Call<UsuarioLogin>, t: Throwable) {
                        // Não foi possível conectar na api
                        erroApi.value = "Falha na solicitação: ${t.message}"
                    }
                })
            }
                if (erroApi.value.isNotBlank()) {
                    Text("Ouve um erro ao tentar efetuar login, verifique os parametros passados")
                } else if (acertoApi.value.isNotBlank()) {
                    Text("${acertoApi.value}")
                    navController.navigate("Perfil")
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
fun InputGetInfoLogin(valorInput: String,exemplo: String, onValueChange: (String) -> Unit, isError: Boolean, errorMessage: String, dica: String, isFieldValid: Boolean) {
    val fieldColor = if (isFieldValid) Color.Black else Color.Red

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
        // Exibir mensagem de erro se houver
        if (isError) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 15.dp, top = 4.dp)
            )
        }
        // Exibir dica se o campo estiver vazio e não houver erro
        if (valorInput.isEmpty() && !isError) {
            Text(
                text = dica,
                color = Color.Black,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 15.dp, top = 4.dp)
            )
        }
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

fun isEmailValid(email: String): Boolean {
    val emailRegex = Regex("^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}\$")
    return emailRegex.matches(email)
}
fun isPasswordValid(password: String): Boolean {
    return password.length >= 8
//            OPÇÕES DE SEGURANÇA DE SENHA PARA ADICINAR NO FUTURO
//            && password.any { it.isUpperCase() } // Pelo menos uma letra maiúscula
//            && password.any { it.isLowerCase() } // Pelo menos uma letra minúscula
//            && password.any { it.isDigit() } // Pelo menos um número
//            && password.any { !it.isLetterOrDigit() } // Pelo menos um caractere especial
}


@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromLogin() {
    VitaeAppTheme {
        TelaLogin(rememberNavController())
    }
}