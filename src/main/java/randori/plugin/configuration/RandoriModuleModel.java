/*
 * Copyright 2013 original Randori IntelliJ Plugin authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package randori.plugin.configuration;

/**
 * @author Michael Schmalle
 * @author Frédéric THOMAS
 */
public class RandoriModuleModel
{
    //----------------------------------
    // generateRbl
    //----------------------------------

    private boolean generateRbl = false;

    public boolean isGenerateRbl() {
        return generateRbl;
    }

    public void setGenerateRbl(boolean generateRbl) {
        this.generateRbl = generateRbl;
    }

    //----------------------------------
    // exportAsFile
    //----------------------------------

    private boolean exportAsFile = true;

    public boolean isExportAsFile() {
        return exportAsFile;
    }

    public void setExportAsFile(boolean exportAsFile) {
        this.exportAsFile = exportAsFile;
    }
}
