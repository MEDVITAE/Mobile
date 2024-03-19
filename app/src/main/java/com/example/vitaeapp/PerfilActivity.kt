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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.R.*

@Composable
fun TelaPerfil() {
    Logo()
    Column {

        AtributoUsuario(stringResource(id = (string.ola)), 70, 15)
        QuadradoInfo()
        AtributoUsuario(stringResource(id = (string.title_input_nome)), 12, 5)
        InputGetInfo("Joao Vitor de Souza Tenorio")
        AtributoUsuario(stringResource(id = (string.title_input_cpf)), 12, 10)
        InputGetInfo(valorInput = "413654667-97")
        AtributoUsuario(stringResource(id = (string.title_input_peso)), 12, 10)
        InputGetInfo(valorInput = "68 kg")
        AtributoUsuario(stringResource(id = (string.title_input_altura)), 12, 10)
        InputGetInfo(valorInput = "1,70")
        BotaoEditar("Editar")

    }
    Menu()
}



@Composable
fun AtributoUsuario(valor: String, paddingTop: Int, paddingBottom: Int) {
    Column {
        Text(
            valor, fontSize = 20.sp, fontFamily = fontRobotoRegular, modifier =
            Modifier.padding(
                top = paddingTop.dp,
                start = 15.dp,
                end = 20.dp,
                bottom = paddingBottom.dp
            )
        )
    }
}

@Composable
fun InputGetInfo(valorInput: String) {
    Column(
        modifier = Modifier
            .width(300.dp)
            .padding(start = 15.dp)
    ) {
        Text(valorInput, fontSize = 16.sp, fontFamily = fontRobotoBold)
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = Color.Black
        )
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
                        modifier = Modifier.fillMaxWidth().height(48.dp),
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
fun BotaoEditar(valor:String){
    Box(  modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(top = 10.dp)
        .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        Row(

            modifier = Modifier
                .width(200.dp)
                .height(45.dp)
                .background(color = colorResource(id = color.vermelho_rosado), shape = RoundedCornerShape(16.dp))
                .border(
                    color = Color.Black,
                    width = 2.dp,
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,

            ){

            Text(
                valor, fontSize = 18.sp, fontFamily = fontRobotoBold
            )
            Image(
                painter = painterResource(id = mipmap.seta_direita),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
            )
        }

    }
}
