package com.example.vitaeapp


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme

class RankingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VitaeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorResource(id = R.color.azul_claro)
                ) {
                    Ranking("Android")
                }
            }
        }
    }
}

@Composable
fun Ranking(name: String, modifier: Modifier = Modifier) {
    val listaRanking = remember {
        mutableStateListOf(
            Ranking(nome = "Vinicios Garcia", pontuacao = 10),
            Ranking(nome = "Pedro Afonso", pontuacao = 9),
            Ranking(nome = "Wilian Paternezi", pontuacao = 8),
            Ranking(nome = "Maessio Damasceno", pontuacao = 9),
            Ranking(nome = "Diego Costa", pontuacao = 11),
        )
    }

    Logo()
    Posicoes(lista = listaRanking)
    Menu()
}

@Composable
fun Posicoes(lista: List<Ranking>) {

    var nome: String = ""
    var pontuacao: Int = 0

    Column(
        Modifier.padding(30.dp, 100.dp)
    ) {
        Row {
            Column {
                Text(
                    "RANKING",
                    fontSize = 36.sp,
                    style = TextStyle(fontFamily = Rowdies),
                )
                Text(
                    "MAIORES DOADORES",
                    modifier = Modifier.offset(y = (-24).dp),
                    fontSize = 13.sp,
                    style = TextStyle(fontFamily = Rowdies)
                )
            }
        }

        val listaOrdenada = lista.sortedByDescending { it.pontuacao }

        Column() {
            // Iterar sobre a lista ordenada para exibir cada item
            listaOrdenada.forEachIndexed { index, item ->
                RankingItem(ranking = item, position = index + 1)
            }
        }
    }
}

@Composable
fun RankingItem(ranking: Ranking, position: Int) {

    val topRankImages = listOf(
        R.mipmap.primeiro,
        R.mipmap.segundo,
        R.mipmap.terceiro,
    )

    Column(
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 50.dp)
            .background(Color.White)
            .height(60.dp)
            .width(350.dp)
            .shadow(0.dp, shape = RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (position in 1..3) {
                Image(
                    painter = painterResource(id = topRankImages[position - 1]),
                    contentDescription = "$position",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(6.dp)
                )
            } else {
                Text(
                    text = "$position",
                    style = TextStyle(
                        fontFamily = Rowdies,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(14.dp)
                )
            }

            Spacer(modifier = Modifier.width(20.dp))

            Text(
                text = "${ranking.nome}",
                style = TextStyle(
                    fontFamily = Rowdies,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = "${ranking.pontuacao}",
                style = TextStyle(
                    fontFamily = Rowdies,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(14.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromRanking() {
    VitaeAppTheme {
        Ranking("Android")
    }
}