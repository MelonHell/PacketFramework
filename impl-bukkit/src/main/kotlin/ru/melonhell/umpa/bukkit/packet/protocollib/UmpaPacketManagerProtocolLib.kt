package ru.melonhell.umpa.bukkit.packet.protocollib

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.utility.MinecraftVersion
import com.github.matfax.klassindex.KlassIndex
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import ru.melonhell.umpa.bukkit.event.UmpaPacketEventBukkit
import ru.melonhell.umpa.bukkit.exceptions.UmpaConverterNotFoundException
import ru.melonhell.umpa.bukkit.packet.protocollib.converter.PacketConverter
import ru.melonhell.umpa.bukkit.utils.MinMaxMinecraftVersion
import ru.melonhell.umpa.bukkit.wrappers.UmpaPlayerBukkit
import ru.melonhell.umpa.core.enums.UmpaPacketType
import ru.melonhell.umpa.core.event.UmpaEventManager
import ru.melonhell.umpa.core.managers.UmpaPacketManager
import ru.melonhell.umpa.core.packet.containers.UmpaPacket
import ru.melonhell.umpa.core.wrappers.UmpaPlayer
import java.util.*

class UmpaPacketManagerProtocolLib(plugin: Plugin, private val eventManager: UmpaEventManager) : UmpaPacketManager, Listener {

    private val converterMapByWrapper: MutableMap<UmpaPacketType, PacketConverter> = EnumMap(UmpaPacketType::class.java)
    private val converterMapByProtocolLibType: MutableMap<PacketType, PacketConverter> = HashMap()

    init {
        val converterClasses = KlassIndex.getAnnotated(MinMaxMinecraftVersion::class)
        converterClasses.forEach { clazz ->
            if (PacketConverter::class.java.isAssignableFrom(clazz.java)) {
                val annotation = clazz.java.getAnnotation(MinMaxMinecraftVersion::class.java)
                val minVersion = MinecraftVersion(annotation.minVersion)
                val maxVersion = MinecraftVersion(annotation.maxVersion.replace("latest", "1.99"))
                if (MinecraftVersion.getCurrentVersion() in minVersion..maxVersion) {
                    val packetConverter = clazz.java.getConstructor().newInstance() as PacketConverter
                    converterMapByWrapper[packetConverter.packetType] = packetConverter
                    packetConverter.protocolLibTypes.forEach { type ->
                        converterMapByProtocolLibType[type] = packetConverter
                    }
                }
            }
        }

        ProtocolLibrary.getProtocolManager()
            .addPacketListener(object : PacketAdapter(plugin, PacketType.values().filter { it.isSupported }) {
                override fun onPacketReceiving(event: PacketEvent) =
                    onPacket(event)
                override fun onPacketSending(event: PacketEvent) =
                    onPacket(event)
            })
    }

    fun wrap(packetContainer: PacketContainer): UmpaPacket {
        val packetConverter = converterMapByProtocolLibType[packetContainer.type]
            ?: throw UmpaConverterNotFoundException(packetContainer.type)
        return packetConverter.wrap(packetContainer)
    }

    fun unwrap(packetWrapper: UmpaPacket): List<PacketContainer> {
        val packetConverter =
            converterMapByWrapper[packetWrapper.packetType] ?: throw UmpaConverterNotFoundException(packetWrapper)
        return packetConverter.unwrap(packetWrapper)
    }

    override fun send(
        player: UmpaPlayer, packetWrapper: UmpaPacket
    ) {
        if (player !is UmpaPlayerBukkit) return
        unwrap(packetWrapper).forEach {
            ProtocolLibrary.getProtocolManager().sendServerPacket(player.handle, it)
        }
    }

    private fun onPacket(protocolLibEvent: PacketEvent) {
        val packetConverter = converterMapByProtocolLibType[protocolLibEvent.packetType] ?: return
        val packetEvent = UmpaPacketEventBukkit(
            protocolLibEvent.player, { packetConverter.wrap(protocolLibEvent.packet) }, packetConverter.packetType
        )
        eventManager.call(packetEvent)
        if (packetEvent.canceled) {
            protocolLibEvent.isCancelled = true
            return
        }
        if (packetEvent.edited) {
            val packetWrapper = packetEvent.packetWrapper
            if (packetWrapper.packetType.direction != packetConverter.packetType.direction)
                throw RuntimeException("ты чё долбоёб блять, ты сука клиентский пакет серверу хуяришь или наоборот я хуй знает что ты там задумал, мудоёб ебливый")
            val unwrap = unwrap(packetWrapper)
            if (unwrap.size == 1) {
                protocolLibEvent.packet = unwrap[0]
            } else {
                // Костыль для всякой хуйни по типу ремува энтити в 1.17.0
                protocolLibEvent.isCancelled = true
                unwrap.forEach { ProtocolLibrary.getProtocolManager().sendServerPacket(protocolLibEvent.player, it) }
            }
        }
    }
}