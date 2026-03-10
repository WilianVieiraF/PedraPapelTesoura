package com.example.pedrapapeltesoura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.pedrapapeltesoura.ui.theme.PedraPapelTesouraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {

            TelaInicial()

        }
    }
}

@Composable
fun TelaInicial(){
    Column(

    ) {

        Text("Jogo de Pedra, Papel e Tesoura")

        Button(onClick = {

        }) {
            Text("Clique para Jogar")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun previewHome(){
    TelaInicial()
}