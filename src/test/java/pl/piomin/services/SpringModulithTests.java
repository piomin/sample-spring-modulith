package pl.piomin.services;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

public class SpringModulithTests {

    ApplicationModules modules = ApplicationModules.of(SpringModulith.class);

    @Test
    void shouldBeCompliant() {
        modules.verify();
    }

    @Test
    void writeDocumentationSnippets() {
        new Documenter(modules)
                .writeModuleCanvases()
                .writeModulesAsPlantUml()
                .writeDocumentation()
                .writeAggregatingDocument()
                .writeIndividualModulesAsPlantUml();
    }
}
