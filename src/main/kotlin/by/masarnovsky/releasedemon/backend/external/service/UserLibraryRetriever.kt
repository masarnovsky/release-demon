package by.masarnovsky.releasedemon.backend.external.service

interface UserLibraryRetriever {
    fun retrieve(identifier: String): List<String>
}