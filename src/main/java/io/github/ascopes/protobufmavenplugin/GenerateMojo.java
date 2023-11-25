/*
 * Copyright (C) 2023, Ashley Scopes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.ascopes.protobufmavenplugin;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Collectors;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.project.MavenProject;

/**
 * Mojo to generate source code from protobuf sources.
 *
 * @author Ashley Scopes
 */
@Mojo(
    name = "generate",
    defaultPhase = LifecyclePhase.GENERATE_SOURCES,
    requiresOnline = true,
    threadSafe = true
)
public final class GenerateMojo extends AbstractGenerateMojo {

  /**
   * Initialise this Mojo.
   */
  public GenerateMojo() {
    // Nothing to do.
  }

  /**
   * The directory to output generated sources to.
   *
   * @param outputDirectory the output directory.
   * @since 0.0.1
   */
  @Parameter(defaultValue = MAIN_OUTPUT + "/protobuf")
  public void setOutputDirectory(String outputDirectory) {
    super.setOutputDirectory(Path.of(outputDirectory));
  }

  /**
   * The root directories to look for protobuf sources in.
   *
   * @param sourceDirectories the source directories.
   * @since 0.0.1
   */
  @Parameter(defaultValue = MAIN_SOURCE)
  public void setSourceDirectories(Set<String> sourceDirectories) {
    var parsedDirectories = sourceDirectories
        .stream()
        .map(Path::of)
        .collect(Collectors.toUnmodifiableSet());
    super.setSourceDirectoryPaths(parsedDirectories);
  }

  @Override
  protected void registerSource(MavenProject project, Path path) {
    project.addCompileSourceRoot(path.toString());
  }
}