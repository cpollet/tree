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

import java.util.HashMap;
import java.util.Map;

public class AttrsVisitor extends TreeBaseVisitor<Map<String, String>> {
    @Override
    public Map<String, String> visitAttrs(TreeParser.AttrsContext ctx) {
        Map<String, String> map = new HashMap<>();

        map.put(
                new EscapedText(ctx.attr().key.getText()).unescape(),
                new EscapedText(ctx.attr().value.getText()).unescape()
        );

        if (ctx.attrs() != null) {
            map.putAll(ctx.attrs().accept(this));
        }

        return map;
    }
}
