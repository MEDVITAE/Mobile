package com.example.vitaeapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import org.jetbrains.annotations.ApiStatus.Internal

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Tela("Android")
                }
            }
        }
    }
}

val fontFamilyRowdies = FontFamily(
    Font(R.font.rowdies_light)
)
val fontRobotoRegular = FontFamily(
    Font(R.font.roboto_regular)
)
val fontRobotoBold = FontFamily(
    Font(R.font.roboto_bold)
)

@Composable
fun Tela(name: String, modifier: Modifier = Modifier) {
    Logo()
    TelaHistorico()
    Menu()
}

@Composable
fun Logo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp), Arrangement.End
    ) {
        Image(
            painter = painterResource(id = R.mipmap.logo),
            contentDescription = "Vitae",
            modifier = Modifier.size(70.dp)
        )
    }
}

@Composable
fun Menu() {
    val contexto = LocalContext.current

    val listaMenu = remember {
        mutableStateListOf(
            MenuItem(R.mipmap.maps, ""),
            MenuItem(R.mipmap.historico, ""),
            MenuItem(R.mipmap.ranking, ""),
            MenuItem(R.mipmap.sangue, ""),
            MenuItem(R.mipmap.agenda, ""),
            MenuItem(R.mipmap.perfil, ""),
        )
    }

    Row(
        modifier = Modifier.fillMaxHeight(),
        verticalAlignment = Alignment.Bottom
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp)
        ) {
            Spacer(modifier = Modifier.width(8.dp))
            listaMenu.forEach { itemId ->
                IconButton(
                    onClick = {
                        val telaConfig = Intent(contexto, ConfigActivity::class.java)

                        contexto.startActivity(telaConfig)
                    },
                    modifier = Modifier.size(55.dp)
                ) {
                    Image(
                        painter = painterResource(id = itemId.iconId),
                        contentDescription = "",
                        modifier = Modifier.size(55.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VitaeAppTheme {
        Tela("Android")
    }
}