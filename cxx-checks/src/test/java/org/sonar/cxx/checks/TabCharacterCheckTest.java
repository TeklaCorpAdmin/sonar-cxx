/*
 * Sonar C++ Plugin (Community)
 * Copyright (C) 2011 Waleri Enns and CONTACT Software GmbH
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.cxx.checks;

import java.io.File;

import org.junit.Test;
import org.sonar.cxx.CxxAstScanner;
import org.sonar.squidbridge.api.SourceFile;
import org.sonar.squidbridge.checks.CheckMessagesVerifier;

public class TabCharacterCheckTest {

  private TabCharacterCheck check = new TabCharacterCheck();

  @Test
  public void fileWithTabsOneMessagePerFile() {
    check.createLineViolation = false;
    SourceFile file = CxxAstScanner.scanSingleFile(new File("src/test/resources/checks/TabCharacter.cc"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().withMessage("Replace all tab characters in this file by sequences of white-spaces.")
      .noMore();
  }

  @Test
  public void fileWithTabsOneMessagePerLine() {
    check.createLineViolation = true;
    SourceFile file = CxxAstScanner.scanSingleFile(new File("src/test/resources/checks/TabCharacter.cc"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .next().atLine(3).withMessage("Replace all tab characters in this line by sequences of white-spaces.")
      .next().atLine(4)
      .noMore();
  }

  @Test
  public void fileWithoutTabs() {
    check.createLineViolation = false;
    SourceFile file = CxxAstScanner.scanSingleFile(new File("src/test/resources/checks/NonEmptyFile.cc"), check);
    CheckMessagesVerifier.verify(file.getCheckMessages())
      .noMore();
  }

}
