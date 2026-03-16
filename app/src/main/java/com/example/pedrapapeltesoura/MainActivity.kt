package com.example.pedrapapeltesoura

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pedrapapeltesoura.ui.theme.PedraPapelTesouraTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PedraPapelTesouraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TelaInicial(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TelaInicial(modifier: Modifier = Modifier) {
    val context = LocalContext.current

    // Lista de opções com IDs de recursos e nomes
    val options = remember {
        listOf(
            R.drawable.pedra to "Pedra",
            R.drawable.papel to "Papel",
            R.drawable.tesoura to "Tesoura"
        )
    }

    var currentImageIndex by remember { mutableIntStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var resultText by remember { mutableStateOf("Toque em JOGAR para começar!") }
    val scope = rememberCoroutineScope()
    

    fun playSound(soundRes: Int) {
        try {
            val mp = MediaPlayer.create(context, soundRes)
            mp.setOnCompletionListener { it.release() } // Libera memória ao terminar
            mp.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PEDRA, PAPEL E TESOURA",
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 40.dp)
        )


        Surface(
            modifier = Modifier
                .size(220.dp)
                .padding(8.dp),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = options[currentImageIndex].first),
                    contentDescription = options[currentImageIndex].second,
                    modifier = Modifier.fillMaxSize(0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(48.dp))

        // Texto de resultado ou status
        Text(
            text = resultText,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(60.dp))

        // Botão principal
        Button(
            onClick = {
                if (!isPlaying) {
                    isPlaying = true
                    resultText = "Sorteando..."
                    

                    playSound(R.raw.roleta)
                    
                    scope.launch {

                        repeat(18) {
                            currentImageIndex = (currentImageIndex + 1) % options.size
                            delay(185)
                        }

                        val randomIndex = Random.nextInt(options.size)
                        currentImageIndex = randomIndex
                        resultText = "Você tirou:\n${options[randomIndex].second.uppercase()}!"

                        // O Android renomeia automaticamente arquivos para minúsculas
                        // Então mesmo que o arquivo seja Resultado.m4a, no código usamos r.raw.resultado
                        playSound(R.raw.resultado)

                        isPlaying = false
                    }
                }
            },
            enabled = !isPlaying,
        ) {
            Text(
                text = if (isPlaying) "SORTEANDO..." else "JOGAR",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    PedraPapelTesouraTheme {
        TelaInicial()
    }
}
