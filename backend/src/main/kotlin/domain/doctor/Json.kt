package domain.doctor

import kotlinx.serialization.*

object DoctorCertificateSerializer : KSerializer<DoctorCertificate> {
	override val descriptor: SerialDescriptor =
		PrimitiveDescriptor(this::class.simpleName!!, PrimitiveKind.INT)

	override fun serialize(encoder: Encoder, value: DoctorCertificate) {
		encoder.encodeInt(value.value)
	}

	override fun deserialize(decoder: Decoder): DoctorCertificate = DoctorCertificate.of(decoder.decodeInt())
}
