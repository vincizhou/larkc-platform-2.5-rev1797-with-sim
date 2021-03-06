/***
 *   Copyright (c) 1995-2009 Cycorp Inc.
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *   
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 *  Substantial portions of this code were developed by the Cyc project
 *  and by Cycorp Inc, whose contribution is gratefully acknowledged.
*/

package  com.cyc.tool.subl.jrtl.nativeCode.subLisp;

import com.cyc.tool.subl.jrtl.nativeCode.type.core.SubLObject;
import com.cyc.tool.subl.jrtl.nativeCode.type.core.SubLObjectFactory;
import com.cyc.tool.subl.jrtl.nativeCode.type.core.SubLSequence;
import com.cyc.tool.subl.jrtl.nativeCode.type.core.SubLVector;
import com.cyc.tool.subl.jrtl.nativeCode.type.number.SubLInteger;
import com.cyc.tool.subl.util.SubLFile;
import com.cyc.tool.subl.util.SubLFiles;

//// Internal Imports

//// External Imports

public  final class Vectors implements SubLFile {
  
  //// Constructors
  
  /** Creates a new instance of Vectors. */
  private Vectors() {}
  public static final SubLFile me = new Vectors();
  
  //// Public Area
  
  public static final SubLObject aref(SubLObject vector, SubLObject index) {
    return vector.get(index.intValue());
  }
  
  public static final SubLObject set_aref(SubLObject vector, SubLObject index, SubLObject value) {
    vector.set(index.intValue(), value);
    return value;
  }
  
  public static final SubLObject make_vector(SubLObject size, SubLObject initialElement) {
    if (initialElement == UNPROVIDED) { initialElement = NIL; }
    return SubLObjectFactory.makeVector(size.intValue(), initialElement);
  }
  
  public static final SubLObject vector(SubLObject[] objects) {
    return SubLObjectFactory.makeVector(objects);
  }
  
  //// Initializers
  
  public void declareFunctions() {
    SubLFiles.declareFunction(me, "aref",        "AREF",        2, 0, false);
    SubLFiles.declareFunction(me, "set_aref",    "SET-AREF",    3, 0, false);
    SubLFiles.declareFunction(me, "make_vector", "MAKE-VECTOR", 1, 1, false);
    SubLFiles.declareFunction(me, "vector",      "VECTOR",      0, 0, true);
  }
  
  public void initializeVariables() {
  }
  
  public void runTopLevelForms() {
  }
  
  //// Protected Area
  
  //// Private Area
  
  //// Internal Rep
  
  //// Main
  
}
