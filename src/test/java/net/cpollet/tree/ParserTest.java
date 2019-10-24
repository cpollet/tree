package net.cpollet.tree;/*
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

import net.cpollet.tree.ast.AttrsVisitor;
import net.cpollet.tree.ast.NodeVisitor;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class ParserTest {
    @Test
    void test() throws IOException {
        Assertions.assertThat(
                new NodeVisitor(new AttrsVisitor()).visit(
                        new TreeParser(
                                new CommonTokenStream(
                                        new TreeLexer(
                                                CharStreams.fromStream(
                                                        Main.class.getResourceAsStream("/example.tree")
                                                )
                                        )
                                )
                        ).node()
                ).toString()
        ).isEqualTo("(/, folder, user=root (usr, folder (local, folder (bin, folder (cmake, file, executable=true))))" +
                "(etc, folder (hosts, file, executable=false)))");
    }

    @Test
    void test_withSpaces() throws IOException {
        Assertions.assertThat(
                new NodeVisitor(new AttrsVisitor()).visit(
                        new TreeParser(
                                new CommonTokenStream(
                                        new TreeLexer(
                                                CharStreams.fromStream(
                                                        Main.class.getResourceAsStream("/spaces.tree")
                                                )
                                        )
                                )
                        ).node()
                ).toString()
        ).isEqualTo("(node\\ a, type\\ a, attr\\ a=val\\ a)");
    }

    @Test
    void test_withSymbols() throws IOException {
        Assertions.assertThat(
                new NodeVisitor(new AttrsVisitor()).visit(
                        new TreeParser(
                                new CommonTokenStream(
                                        new TreeLexer(
                                                CharStreams.fromStream(
                                                        Main.class.getResourceAsStream("/symbols.tree")
                                                )
                                        )
                                )
                        ).node()
                ).toString()
        ).isEqualTo("(a\\,\\(\\)\\=b, c#d, e\"=f')");
    }
}
