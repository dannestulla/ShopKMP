package br.gohan.shopsample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform