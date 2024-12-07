package br.com.jeffersonbm.fazenda02

import android.util.Log
import br.com.yuriduarte.cheirinhodecafe.Cafe
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson

class DAO (banco : DatabaseReference){
    var banco : DatabaseReference
    init{
        this.banco = banco
    }
    fun inserir_atualizar(cafe: Cafe){
        this.banco.child(cafe.id).setValue(cafe)
    }
    /*fun mostrarDados(): ArrayList<String>{
        var listaFazendas = ArrayList<String>()
        listaFazendas.add("Olá Coutinho")
        this.banco.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val gson = Gson()
                    Log.i("TESTE","------")
                    for (i in snapshot.children) {
                        val json = gson.toJson(i.value)
                        val fazenda = gson.fromJson(json, Fazenda::class.java)
                        listaFazendas.add(fazenda.toString())
                        Log.i("TESTE","Fazenda: "+fazenda.toString())
                    }
                    Log.i("TESTE","------")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.i("Teste", "Erro: $error")
            }
        })
        return(listaFazendas)
    }*/
    /*fun mostrarDados(callback: (ArrayList<String>) -> Unit) {
        val listaFazendas = ArrayList<String>()

        this.banco.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val gson = Gson()
                    Log.i("TESTE", "------")
                    for (i in snapshot.children) {
                        val json = gson.toJson(i.value)
                        val fazenda = gson.fromJson(json, Fazenda::class.java)
                        listaFazendas.add(fazenda.toString())
                        Log.i("TESTE", "Fazenda: " + fazenda.toString())
                    }
                    Log.i("TESTE", "------")
                    callback(listaFazendas)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i("Teste", "Erro: $error")
            }
        })
    }
    fun excluir(registro : String){
        this.banco.child(registro).removeValue()
    }*/
}