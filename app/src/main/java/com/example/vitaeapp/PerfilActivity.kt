package com.example.vitaeapp

import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.R.*
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import java.time.format.TextStyle

@Composable
fun TelaPerfil() {
    var nome = remember { mutableStateOf("") }
    var peso = remember { mutableStateOf("") }
    var altura = remember { mutableStateOf("") }
    var cpf = remember { mutableStateOf("") }


    Column {

        AtributoUsuario(stringResource(id = (string.ola)), 70, 15)
        QuadradoInfo()
        AtributoUsuario(stringResource(id = (string.title_input_nome)), 12, 5)
        InputGetInfo( valor = nome.value) { nome.value = it }
        AtributoUsuario(stringResource(id = (string.title_input_cpf)), 12, 10)
        InputGetInfo(valor = cpf.value) { cpf.value = it }
        AtributoUsuario(stringResource(id = (string.title_input_peso)), 12, 10)
        InputGetInfo(valor = peso.value) { peso.value = it }
        AtributoUsuario(stringResource(id = (string.title_input_altura)), 12, 10)
        InputGetInfo( valor = altura.value) { altura.value = it }
        BotaoEditar("Editar")

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
fun InputGetInfo( valor: String, onValueChange: (String) -> Unit) {
    val valorInput = remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .width(450.dp)
            .padding(start = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(25.dp) // Definindo a altura do Box como 50
                .background(Color.Transparent)
        ) {
            TextField(
                value = valorInput.value,
                onValueChange = { onValueChange(it); valorInput.value = it },
                singleLine = true,
                textStyle = androidx.compose.ui.text.TextStyle(fontSize = 17.sp,fontFamily=fontRobotoBold, color = Color.Black),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                ),
                modifier = Modifier.width(350.dp)
            )
        }

    }
}



@Composable
fun QuadradoComTexto(imagemTexto: String, textoQuadrado: String) {

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
                        Text(imagemTexto, fontSize = 30.sp, fontFamily = fontFamilyRowdies)
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
fun QuadradoInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
        ) {
            QuadradoComImagem(
                mipmap.check,
                stringResource(id = (string.title_perfil_apto_a_doacao))
            )
            Spacer(modifier = Modifier.width(10.dp))
            QuadradoComTexto(
                "5",
                stringResource(id = (string.title_perfil_quantidade_doacao))
            )

            Spacer(modifier = Modifier.width(10.dp))
            QuadradoComImagem(
                mipmap.tiposangue,
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
