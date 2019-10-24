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

import com.beust.jcommander.Parameter;

import java.util.Optional;

public class Parameters {
    @Parameter(names = "-h", description = "Display this help message.", help = true)
    private boolean help = false;

    @Parameter(names = "-f", description = "Read from file. If not set, read from STDIN.")
    private String filename;

    public boolean help() {
        return help;
    }

    public Optional<String> filename() {
        return Optional.ofNullable(filename);
    }
}
