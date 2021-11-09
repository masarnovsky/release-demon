package by.masarnovsky.releasedemon.backend.external.service

interface UserLibraryRetriever {
    fun retrieve(username: String): List<String>
}