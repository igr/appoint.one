package appoint1.annotations

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import kotlinx.serialization.*
import org.jetbrains.exposed.dao.id.EntityID

internal object IdGenerator {

	fun generateFile(className: String): FileSpec {

		val serializerClassName = "${className}Serializer"
		val typeName = ClassName("id", className)
		val file = FileSpec.builder("id", "${className}Generated")

		file.addType(
			TypeSpec.classBuilder(className)
				.addAnnotation(AnnotationSpec.builder(Serializable::class)
					.addMember("with = %T::class", ClassName("id", serializerClassName))
					.build()
				)
				.addModifiers(KModifier.DATA)
				.primaryConstructor(FunSpec.constructorBuilder()
					.addParameter("value", Int::class)
					.build())
				.addProperty(PropertySpec.builder("value", Int::class)
					.initializer("value")
					.build())
				.addFunction(toStringFunction())
				.build()
		)

		file.addType(
			TypeSpec.objectBuilder(serializerClassName)
				.addSuperinterface(
					KSerializer::class.asClassName().parameterizedBy(typeName))
				.addAnnotation(AnnotationSpec.builder(Serializer::class)
					.addMember("forClass = %T::class", typeName)
					.build()
				)
				.addProperty(descriptorProperty("${className}Serializer"))
				.addFunction(serializeFunction(typeName))
				.addFunction(deserializeFunction(typeName))
				.build()
		)

		file.addFunction(intExtension(className, typeName))
		file.addFunction(stringExtension(className, typeName))
		file.addFunction(entityExtension(className, typeName))


		return file.build()
	}

//	private fun createConstructorCall(type: TypeName, vararg args: String): CodeBlock {
//		val argsCode = args
//			.map { CodeBlock.of("%L", it) }
//			.joinToCode(separator = ", ", prefix = "(", suffix = ")")
//
//		return CodeBlock.Builder()
//			.add("%T", type)
//			.add(argsCode)
//			.build()
//	}

	private fun descriptorProperty(serializerClassName: String): PropertySpec {
		val primitiveDescriptor = MemberName("kotlinx.serialization", "PrimitiveDescriptor")

		return PropertySpec.builder("descriptor", SerialDescriptor::class)
			.addModifiers(KModifier.OVERRIDE)
			.initializer(CodeBlock.Builder()
				.add("%M(\"${serializerClassName}\", %T)", primitiveDescriptor, PrimitiveKind.INT::class.asTypeName())
				.build()
			)
			.build()
	}

	private fun toStringFunction(): FunSpec {
		return FunSpec.builder("toString")
			.addModifiers(KModifier.OVERRIDE)
			.addStatement("return value.toString()")
			.build()
	}

	private fun serializeFunction(typeName: TypeName): FunSpec {
		return FunSpec.builder("serialize")
			.addModifiers(KModifier.OVERRIDE)
			.addParameter("encoder", Encoder::class)
			.addParameter("value", typeName)
			.addStatement("encoder.encodeInt(value.value)")
			.build()
	}

	private fun deserializeFunction(typeName: TypeName): FunSpec {
		return FunSpec.builder("deserialize")
			.addModifiers(KModifier.OVERRIDE)
			.returns(typeName)
			.addParameter("decoder", Decoder::class)
			.addStatement("return ${typeName}(decoder.decodeInt())")
			.build()
	}

	private fun intExtension(name: String, typeName: TypeName): FunSpec {
		return FunSpec.builder("to${name}")
			.receiver(Int::class)
			.returns(typeName)
			.addStatement("return ${name}(this)")
			.build()
	}

	private fun stringExtension(name: String, typeName: TypeName): FunSpec {
		return FunSpec.builder("to${name}")
			.receiver(String::class)
			.returns(typeName)
			.addStatement("return ${name}(this.toInt())")
			.build()
	}

	private fun entityExtension(name: String, typeName: TypeName): FunSpec {
		return FunSpec.builder("to${name}")
			.receiver(EntityID::class.parameterizedBy(Int::class))
			.returns(typeName)
			.addStatement("return ${name}(this.value)")
			.build()
	}

}
