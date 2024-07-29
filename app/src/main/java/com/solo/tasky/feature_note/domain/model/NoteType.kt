package com.solo.tasky.feature_note.domain.model

class NoteType {

    companion object {
        const val ALL = "ALL"
        const val STUDY = "STUDY"
        const val WORK = "WORK"
        const val INSPIRATION = "INSPIRATION"
        const val GOALS = "GOALS"
        const val BOOKS = "BOOKS"
    }

}


val noteTypes = listOf(
    NoteType.ALL,
    NoteType.STUDY,
    NoteType.WORK,
    NoteType.INSPIRATION,
    NoteType.GOALS,
    NoteType.BOOKS
)
