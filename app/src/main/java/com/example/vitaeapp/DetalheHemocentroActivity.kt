package com.example.vitaeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vitaeapp.ui.theme.Rowdies
import com.example.vitaeapp.ui.theme.VitaeAppTheme

@Composable
fun TelaDetalheHemocentro(modifier: Modifier = Modifier) {
    val tiposSangue = remember {
        mutableStateListOf(
            "A+",
            "A-",
            "B+",
            "B-",
            "AB+",
            "AB-",
            "O+",
            "O-"
        )
    }

    val tiposSangue2 = remember {
        mutableStateListOf(
            "O+",
            "O-"
        )
    }

    Column(
        Modifier.padding(30.dp, 70.dp)
    ){
        Text(
            text = "Hemocentro São Paulo",
            Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp),
            style = TextStyle(fontFamily = Rowdies, fontSize = 20.sp)
        )
        Text(text = "Local destinado ao atendimento de Doadores de sangue")


        Column(
            Modifier.padding(0.dp, 25.dp, 0.dp, 20.dp)
        ){
            Text(text = "Endereço: Rua morro das pedras, 120 - São Paulo", Modifier
                .background(Color.White)
                .width(350.dp)
                .height(70.dp)
                .padding(10.dp)
                .size(50.dp))
        }
        Column(
            Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)
        ){

            Text(text = "Endereço: Rua morro das pedras, 120 - São Paulo", Modifier
                .background(Color.White)
                .width(350.dp)
                .height(70.dp)
                .padding(10.dp))
        }

        Text(
            text = "Prioridades de doações",
            Modifier.padding(0.dp, 0.dp, 0.dp, 15.dp),
            style = TextStyle(fontFamily = Rowdies, fontSize = 20.sp)
        )

        tiposSangue(tiposSangue)
        tiposSangue(tiposSangue2)

        Button(
            onClick = { /*TODO*/ },
            Modifier
                .fillMaxSize()
                .padding(95.dp, 100.dp),
            colors = ButtonDefaults.buttonColors(Color(android.graphics.Color.parseColor("#f24e4e")))
        ) {
            Text(text = "Agendar")
        }
    }
}

@Composable
fun tiposSangue(lista: List<String>){

    Row() {
        lista.forEach { item ->
            Text(text = item, Modifier
                .background(color = Color.White)
                .padding(10.dp))
            Spacer(modifier = Modifier.width(15.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreviewFromDetalhe() {
    VitaeAppTheme {
        TelaDetalheHemocentro()
    }
}