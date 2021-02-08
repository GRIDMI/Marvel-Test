package com.gridmi.marvel.ui.fragment.master

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gridmi.marvel.enteties.*
import com.gridmi.marvel.tools.API
import io.realm.Realm
import io.realm.Sort

class MasterViewModel : ViewModel() {

    private val realm:Realm = Realm.getDefaultInstance()

    val items: MutableLiveData<List<CharacterEntity>> by lazy {
        MutableLiveData<List<CharacterEntity>>().also {
            API.characters({ list ->
                try {

                    realm.beginTransaction()

                    val i = CharacterEntity.fromDtoList(list)

                    realm.where(CharacterComicEntity::class.java).findAll().deleteAllFromRealm()
                    realm.where(CharacterStoryEntity::class.java).findAll().deleteAllFromRealm()
                    realm.where(CharacterEventEntity::class.java).findAll().deleteAllFromRealm()
                    realm.where(CharacterSeriesEntity::class.java).findAll().deleteAllFromRealm()
                    list.forEach {
                        realm.insertOrUpdate(CharacterComicEntity.from(it))
                        realm.insertOrUpdate(CharacterStoryEntity.from(it))
                        realm.insertOrUpdate(CharacterEventEntity.from(it))
                        realm.insertOrUpdate(CharacterSeriesEntity.from(it))
                    }

                    realm.insertOrUpdate(i)
                    realm.commitTransaction()

                    getMoreItems()

                } catch (ignored:Throwable) {
                }
            })
        }
    }

    fun getMoreItems(limit:Long = 4) {

        val i = items.value?.toMutableList() ?: emptyList()

        val minId:Int = when(i.isNotEmpty()) {
            true -> i[i.size - 1].id!!
            false -> 0
        }

        val r = realm
                .where(CharacterEntity::class.java)
                .greaterThan("id", minId)
                .sort("id", Sort.ASCENDING)
                .limit(limit).findAll()

        items.value = (i + realm.copyFromRealm(r)).sortedBy {
            it.id
        }

    }

    override fun onCleared() {
        realm.close()
        super.onCleared()
    }

}

