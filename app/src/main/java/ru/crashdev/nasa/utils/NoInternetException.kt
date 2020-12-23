package ru.crashdev.nasa.utils

import java.io.IOException

class NoInternetException(error: String) : IOException(error) {

}
