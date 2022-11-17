package com.example.weatherapp.data.hardware

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object HardwareSerializer : Serializer<HardwareMap> {
    override val defaultValue: HardwareMap
        get() = HardwareMap.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): HardwareMap {
        try {
            return HardwareMap.parseFrom(input)
        } catch (ex: InvalidProtocolBufferException){
            throw CorruptionException("Cannot read proto.", ex)
        }
    }

    override suspend fun writeTo(t: HardwareMap, output: OutputStream) =
        t.writeTo(output)
}

fun toSaved(entity: HardwareEntity) =
    SavedHardware.newBuilder()
        .setAddress(entity.ipAddress)
        .setName(entity.nickname)
     //   .setLastSuccess(entity.lastSuccess)
        .build()

fun fromSaved(saved: SavedHardware): HardwareEntity {
    val addr = saved.address ?: throw CorruptionException("Undefined address.")
    val name = saved.name ?: throw CorruptionException("Undefined name.")
   // val lastSuccess = saved.lastSuccess
    return HardwareEntity(ipAddress = addr, nickname = name)
}

fun fromSavedMap(savedMap: HardwareMap): Map<String, HardwareEntity> {
    val m: Map<String, SavedHardware> = savedMap.hwsMap
    return m.mapValues { fromSaved(it.value) }
}