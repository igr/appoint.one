package domain.patient

import kotlinx.serialization.*

object PatientSexSerializer : KSerializer<PatientSex> {
	override val descriptor: SerialDescriptor =
		PrimitiveDescriptor(this::class.simpleName!!, PrimitiveKind.INT)

	override fun serialize(encoder: Encoder, value: PatientSex) {
		encoder.encodeInt(value.value)
	}

	override fun deserialize(decoder: Decoder): PatientSex = PatientSex.of(decoder.decodeInt())
}

