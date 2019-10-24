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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Node {
    private final String name;
    private final String type;
    private final Map<String, String> attributes;
    private final List<Node> children;

    public Node(String name, String type, Map<String, String> attributes, List<Node> children) {
        this.name = name;
        this.type = type;
        this.attributes = attributes;
        this.children = children;
    }

    public <T> T accept(Visitor<? extends T> visitor) {
        return visitor.visit(this);
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public List<Node> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s%s%s)",
                new EscapedText(name).escape(),
                new EscapedText(type).escape(),
                attributes.isEmpty() ?
                        "" :
                        ", " + attributes.entrySet().stream()
                                .map(e -> String.format("%s=%s",
                                        new EscapedText(e.getKey()).escape(),
                                        new EscapedText(e.getValue()).escape()
                                ))
                                .collect(Collectors.joining(", ")),
                children.isEmpty() ?
                        "" :
                        " " + children.stream()
                                .map(Node::toString)
                                .collect(Collectors.joining(""))
        );
    }
}
