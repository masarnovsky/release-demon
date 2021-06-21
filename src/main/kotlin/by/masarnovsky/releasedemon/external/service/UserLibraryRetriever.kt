package by.masarnovsky.releasedemon.external.service

interface UserLibraryRetriever {
    fun retrieve(username: String): List<String>
}