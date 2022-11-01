package ru.melonhell.umpa.core.enums.keyed

import net.kyori.adventure.key.Key
import ru.melonhell.umpa.core.data.entitymeta.UmpaArmorStandMetadata
import ru.melonhell.umpa.core.data.entitymeta.UmpaEntityMetadata
import ru.melonhell.umpa.core.data.entitymeta.UmpaSlimeMetadata
import java.util.*
import java.util.stream.Collectors
import kotlin.reflect.KClass

enum class UmpaEntityType(
    private val stringKey: String,
    private val metaClass: KClass<out UmpaEntityMetadata> = UmpaEntityMetadata::class
    ) : UmpaKeyed {
    ALLAY("minecraft:allay"),
    AREA_EFFECT_CLOUD("minecraft:area_effect_cloud"),
    ARMOR_STAND("minecraft:armor_stand", UmpaArmorStandMetadata::class),
    ARROW("minecraft:arrow"),
    AXOLOTL("minecraft:axolotl"),
    BAT("minecraft:bat"),
    BEE("minecraft:bee"),
    BLAZE("minecraft:blaze"),
    BOAT("minecraft:boat"),
    CHEST_BOAT("minecraft:chest_boat"),
    CAT("minecraft:cat"),
    CAVE_SPIDER("minecraft:cave_spider"),
    CHICKEN("minecraft:chicken"),
    COD("minecraft:cod"),
    COW("minecraft:cow"),
    CREEPER("minecraft:creeper"),
    DOLPHIN("minecraft:dolphin"),
    DONKEY("minecraft:donkey"),
    DRAGON_FIREBALL("minecraft:dragon_fireball"),
    DROWNED("minecraft:drowned"),
    ELDER_GUARDIAN("minecraft:elder_guardian"),
    END_CRYSTAL("minecraft:end_crystal"),
    ENDER_DRAGON("minecraft:ender_dragon"),
    ENDERMAN("minecraft:enderman"),
    ENDERMITE("minecraft:endermite"),
    EVOKER("minecraft:evoker"),
    EVOKER_FANGS("minecraft:evoker_fangs"),
    EXPERIENCE_ORB("minecraft:experience_orb"),
    EYE_OF_ENDER("minecraft:eye_of_ender"),
    FALLING_BLOCK("minecraft:falling_block", UmpaArmorStandMetadata::class),
    FIREWORK_ROCKET("minecraft:firework_rocket"),
    FOX("minecraft:fox"),
    FROG("minecraft:frog"),
    GHAST("minecraft:ghast"),
    GIANT("minecraft:giant"),
    GLOW_ITEM_FRAME("minecraft:glow_item_frame"),
    GLOW_SQUID("minecraft:glow_squid"),
    GOAT("minecraft:goat"),
    GUARDIAN("minecraft:guardian"),
    HOGLIN("minecraft:hoglin"),
    HORSE("minecraft:horse"),
    HUSK("minecraft:husk"),
    ILLUSIONER("minecraft:illusioner"),
    IRON_GOLEM("minecraft:iron_golem"),
    ITEM("minecraft:item"),
    ITEM_FRAME("minecraft:item_frame"),
    FIREBALL("minecraft:fireball"),
    LEASH_KNOT("minecraft:leash_knot"),
    LIGHTNING_BOLT("minecraft:lightning_bolt"),
    LLAMA("minecraft:llama"),
    LLAMA_SPIT("minecraft:llama_spit"),
    MAGMA_CUBE("minecraft:magma_cube"),
    MARKER("minecraft:marker"),
    MINECART("minecraft:minecart"),
    CHEST_MINECART("minecraft:chest_minecart"),
    COMMAND_BLOCK_MINECART("minecraft:command_block_minecart"),
    FURNACE_MINECART("minecraft:furnace_minecart"),
    HOPPER_MINECART("minecraft:hopper_minecart"),
    SPAWNER_MINECART("minecraft:spawner_minecart"),
    TNT_MINECART("minecraft:tnt_minecart"),
    MULE("minecraft:mule"),
    MOOSHROOM("minecraft:mooshroom"),
    OCELOT("minecraft:ocelot"),
    PAINTING("minecraft:painting"),
    PANDA("minecraft:panda"),
    PARROT("minecraft:parrot"),
    PHANTOM("minecraft:phantom"),
    PIG("minecraft:pig"),
    PIGLIN("minecraft:piglin"),
    PIGLIN_BRUTE("minecraft:piglin_brute"),
    PILLAGER("minecraft:pillager"),
    POLAR_BEAR("minecraft:polar_bear"),
    TNT("minecraft:tnt"),
    PUFFERFISH("minecraft:pufferfish"),
    RABBIT("minecraft:rabbit"),
    RAVAGER("minecraft:ravager"),
    SALMON("minecraft:salmon"),
    SHEEP("minecraft:sheep"),
    SHULKER("minecraft:shulker"),
    SHULKER_BULLET("minecraft:shulker_bullet"),
    SILVERFISH("minecraft:silverfish"),
    SKELETON("minecraft:skeleton"),
    SKELETON_HORSE("minecraft:skeleton_horse"),
    SLIME("minecraft:slime", UmpaSlimeMetadata::class),
    SMALL_FIREBALL("minecraft:small_fireball"),
    SNOW_GOLEM("minecraft:snow_golem"),
    SNOWBALL("minecraft:snowball"),
    SPECTRAL_ARROW("minecraft:spectral_arrow"),
    SPIDER("minecraft:spider"),
    SQUID("minecraft:squid"),
    STRAY("minecraft:stray"),
    STRIDER("minecraft:strider"),
    TADPOLE("minecraft:tadpole"),
    EGG("minecraft:egg"),
    ENDER_PEARL("minecraft:ender_pearl"),
    EXPERIENCE_BOTTLE("minecraft:experience_bottle"),
    POTION("minecraft:potion"),
    TRIDENT("minecraft:trident"),
    TRADER_LLAMA("minecraft:trader_llama"),
    TROPICAL_FISH("minecraft:tropical_fish"),
    TURTLE("minecraft:turtle"),
    VEX("minecraft:vex"),
    VILLAGER("minecraft:villager"),
    VINDICATOR("minecraft:vindicator"),
    WANDERING_TRADER("minecraft:wandering_trader"),
    WARDEN("minecraft:warden"),
    WITCH("minecraft:witch"),
    WITHER("minecraft:wither"),
    WITHER_SKELETON("minecraft:wither_skeleton"),
    WITHER_SKULL("minecraft:wither_skull"),
    WOLF("minecraft:wolf"),
    ZOGLIN("minecraft:zoglin"),
    ZOMBIE("minecraft:zombie"),
    ZOMBIE_HORSE("minecraft:zombie_horse"),
    ZOMBIE_VILLAGER("minecraft:zombie_villager"),
    ZOMBIFIED_PIGLIN("minecraft:zombified_piglin"),
    PLAYER("minecraft:player"),
    FISHING_BOBBER("minecraft:fishing_bobber");

    override val key: Key = Key.key(stringKey, ':')

    companion object : UmpaKeyed.Companion<UmpaEntityType> {
        private val keyMap = Arrays.stream(UmpaEntityType.values())
            .collect(Collectors.toMap({ it.stringKey }, { it }))

        @JvmStatic
        override fun fromKey(key: String) = keyMap[key]

        @JvmStatic
        override fun fromKey(key: Key) = fromKey(key.asString())
    }
}