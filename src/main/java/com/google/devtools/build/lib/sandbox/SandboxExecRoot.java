// Copyright 2016 The Bazel Authors. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.devtools.build.lib.sandbox;

import com.google.devtools.build.lib.vfs.Path;
import com.google.devtools.build.lib.vfs.PathFragment;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * SandboxExecRoot is responsible for making a list of input files available inside the directory,
 * so that a process running inside the directory can access the files. It also handles moving the
 * output files generated by the process out of the directory into a destination directory.
 */
public interface SandboxExecRoot {

  /**
   * Creates the sandboxed execution root, making all {@code inputs} available for reading, making
   * sure that the parent directories of all {@code outputs} and that all {@code writableDirs}
   * exist and can be written into.
   *
   * @param inputs  Specifies the input files to be made available inside the directory. The key of
   *     the map is a relative path inside the sandboxed execution root, while the value is the
   *     absolute path of the file in the filesystem.
   * @param outputs  Output files that the process is expected to write to as relative paths to the
   *     sandboxed execution root.
   * @param writableDirs  Directories that the process may write into. All paths that are not inside
   *     the sandboxed execution root must be ignored by this method.
   * @throws IOException
   */
  void createFileSystem(
      Map<PathFragment, Path> inputs, Collection<PathFragment> outputs, Set<Path> writableDirs)
      throws IOException;

  /**
   * Moves all {@code outputs} to {@code execRoot} while keeping the directory structure.
   *
   * @throws IOException
   */
  void copyOutputs(Path execRoot, Collection<PathFragment> outputs) throws IOException;

}