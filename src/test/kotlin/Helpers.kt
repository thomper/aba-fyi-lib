val ALL_ASCII_CHARS = CharArray(127) { (it).toChar() }.joinToString("")
val ALL_ASCII_CHAR_PAIRS = ALL_ASCII_CHARS.flatMap { first -> ALL_ASCII_CHARS.map { second -> "$first$second" } }
