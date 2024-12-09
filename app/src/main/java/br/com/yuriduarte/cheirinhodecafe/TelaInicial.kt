package br.com.yuriduarte.cheirinhodecafe

import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.yuriduarte.cheirinhodecafe.ui.theme.CheirinhoDeCaféTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

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

    val banco = DAO(Firebase.database.getReference("Cafe"))

    var informacoes by remember { mutableStateOf<List<String>>(emptyList()) }

    var refreshState by remember { mutableStateOf(0) } // Variável para controlar o refresh

    banco.informacoes { infos->
        informacoes = infos
    }

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

            if (informacoes.isNotEmpty()) {
                Text("Total de ${informacoes[0]} cafés cadastrados")
            }

            Button(
                onClick = {

                    val intentTelaInserir = Intent(contexto, TelaInsercao::class.java)
                    contexto.startActivity(intentTelaInserir)

                    banco.informacoes { infos->
                        informacoes = infos
                    }

                    refreshState++ // Incrementa o estado para "forçar" um refresh

                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(
                        start = 50.dp,
                        end = 50.dp
                    )
            ) {

                Text("Iserir Café")

            }

            Button(
                onClick = {
                    val intentTelaLista = Intent(contexto, TelaListaCafe::class.java)
                    contexto.startActivity(intentTelaLista)

                    banco.informacoes { infos->
                        informacoes = infos
                    }

                },
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(
                        start = 50.dp,
                        end = 50.dp,
                    )
            ) {

                Text("Lista de Cafés")

            }

            if (informacoes.isNotEmpty()) {
                Text("Id do café mais caro: " + informacoes[1])
                Text("Média de preço dos cafés: R$ " + formatarNumero(informacoes[2]))
                Text("Id do café com mais aroma: " + informacoes[3])
                Text("Id do café menos acidez: " + informacoes[4])
            }

        }

    }

}

@Composable
@Preview
fun MostraTelaInicial(){

    TelaInicial()

}

fun formatarNumero(numero: String): String {
    // Cria o padrão de formatação
    val formato = DecimalFormat("#,##0.00")

    // Substitui o ponto por vírgula
    return formato.format(numero.toDouble()).replace(".", ",")
}