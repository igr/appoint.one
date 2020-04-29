package domain.timeslot

import kotlinx.serialization.*

object TimeslotStatusSerializer : KSerializer<TimeslotStatus> {
	override val descriptor: SerialDescriptor =
		PrimitiveDescriptor(this::class.simpleName!!, PrimitiveKind.INT)

	override fun serialize(encoder: Encoder, value: TimeslotStatus) {
		encoder.encodeInt(value.value)
	}

	override fun deserialize(decoder: Decoder): TimeslotStatus = TimeslotStatus.of(decoder.decodeInt())
}
