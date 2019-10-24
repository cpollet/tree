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
package net.cpollet.tree;

import com.beust.jcommander.JCommander;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.cpollet.tree.ast.AttrsVisitor;
import net.cpollet.tree.ast.Node;
import net.cpollet.tree.ast.NodeVisitor;
import net.cpollet.tree.output.json.JsonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    private final Parameters parameters;

    public Main(Parameters parameters) {
        this.parameters = parameters;
    }

    public static void main(String[] args) throws IOException {
        Parameters parameters = new Parameters();

        JCommander jcommander = JCommander.newBuilder()
                .addObject(parameters)
                .build();

        jcommander.parse(args);

        if (parameters.help()) {
            Properties props = new Properties();
            props.load(Main.class.getResourceAsStream("/maven/build.properties"));
            jcommander.setProgramName(String.format("java -jar %s", props.getProperty("build.name")));
            System.out.println(String.format("%s version %s (%s)",
                    props.getProperty("build.name"),
                    props.getProperty("build.version"),
                    props.getProperty("build.date")
            ));
            jcommander.usage();
            System.exit(0);
        }

        new Main(parameters).run();
    }

    private InputStream inputStream(String filename) {
        try {
            return new FileInputStream(filename);
        }
        catch (FileNotFoundException e) {
            throw new Error("Cannot open " + filename);
        }
    }

    private void run() throws IOException {
        Node node = new NodeVisitor(new AttrsVisitor()).visit(
                new TreeParser(
                        new CommonTokenStream(
                                new TreeLexer(
                                        CharStreams.fromStream(
                                                parameters.filename()
                                                        .map(this::inputStream)
                                                        .orElse(System.in)

                                        )
                                )
                        )
                ).node()
        );

        System.out.println(
                new GsonBuilder().setPrettyPrinting().create().toJson(
                        JsonParser.parseString(
                                new JsonVisitor().visit(node)
                        )
                )
        );
    }
}
