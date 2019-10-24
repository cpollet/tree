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
package net.cpollet.tree.ast;

import net.cpollet.tree.TreeBaseVisitor;
import net.cpollet.tree.TreeParser;

import java.util.Collections;
import java.util.stream.Collectors;

public class NodeVisitor extends TreeBaseVisitor<Node> {
    private final AttrsVisitor attrsVisitor;

    public NodeVisitor(AttrsVisitor attrsVisitor) {
        this.attrsVisitor = attrsVisitor;
    }

    @Override
    public Node visitNode(TreeParser.NodeContext ctx) {
        return new Node(
                ctx.nodeName.getText().replace("\\ ", " "),
                ctx.nodeType.getText().replace("\\ ", " "),
                ctx.attrs() == null ?
                        Collections.emptyMap() :
                        ctx.attrs().accept(attrsVisitor),
                ctx.node().stream()
                        .map(n -> n.accept(this))
                        .collect(Collectors.toList())
        );
    }
}
