package com.gridmi.marvel.ui.fragment.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gridmi.marvel.enteties.*
import io.realm.Realm

class DetailViewModel : ViewModel() {

    private val realm = Realm.getDefaultInstance()

    val item = MutableLiveData<CharacterEntity>()

    fun getComics() : List<CharacterComicEntity> {
        return realm.where(CharacterComicEntity::class.java).equalTo("characterId", item.value!!.id).findAll()
    }

    fun getStories() : List<CharacterStoryEntity> {
        return realm.where(CharacterStoryEntity::class.java).equalTo("characterId", item.value!!.id).findAll()
    }

    fun getEvents() : List<CharacterEventEntity> {
        return realm.where(CharacterEventEntity::class.java).equalTo("characterId", item.value!!.id).findAll()
    }

    fun getSeries() : List<CharacterSeriesEntity> {
        return realm.where(CharacterSeriesEntity::class.java).equalTo("characterId", item.value!!.id).findAll()
    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

}