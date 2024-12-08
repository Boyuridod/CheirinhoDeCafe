package br.com.yuriduarte.cheirinhodecafe

import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.jeffersonbm.fazenda02.DAO
import br.com.yuriduarte.cheirinhodecafe.ui.theme.CheirinhoDeCaféTheme
import com.google.firebase.Firebase
import com.google.firebase.database.database

class TelaListaCafe : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CheirinhoDeCaféTheme {

                TelaListaCafes()

            }
        }
    }
}

@Composable
fun TelaListaCafes(){

    val banco = DAO(Firebase.database.getReference("Cafe"))

    var listaCafe by remember { mutableStateOf<List<String>>(emptyList()) }

    banco.mostrarDados { lista ->
        if (lista.isNotEmpty()) {
            Log.i("TesteCafe", "Deu bom " + lista[0])
            listaCafe = lista // Atualiza o estado da lista
        } else {
            Log.i("TesteCafe", "Lista vazia ou erro no carregamento")
        }
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
            verticalArrangement = Arrangement.SpaceEvenly
        ){

            listaCafe.forEach { cafe ->

                Button(
                    onClick = {
                        //TODO
                    },
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                ) {
                    Text(text = cafe)
                }
                Log.i("TesteCafe", "Inseri " + cafe)
            }

        }

    }

}
