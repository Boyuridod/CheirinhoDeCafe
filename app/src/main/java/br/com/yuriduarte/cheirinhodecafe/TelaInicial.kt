package br.com.yuriduarte.cheirinhodecafe

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.yuriduarte.cheirinhodecafe.ui.theme.CheirinhoDeCaféTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheirinhoDeCaféTheme {

                TelaInicial()

            }
        }
    }
}

@Composable
fun TelaInicial(){

    val contexto = LocalContext.current

    Scaffold (
        Modifier.fillMaxHeight()
    ){
        paddingValues ->
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ){
            Button(
                onClick = {

                    val intentTelaInserir = Intent(contexto, TelaInsercao::class.java)
                    contexto.startActivity(intentTelaInserir)

                },
                shape = RoundedCornerShape(5.dp)
            ) {

                Text("Iserir Café")

            }

            Button(
                onClick = {
                    //TODO: Bot
                },
                shape = RoundedCornerShape(5.dp)
            ) {

                Text("Lista de Cafés")

            }
        }

    }

}

@Composable
@Preview
fun MostraTelaInicial(){

    TelaInicial()

}