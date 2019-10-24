/*
 * Copyright 2019 Christophe Pollet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.cpollet.tree.output.json;

import com.google.common.collect.ImmutableMap;
import net.cpollet.tree.ast.Node;
import net.cpollet.tree.ast.Visitor;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.util.Map;
import java.util.stream.Collectors;

import static org.jtwig.JtwigTemplate.classpathTemplate;

public class JsonVisitor implements Visitor<String> {
    private static final String TEMPLATE_PATH = JsonVisitor.class.getPackage().getName()
            .replace(".", File.separator) + File.separator;

    private static final Map<Class<?>, JtwigTemplate> templates = ImmutableMap.<Class<?>, JtwigTemplate>builder()
            .put(Node.class, classpathTemplate(TEMPLATE_PATH + "node.twig"))
            .build();

    @Override
    public String visit(Node node) {
        return templates.get(node.getClass()).render(
                JtwigModel.newModel()
                        .with("name", node.getName())
                        .with("type", node.getType())
                        .with("attributes", node.getAttributes().entrySet())
                        .with("children", node.getChildren().stream()
                                .map(n -> n.accept(this))
                                .collect(Collectors.toList()))
        );
    }
}
