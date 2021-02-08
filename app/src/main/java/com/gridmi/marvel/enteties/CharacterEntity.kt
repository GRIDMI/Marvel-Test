package com.gridmi.marvel.enteties

import com.gridmi.marvel.dto.CharacterDto
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class CharacterEntity : RealmObject() {

    @PrimaryKey
    var id:Int? = null

    var avatar:String? = null
    var name:String? = null
    var description:String? = null

    companion object {
        fun fromDtoList(list:List<CharacterDto>) : List<CharacterEntity> {
            return list.map { fromDto(it) }
        }
        private fun fromDto(from:CharacterDto) : CharacterEntity {
            val to = CharacterEntity()
            to.id = from.id
            to.avatar = "${from.thumbnail?.path}.${from.thumbnail?.extension}"
            to.name = from.name
            to.description = from.description
            return to
        }
    }

    override fun toString(): String {
        return "CharacterEntity(id=$id, avatar=$avatar, name=$name, description=$description)"
    }

}

open class CharacterComicEntity : RealmObject() {
    var characterId:Int? = null
    var name:String? = null
    companion object {
        fun from(character:CharacterDto) : List<CharacterComicEntity> {
            return character.comics!!.items!!.map {
                CharacterComicEntity().apply {
                    characterId = character.id
                    name = it!!.name
                }
            }
        }
    }
}

open class CharacterStoryEntity : RealmObject() {
    var characterId:Int? = null
    var name:String? = null
    companion object {
        fun from(character:CharacterDto) : List<CharacterStoryEntity> {
            return character.stories!!.items!!.map {
                CharacterStoryEntity().apply {
                    characterId = character.id
                    name = it!!.name
                }
            }
        }
    }
}

open class CharacterEventEntity : RealmObject() {
    var characterId:Int? = null
    var name:String? = null
    companion object {
        fun from(character:CharacterDto) : List<CharacterEventEntity> {
            return character.comics!!.items!!.map {
                CharacterEventEntity().apply {
                    characterId = character.id
                    name = it!!.name
                }
            }
        }
    }
}

open class CharacterSeriesEntity : RealmObject() {
    var characterId:Int? = null
    var name:String? = null
    companion object {
        fun from(character:CharacterDto) : List<CharacterSeriesEntity> {
            return character.comics!!.items!!.map {
                CharacterSeriesEntity().apply {
                    characterId = character.id
                    name = it!!.name
                }
            }
        }
    }
}