package by.masarnovsky.releasedemon.backend.event

data class LastfmUsernameSavedEvent(val chatId: Long)

data class NewArtistsEvent(val artistsId: List<Long>)