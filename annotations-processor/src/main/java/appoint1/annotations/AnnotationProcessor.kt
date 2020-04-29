package appoint1.annotations

import java.io.File
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.annotation.processing.SupportedSourceVersion
import javax.lang.model.SourceVersion
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.tools.Diagnostic

@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedOptions(AnnotationProcessor.KAPT_KOTLIN_GENERATED_OPTION_NAME)
class AnnotationProcessor : AbstractProcessor() {
	companion object {
		const val KAPT_KOTLIN_GENERATED_OPTION_NAME = "kapt.kotlin.generated"
	}

	override fun getSupportedAnnotationTypes(): Set<String> =
		setOf(IdGen::class.java.canonicalName)

	/**
	 * Process the annotations.
	 */
	override fun process(annotations: Set<TypeElement>, roundEnv: RoundEnvironment): Boolean {
		val kaptKotlinGeneratedDir = processingEnv.options[KAPT_KOTLIN_GENERATED_OPTION_NAME] ?: run {
			processingEnv.messager.printMessage(
				Diagnostic.Kind.ERROR,
				"Can't find the target directory for generated Kotlin files."
			)
			return false
		}

		var count = 0

		roundEnv
			.getElementsAnnotatedWith(IdGen::class.java)
			.asSequence()
			.filter { it.kind == ElementKind.METHOD }
			.map {
				it.simpleName
					.toString()
					.substringBefore('$')
					.removePrefix("_")
					.capitalize()
			}
			.onEach { count++ }
			.map { IdGenerator.generateFile(it) }
			.forEach {
				println(">>> ${it.name}")
				//it.writeTo(System.out)
				it.writeTo(File(kaptKotlinGeneratedDir, "${it.name}.kt").apply {
					parentFile.mkdirs()
				})
			}

		return count > 0
	}

}

