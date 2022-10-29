package ru.melonhell.umpa.bukkit.converter.clientbound.playerAbilities

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketContainer
import ru.melonhell.umpa.bukkit.converter.PacketConverter
import ru.melonhell.umpa.bukkit.converter.ProtocolVersion
import ru.melonhell.umpa.bukkit.exceptions.WrongConverterException
import ru.melonhell.umpa.core.packet.containers.UmpaPacketContainer
import ru.melonhell.umpa.core.packet.containers.clientbound.UmpaCbPlayerAbilitiesPacket

@ProtocolVersion("1.8", "latest")
class PlayerAbilitiesPacketConverter_v1_8_0 : PacketConverter {
    override fun wrap(container: PacketContainer): UmpaCbPlayerAbilitiesPacket {
        val invulnerable = container.booleans.read(0)
        val isFlying = container.booleans.read(1)
        val canFly = container.booleans.read(2)
        val instabuild = container.booleans.read(3)
        val flyingSpeed = container.float.read(0)
        val walkingSpeed = container.float.read(1)
        return UmpaCbPlayerAbilitiesPacket(invulnerable, isFlying, canFly, instabuild, flyingSpeed, walkingSpeed)
    }

    override fun unwrap(wrapper: UmpaPacketContainer): List<PacketContainer> {
        if (wrapper !is UmpaCbPlayerAbilitiesPacket) throw WrongConverterException(wrapper, this)
        val container = PacketContainer(PacketType.Play.Server.ABILITIES)
        container.booleans.write(0, wrapper.invulnerable)
        container.booleans.write(1, wrapper.isFlying)
        container.booleans.write(2, wrapper.canFly)
        container.booleans.write(3, wrapper.instabuild)
        container.float.write(0, wrapper.flyingSpeed)
        container.float.write(1, wrapper.fov)
        return listOf(container)
    }

    override val protocolLibTypes = listOf(PacketType.Play.Server.ABILITIES)
    override val wrapperType = UmpaCbPlayerAbilitiesPacket::class
}