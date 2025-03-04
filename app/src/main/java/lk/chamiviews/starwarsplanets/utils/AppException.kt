package lk.chamiviews.starwarsplanets.utils

class NoNetworkException(message: String = "No network connection available") : Exception(message)

class RemoteDataSourceException(message: String) : Exception(message)

class NoMorePagesException(message: String = "No more pages available") : Exception(message)