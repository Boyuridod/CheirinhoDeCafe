package br.com.yuriduarte.cheirinhodecafe

class Cafe(id : String, nome : String, nota : EnumNota, aroma : Int, acidez : Int, amargor : Int, sabor : Int, preco : Double) {

    var id : String
    var nome : String
    var nota : EnumNota
    var aroma : Int
    var acidez : Int
    var amargor : Int
    var sabor : Int
    var preco : Double

    init {
        this.id = id
        this.nome = nome
        this.nota = nota
        this.aroma = aroma
        this.acidez = acidez
        this.amargor = amargor
        this.sabor = sabor
        this.preco = preco
    }

    override fun toString(): String {
        return ("[" + id + "] Nome: " + nome + " nota de: " + nota.toString() + " aroma: " + aroma.toString() + " acidez: " +
                acidez.toString() + " amargor: " + amargor.toString() + " sabor: " + sabor.toString() + " preço: " + preco.toString())
    }

}