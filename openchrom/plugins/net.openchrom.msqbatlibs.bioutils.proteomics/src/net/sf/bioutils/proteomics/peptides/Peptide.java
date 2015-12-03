/*******************************************************************************
 * Copyright 2011-2014 Alexander Kerner. All rights reserved.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.bioutils.proteomics.peptides;

import java.util.Collection;
import java.util.List;

public interface Peptide extends Iterable<AminoAcid> {

    public static double MOL_WEIGHT_OXYGEN = 15.9994;

    public static double MOL_WEIGHT_HYDROGEN = 1.0078;

    List<AminoAcid> asAminoAcidList();

    List<Character> asCharacterList();

    String asString();

    List<String> asStringList();

    boolean contains(AminoAcid p);

    int getChargeState();

    Collection<Modification> getModifications();

    double getMolWeight();

    double getMolWeightCTerminal();

    double getMolWeightNTerminal();

    void setModifications(Collection<Modification> modifications);

}
