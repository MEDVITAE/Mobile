package com.example.vitaeapp

import android.icu.text.Transliterator.Position
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.Ranking
import com.example.vitaeapp.classes.Usuario
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Response

@Composable
fun TelaRanking(modifier: Modifier = Modifier) {
    Posicoes()
}

@Composable
fun Posicoes() {

    val ranking = remember {
        mutableStateListOf<Ranking>()
    }

    val erroApi = remember { mutableStateOf("") }

    val apiRanking = RetrofitServices.getApiRanking()

    val get = apiRanking.get("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImFtYXJlbEBnbWFpbC5jb20iLCJleHAiOjE3MTQ5NDcxNTV9._CmO0AMYX3Uz5-q68-yKU4ASCmIMVJixWWNvd1TuCgU")

    val scrollState = rememberScrollState()

    get.enqueue(object : retrofit2.Callback<List<Ranking>> {

        override fun onResponse(call: Call<List<Ranking>>, response: Response<List<Ranking>>) {
            if (response.isSuccessful) {
                val lista = response.body()
                if (lista != null) {
                    ranking.clear()
                    ranking.addAll(lista)
                }
            } else {
                erroApi.value = response.errorBody().toString()
            }
        }

        override fun onFailure(call: Call<List<Ranking>>, t: Throwable) {
            erroApi.value = t.message!!
        }

    })

    LaunchedEffect(Unit) {
        scrollState.animateScrollTo(100)
    }

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


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .verticalScroll(scrollState)
        ) {
            Column {
                if (erroApi.value.isNotBlank()) {
                    Text(erroApi.value, style = TextStyle(color = Color.Red))
                } else {
                    if (ranking.isEmpty()) {
                        Text("Sem doadores no ranking")
                    } else {
                        ranking.forEachIndexed { index, item ->
                            RankingItem(ranking = item, position = index + 1)
                        }
                    }
                }
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
                text = "${ranking.totalDoado}",
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
        TelaRanking()
    }
}
