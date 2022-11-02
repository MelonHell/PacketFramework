package ru.melonhell.umpa.core.data.entitymeta.meta.mob.monster.raider

import ru.melonhell.umpa.core.data.entitymeta.meta.abstr.UmpaSpellcasterIllagerMetadata

open class UmpaEvokerMetadata : UmpaSpellcasterIllagerMetadata() {
		
	override val isEmpty: Boolean
		get() = this == empty

	override fun clone(): UmpaEvokerMetadata {
		return super.clone() as UmpaEvokerMetadata
	}

	companion object {
		private val empty = UmpaEvokerMetadata()
	}
}
