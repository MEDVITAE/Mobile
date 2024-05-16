package com.example.vitaeapp

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vitaeapp.classes.MenuItem
import com.example.vitaeapp.ui.theme.VitaeAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.azul_claro)
                ) {
                    Tela(rememberNavController())
                }
            }
        }
    }
}

val fontFamilyRowdies = FontFamily(
    Font(R.font.rowdies_light)
)
val fontFamilyRowdiesBold = FontFamily(
    Font(R.font.rowdies_bold)
)
val fontRobotoRegular = FontFamily(
    Font(R.font.roboto_regular)
)
val fontRobotoBold = FontFamily(
    Font(R.font.roboto_bold)
)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Tela(navController: NavHostController, modifier: Modifier = Modifier) {
    Logo(false)
    NavHost(
        navController = navController,
        startDestination = "Login"
    ) {

        composable("Cadastro") {
            TelaCadastro(navController)
        }
        composable("Login") {
            TelaLogin(navController)
        }
        composable("Mapa/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            ChamaMaps(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("Perfil/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            TelaPerfil(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("Configuracao/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            TelaDeConfiguracao(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("Historico/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            TelaHistorico(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("DetalheHemo/{token}/{id}/{idHemo}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            },
            navArgument("idHemo") {
                type = NavType.IntType
            }
        )) {
            TelaDetalheHemocentro(
                navController,
                token = it.arguments?.getString("token") ?: "",
                id = it.arguments?.getInt("id") ?: 0,
                idHemo = it.arguments?.getInt("idHemo") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("Ranking/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            TelaRanking(
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("Agenda/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            TelaAgendamento(
                navController,
                token = it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
        composable("Quiz/{token}/{id}", arguments = listOf(
            navArgument("token") {
                type = NavType.StringType
            },
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            TelaQuiz(
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
            Menu(
                navController,
                it.arguments?.getString("token") ?: "",
                it.arguments?.getInt("id") ?: 0
            )
        }
    }
}


@Composable
fun Logo(logoPosicao: Boolean) {
    if (logoPosicao) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp), Arrangement.End
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Vitae",
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 10.dp)

            )
        }
    } else {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.mipmap.logo),
                contentDescription = "Vitae",
                modifier = Modifier
                    .size(70.dp)
                    .padding(top = 10.dp)

            )
        }
    }
}

@Composable
fun Menu(navController: NavHostController, token: String, id: Int) {
    val listaMenu = remember {
        mutableStateListOf(
            MenuItem(R.mipmap.maps, "Mapa/${token}/${id}"),
            MenuItem(R.mipmap.historico, "Historico/${token}/${id}"),
            MenuItem(R.mipmap.ranking, "Ranking/${token}/${id}"),
            MenuItem(R.mipmap.sangue, "Quiz/${token}/${id}"),
            MenuItem(R.mipmap.agenda, "Agenda/${token}/${id}"),
            MenuItem(R.mipmap.perfil, "Perfil/${token}/${id}"),
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
                        navController.navigate(itemId.tela)
                    },
                    modifier = Modifier.size(55.dp)
                ) {
                    Image(
                        painter = painterResource(id = itemId.icon),
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VitaeAppTheme {
        Tela(rememberNavController())
    }
}