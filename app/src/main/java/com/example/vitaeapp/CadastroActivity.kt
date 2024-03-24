package com.example.vitaeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.ui.theme.VitaeAppTheme

class CadastroActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.azul_claro)
                ) {
                    TelaCadastro("Android")
                }
            }
        }
    }
}

@Composable
fun TelaCadastro(name: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Logo()
        val entradaTexto by remember { mutableStateOf("") }

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
                25
            )
            Spacer(modifier = Modifier.height(15.dp))

            AtributoUsuarioCadastro(valor = "Nome Completo", paddingTop = 0, paddingBottom = 10, 20)
            InputGetInfoCadastro(valorInput = "Pedro Afonso")

            AtributoUsuarioCadastro(valor = "CPF", paddingTop = 0, paddingBottom = 10, 20)
            InputGetInfoCadastro(valorInput = "12345678910")

            AtributoUsuarioCadastro(valor = "Email", paddingTop = 0, paddingBottom = 10, 20)
            InputGetInfoCadastro(valorInput = "email@gmail.com")

            AtributoUsuarioCadastro(valor = "Senha", paddingTop = 20, paddingBottom = 10, 20)
            InputGetInfoCadastro(valorInput = "********")

            Spacer(modifier = Modifier.height(0.dp))
        }
        BotaoCadastro("Cadastre-se")

    }
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
fun InputGetInfoCadastro(valorInput: String) {
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
fun BotaoCadastro(valor:String){
    Box(  modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(top = 10.dp)
        .background(Color.Transparent, shape = RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center,

        ) {

        val contexto = LocalContext.current

        IconButton(
            modifier = Modifier
                .width(210.dp)
                .height(45.dp),
            onClick = {
                contexto.startActivity(Intent(contexto, LoginActivity::class.java))
            }
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
        TelaCadastro("Android")
    }
}