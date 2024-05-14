package com.example.vitaeapp


import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.example.vitaeapp.api.RetrofitServices
import com.example.vitaeapp.classes.HemoCentroDetalhes
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class DetalheHemocentroActivity : ComponentActivity() {
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
                    TelaDetalheHemocentro("")
                }
            }
        }
    }
}

@Composable
fun TelaDetalheHemocentro(teste:String) {
    var teste = teste
    var isLoading by remember { mutableStateOf(true) }
    var nome = remember { mutableStateOf("") }
    var cep = remember { mutableStateOf("") }
    var numero = remember { mutableStateOf(0) }
    var bairro = remember { mutableStateOf("") }
    var rua = remember { mutableStateOf("") }
    val apiHemo = RetrofitServices.getDetalhesHemo()
    val get = apiHemo.getDetalhesUsuario(
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ2aXRhZS1zZXJ2aWNvcyIsInN1YiI6ImFtYXJlbG9AZ21haWwuY29tIiwiZXhwIjoxNzE1MzcwNzY4fQ.rNWkCHgp8XtPfMn2gg5c509bkagE1Uaqc8m0OiEKpL0",
        teste.toInt()
    )
    val erroApi = remember { mutableStateOf("") }
    LaunchedEffect(Unit) { // Executa quando o componente é iniciado
        get.enqueue(object : Callback<HemoCentroDetalhes> {
            override fun onResponse(
                call: Call<HemoCentroDetalhes>,
                response: Response<HemoCentroDetalhes>
            ) {
                if (response.isSuccessful) {
                    val obj = response.body()
                    if (obj != null) {
                        nome.value = obj.nome
                        rua.value = obj.rua
                        numero.value = obj.numero
                        cep.value = obj.cep
                        bairro.value = obj.bairro
                    } else {
                        erroApi.value = "Erro ao buscar detalhes"
                    }
                } else {
                    erroApi.value = "Erro na solicitação: ${response.code()}"
                }
                isLoading = false // Indica que os dados foram carregados
            }

            override fun onFailure(call: Call<HemoCentroDetalhes>, t: Throwable) {
                // Lógica para lidar com falha na solicitação
                isLoading = false // Indica que os dados foram carregados
            }
        })
    }

    if (isLoading) {
        // Exibe mensagem de carregamento enquanto os dados estão sendo carregados
        Text(text = "Carregando dados...")
    } else {
        val tiposSangue = remember {
            mutableStateListOf(
                "A+",
                "A-",
                "B+",
                "B-",
                "AB+",
                "AB-"
            )
        }

        val tiposSangue2 = remember {
            mutableStateListOf(
                "O+",
                "O-"
            )
        }
        // Exibe os campos com os valores recebidos da API
        Column(
            Modifier.padding(30.dp, 70.dp)
        ) {
            Text(
                text = nome.value,
                Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp),
                style = TextStyle(fontFamily = Rowdies, fontSize = 20.sp)
            )
            Text(text = "Local destinado ao atendimento de Doadores de sangue")


            Column(
                Modifier.padding(0.dp, 25.dp, 0.dp, 20.dp)
            ) {
                Text(
                    text = "Endereço:${rua.value}, ${numero.value} - ${bairro.value}", Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .width(350.dp)
                        .height(70.dp)
                        .padding(10.dp)
                        .size(50.dp)
                )
            }

            Column(
            ) {

                Text(
                    text = "Atendimento: Seg a Sab - 7:00 ás 12:00",
                    modifier = Modifier
                        .background(Color.White, shape = RoundedCornerShape(8.dp))
                        .width(350.dp)
                        .height(45.dp)
                        .padding(10.dp)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Prioridades de doações",
                style = TextStyle(fontFamily = Rowdies, fontSize = 20.sp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            tiposSangue(tiposSangue)
            tiposSangue(tiposSangue2)

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .height(40.dp)
                    .width(120.dp)
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#f24e4e")))
            ) {
                Text(text = "Agendar")
            }

        }
    }
    Logo(true)
}

@Composable
fun tiposSangue(lista: List<String>) {
    Row(
    ) {
        lista.forEach { item ->
            Box(
                modifier = Modifier
                    .border(
                        BorderStroke(1.dp, Color.Black),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                    .width(40.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
    Spacer(modifier = Modifier.height(15.dp))
}


@Composable
fun GreetingPreviewFromDetalhe(fkHemo:String) {
    VitaeAppTheme {
        TelaDetalheHemocentro(fkHemo)
    }
}