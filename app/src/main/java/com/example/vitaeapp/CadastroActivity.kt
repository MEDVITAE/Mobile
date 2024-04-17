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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.ui.theme.VitaeAppTheme

@Composable
fun TelaCadastro(navController: NavHostController, modifier: Modifier = Modifier) {

    val nomeCompleto = remember { mutableStateOf("") }
    val dtnasc = remember { mutableStateOf("") }
    val cpf = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val senha = remember { mutableStateOf("") }

    val nomeCompletoError = remember { mutableStateOf("") }
    val dtNascError = remember { mutableStateOf("") }
    val cpfError = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf("") }
    val senhaError = remember { mutableStateOf("") }

    val isNomeCompletoValid = remember { mutableStateOf(true) }
    val isDtNascValid = remember { mutableStateOf(true) }
    val isCpfValid = remember { mutableStateOf(true) }
    val isEmailValid = remember { mutableStateOf(true) }
    val isSenhaValid = remember { mutableStateOf(true) }


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                valorInput = dtnasc.value,
                exemplo = "01-01-1999",
                onValueChange = { dtnasc.value = it },
                isError = dtNascError.value.isNotBlank(),
                errorMessage = dtNascError.value,
                dica = if (dtNascError.value.isBlank()) "A data de nascimento deve ser 01-01-1999" else "",
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
                dtNascError.value.isNotBlank() ||
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

            isNomeCompletoValid.value = validarNomeCompleto(nomeCompleto.value)
            isDtNascValid.value = validarDtNasc(dtnasc.value)
            isCpfValid.value = validarCPF(cpf.value)
            isEmailValid.value = validarEmail(email.value)
            isSenhaValid.value = validarSenha(senha.value)


            if (!isNomeCompletoValid.value || !isCpfValid.value || !isEmailValid.value || !isSenhaValid.value || !isDtNascValid.value) {
                nomeCompletoError.value = if (!isNomeCompletoValid.value) "Nome inválido" else ""
                dtNascError.value = if (!isDtNascValid.value) "Data Nascimento inválida" else ""
                cpfError.value = if (!isCpfValid.value) "CPF inválido" else ""
                emailError.value = if (!isEmailValid.value) "Email inválido" else ""
                senhaError.value = if (!isSenhaValid.value) "Senha inválida" else ""
            } else {
                navController.navigate("Login")
            }
        }
    }
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
fun AtributoUsuarioCadastroBemVindo(valor: String, paddingTop: Int, paddingBottom: Int,tamanho:Int) {
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
fun AtributoUsuarioCadastro(valor: String, paddingTop: Int, paddingBottom: Int,tamanho:Int) {
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
fun BotaoCadastro(valor:String, onClick: () -> Unit){
    Box(  modifier = Modifier
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
    VitaeAppTheme {
        TelaCadastro(rememberNavController())
    }
}