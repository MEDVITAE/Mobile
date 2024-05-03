package com.example.vitaeapp

import android.content.Context
import android.content.SharedPreferences
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
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Caracteristicas
import com.example.vitaeapp.classes.UsuarioCadastro
import com.example.vitaeapp.classes.UsuarioLogin
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response

@Composable
fun TelaCadastro(navController: NavHostController, modifier: Modifier = Modifier) {

    val nomeCompleto = remember { mutableStateOf("") }
    val nascimento = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }
    val token = remember {
        mutableStateOf("")

    }

    val nomeCompletoError = remember { mutableStateOf("") }
    val nascimentoError = remember { mutableStateOf("") }
    val cpfError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val senhaError = remember { mutableStateOf("") }

    val isNomeCompletoValid = remember { mutableStateOf(true) }
    val isNascimentoValid = remember { mutableStateOf(true) }
    val isCpfValid = remember { mutableStateOf(true) }
    val isEmailValid = remember { mutableStateOf(true) }
    val isSenhaValid = remember { mutableStateOf(true) }

    val erroApi = remember { mutableStateOf("") }
    val acertoApi = remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val validacaoCadastro = remember {
            mutableStateOf(
                UsuarioCadastro("", "", "", "", ""),
            )
        }
        val validacaoCaracteristicas = remember {
            mutableStateOf(
                Caracteristicas("", "", false, "", "", false,0)
            )
        }

        val validacaoUsuario = remember {
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
            Spacer(modifier = Modifier.height(40.dp))
            AtributoUsuarioCadastroBemVindo(
                valor = "Seja Bem-vindo",
                paddingTop = 20,
                paddingBottom = 10,
                tamanho = 25
            )
            Spacer(modifier = Modifier.height(15.dp))

            AtributoUsuarioCadastro(
                valor = "Nome Completo",
                paddingTop = 0,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoCadastro(
                valorInput = nomeCompleto.value,
                exemplo = "Pedro Afonso",
                onValueChange = { nomeCompleto.value = it },
                isError = nomeCompletoError.value.isNotBlank(),
                errorMessage = nomeCompletoError.value,
                dica = if (nomeCompletoError.value.isBlank()) "O seu nome deve conter 3 ou mais letras" else "",
                isFieldValid = isNomeCompletoValid.value
            )

            AtributoUsuarioCadastro(
                valor = "Data Nascimento",
                paddingTop = 20,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoCadastro(
                valorInput = nascimento.value,
                exemplo = "01-01-1999",
                onValueChange = { nascimento.value = it },
                isError = nascimentoError.value.isNotBlank(),
                errorMessage = nascimentoError.value,
                dica = if (nascimentoError.value.isBlank()) "A data de nascimento deve ser 01-01-1999" else "",
                isFieldValid = isSenhaValid.value
            )

            AtributoUsuarioCadastro(
                valor = "CPF",
                paddingTop = 0,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoCadastro(
                valorInput = cpf.value,
                exemplo = "12345678910",
                onValueChange = { cpf.value = it },
                isError = cpfError.value.isNotBlank(),
                errorMessage = cpfError.value,
                dica = if (cpfError.value.isBlank()) "Insira apenas os números do seu CPF" else "",
                isFieldValid = isCpfValid.value
            )

            AtributoUsuarioCadastro(
                valor = "Email",
                paddingTop = 0,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoCadastro(
                valorInput = email.value,
                exemplo = "email@example.com",
                onValueChange = { email.value = it },
                isError = emailError.value.isNotBlank(),
                errorMessage = emailError.value,
                dica = if (emailError.value.isBlank()) "Insira um email no formato nome@email.com" else "",
                isFieldValid = isEmailValid.value
            )

            AtributoUsuarioCadastro(
                valor = "Senha",
                paddingTop = 20,
                paddingBottom = 10,
                tamanho = 20
            )
            InputGetInfoCadastro(
                valorInput = senha.value,
                exemplo = "********",
                onValueChange = { senha.value = it },
                isError = senhaError.value.isNotBlank(),
                errorMessage = senhaError.value,
                dica = if (senhaError.value.isBlank()) "A senha deve conter pelo menos 8 caracteres" else "",
                isFieldValid = isSenhaValid.value
            )

            // Exibir mensagens de erro
            if (nomeCompletoError.value.isNotBlank() ||
                nascimentoError.value.isNotBlank() ||
                cpfError.value.isNotBlank() ||
                emailError.value.isNotBlank() ||
                senhaError.value.isNotBlank()
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Por favor, corrija os campos incorretos.",
                    color = Color.Red,
                    fontSize = 14.sp,
                )
            }

            Spacer(modifier = Modifier.height(0.dp))
        }

        BotaoCadastro("Cadastre-se") {
            val cadastroUsuario = UsuarioCadastro(
                nome = nomeCompleto.value,
                cpf = cpf.value,
                email = email.value,
                senha = senha.value,
                role = "PACIENTE"
            )

            val caracteristicas = Caracteristicas(
                peso = "",
                altura = null,
                tatto = false,
                sexo = null,
                nascimento = nascimento.value,
                apto = false
            )
            cadastrarUser(cadastroUsuario, caracteristicas, acertoApi, erroApi)
        }

        if (erroApi.value.isNotBlank()) {
            Text("${erroApi.value}")
        } else if (acertoApi.value.isNotBlank()) {
            Text("${acertoApi.value}")
            Text("${validacaoCadastro.value}")
            Text("${validacaoCaracteristicas.value}")
        }
    }
}

fun cadastrarUser(
    cadastro: UsuarioCadastro,
    caracteristicas: Caracteristicas,
    acertoApi: MutableState<String>,
    erroApi: MutableState<String>,
) {
    val apiCadastro = RetrofitServices.getCadastroService()

    val postCad = apiCadastro.postCadastro(cadastro)
    postCad.enqueue(object : retrofit2.Callback<UsuarioCadastro> {
        override fun onResponse(call: Call<UsuarioCadastro>, response: Response<UsuarioCadastro>) {
            if (response.isSuccessful) {
                val usuario = response.body()
                if (usuario != null) {
                    acertoApi.value = "Usuário Cadastrado"
                    val tokenUsuario = UsuarioLogin(
                        email = cadastro.email,
                        senha = cadastro.senha
                    )

                    resgateToken(tokenUsuario, caracteristicas, acertoApi, erroApi)
                } else {
                    erroApi.value = "Erro ao cadastrar o usuário"
                }
            } else {
                erroApi.value = "Erro na solicitação cadastro: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<UsuarioCadastro>, t: Throwable) {
            erroApi.value = "Falha na solicitação: ${t.message}"
        }
    })
}

fun resgateToken(
    login: UsuarioLogin,
    caracteristicas: Caracteristicas,
    acertoApi: MutableState<String>,
    erroApi: MutableState<String>
) {
    val apiLogin = RetrofitServices.getLoginService()
    val postLogin = apiLogin.postLogin(login)
    postLogin.enqueue(object : retrofit2.Callback<UsuarioLogin> {
        override fun onResponse(call: Call<UsuarioLogin>, response: Response<UsuarioLogin>) {
            if (response.isSuccessful) {
                val usuario = response.body()
                if (usuario != null) {
                    acertoApi.value = "Usuário verificado"
                    caracteristicas.fkUsuario = usuario.Id
                        usuario.token?.let { cadastrarCaract(caracteristicas, acertoApi, erroApi, it) }
                } else {
                    erroApi.value = "Erro ao verificar usuário"
                }
            } else {
                erroApi.value = "Erro na solicitação Login: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<UsuarioLogin>, t: Throwable) {
            erroApi.value = "Falha na solicitação: ${t.message}"
        }
    })
}

fun cadastrarCaract(
    caracteristicas: Caracteristicas,
    acertoApi: MutableState<String>,
    erroApi: MutableState<String>,
    token: String
) {
    val apiCaracteristicas = RetrofitServices.getCaracteristicasService()
    val postCarac = apiCaracteristicas.postCaracteristicas(token, caracteristicas)
    postCarac.enqueue(object : retrofit2.Callback<Caracteristicas> {
        override fun onResponse(call: Call<Caracteristicas>, response: Response<Caracteristicas>) {
            if (response.isSuccessful) {
                acertoApi.value = "Características cadastradas"
            } else {
                erroApi.value = "Erro ao cadastrar as características: ${response.code()}"
            }
        }

        override fun onFailure(call: Call<Caracteristicas>, t: Throwable) {
            erroApi.value = "Falha na solicitação: ${t.message}"
        }
    })
}

fun validarNomeCompleto(nomeCompleto: String): Boolean {
    return nomeCompleto.isNotBlank() && nomeCompleto.length >= 3
}

fun validarDtNasc(dtNasc: String): Boolean {
    return dtNasc.isNotBlank() && dtNasc.length == 10
}

fun validarCPF(cpf: String): Boolean {
    return cpf.isNotBlank() && cpf.length == 11
}

fun validarEmail(email: String): Boolean {
    return email.isNotBlank() && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

fun validarSenha(senha: String): Boolean {
    return senha.isNotBlank() && senha.length >= 8
}

@Composable
fun AtributoUsuarioCadastroBemVindo(
    valor: String,
    paddingTop: Int,
    paddingBottom: Int,
    tamanho: Int
) {
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
fun AtributoUsuarioCadastro(valor: String, paddingTop: Int, paddingBottom: Int, tamanho: Int) {
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
fun InputGetInfoCadastro(
    valorInput: String,
    exemplo: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    dica: String,
    isFieldValid: Boolean
) {
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
fun BotaoCadastro(valor: String, onClick: () -> Unit) {
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
                    valor, fontSize = 18.sp,
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
fun GreetingPreviewFromCadastro() {
    val navController = rememberNavController()
    VitaeAppTheme {
        TelaCadastro(rememberNavController())
    }
}