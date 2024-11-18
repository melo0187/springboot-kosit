package info.melo.springkosit

import de.kosit.validationtool.api.Check
import de.kosit.validationtool.impl.DefaultCheck
import de.kosit.validationtool.impl.ResolvingMode
import de.kosit.validationtool.impl.xml.BaseResolvingStrategy
import de.kosit.validationtool.impl.xml.ProcessorProvider
import de.kosit.validationtool.impl.xml.RelativeUriResolver
import net.sf.saxon.lib.UnparsedTextURIResolver
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.net.URI
import javax.xml.XMLConstants
import javax.xml.transform.URIResolver
import javax.xml.validation.Schema
import javax.xml.validation.SchemaFactory
import javax.xml.validation.Validator
import de.kosit.validationtool.api.Configuration as KositConfiguration

@SpringBootApplication
class SpringkositApplication

fun main(args: Array<String>) {
    runApplication<SpringkositApplication>(*args)
}

@Configuration
class ValidatorConfiguration {
    @Bean
    fun kositValidationCheck(): Check {
        val scenariosUri: URI =
            ClassPathResource("validator-configuration-xrechnung_3.0.2_2024-06-20/scenarios.xml").uri
        val checkConfiguration =
            KositConfiguration
                .load(scenariosUri)
                .build(ProcessorProvider.getProcessor())
        return DefaultCheck(checkConfiguration)
    }
}
