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

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.cpollet.tree.ast.AttrsVisitor;
import net.cpollet.tree.ast.Node;
import net.cpollet.tree.ast.NodeVisitor;
import net.cpollet.tree.output.json.JsonVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length != 1) {
            Properties props = new Properties();
            props.load(Main.class.getResourceAsStream("/maven/build.properties"));
            System.out.println(String.format("%s version %s (%s)%nUsage: java -jar %s.jar FILE",
                    props.getProperty("build.name"),
                    props.getProperty("build.version"),
                    props.getProperty("build.date"),
                    props.getProperty("build.name")
            ));
            System.exit(0);
        }

        Node node = new NodeVisitor(new AttrsVisitor()).visit(
                new TreeParser(
                        new CommonTokenStream(
                                new TreeLexer(
                                        CharStreams.fromStream(
                                                new FileInputStream(args[0])
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
