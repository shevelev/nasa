package ru.crashdev.nasa.utils

import java.io.IOException

class ApiException(error: String) : IOException(error)
class NoInternetException(error: String) : IOException(error)