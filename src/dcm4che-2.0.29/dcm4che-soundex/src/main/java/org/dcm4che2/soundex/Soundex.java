/* ***** BEGIN LICENSE BLOCK *****
 * Version: MPL 1.1/GPL 2.0/LGPL 2.1
 *
 * The contents of this file are subject to the Mozilla Public License Version
 * 1.1 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * http://www.mozilla.org/MPL/
 *
 * Software distributed under the License is distributed on an "AS IS" basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License
 * for the specific language governing rights and limitations under the
 * License.
 *
 * The Original Code is part of dcm4che, an implementation of DICOM(TM) in
 * Java(TM), hosted at http://sourceforge.net/projects/dcm4che.
 *
 * The Initial Developer of the Original Code is
 * Agfa HealthCare.
 * Portions created by the Initial Developer are Copyright (C) 2010
 * the Initial Developer. All Rights Reserved.
 *
 * Contributor(s):
 * See listed authors below.
 *
 * Alternatively, the contents of this file may be used under the terms of
 * either the GNU General Public License Version 2 or later (the "GPL"), or
 * the GNU Lesser General Public License Version 2.1 or later (the "LGPL"),
 * in which case the provisions of the GPL or the LGPL are applicable instead
 * of those above. If you wish to allow use of your version of this file only
 * under the terms of either the GPL or the LGPL, and not to allow others to
 * use your version of this file under the terms of the MPL, indicate your
 * decision by deleting the provisions above and replace them with the notice
 * and other provisions required by the GPL or the LGPL. If you do not delete
 * the provisions above, a recipient may use your version of this file under
 * the terms of any one of the MPL, the GPL or the LGPL.
 *
 * ***** END LICENSE BLOCK ***** */

package org.dcm4che2.soundex;

public class Soundex implements FuzzyStr {

    private final boolean encodeFirst;
    private final int codeLength;
    private final int padLength;

    public Soundex() {
        this(false, 4, 4);
    }

    public Soundex(boolean encodeFirst, int codeLength, int padLength) {
        this.encodeFirst = encodeFirst;
        this.codeLength = codeLength;
        this.padLength = padLength;
       
    }

    public String toFuzzy(String s) {
        if (s == null || s.length() == 0)
            return "";

        char[] in = s.toUpperCase().toCharArray();
        char[] out = in.length < padLength ? new char[padLength] : in;
        int i = 0;
        int j = 0;
        if (!encodeFirst) {
            while (!Character.isLetter(in[i]))
                if (++i >= in.length)
                    return "";
            out[j++] = in[i++];
        }

        char prevout = 0;
        char curout = 0;
        for (; i < in.length && j < codeLength; i++) {
            switch (in[i]) {
            case 'A':
            case 'Ä':
            case 'À':
            case 'Á':
            case 'Â':
            case 'Ã':
            case 'Å':
            case 'Æ':
            case 'E':
            case 'È':
            case 'É':
            case 'Ê':
            case 'Ë':
            case 'I':
            case 'Ì':
            case 'Í':
            case 'Î':
            case 'Ï':
            case 'O':
            case 'Ò':
            case 'Ó':
            case 'Ô':
            case 'Õ':
            case 'Ö':
            case 'Ø':
            case 'U':
            case 'Ù':
            case 'Ú':
            case 'Û':
            case 'Ü':
            case 'Y':
            case 'Ý':
            case 'H':
            case 'W':
                prevout = '0';
                continue;
            case 'B':
            case 'F':
            case 'P':
            case 'V':
                curout = '1';
                break;
            case 'C':
            case 'Ç':
            case 'G':
            case 'J':
            case 'K':
            case 'Q':
            case 'S':
            case 'X':
            case 'Z':
            case 'ß':
                curout = '2';
                break;
            case 'D':
            case 'T':
                curout = '3';
                break;
            case 'L':
                curout = '4';
                break;
            case 'M':
            case 'N':
                curout = '5';
                break;
            case 'R':
                curout = '6';
                break;
            default:
                continue;
            }
            if (prevout != curout)
                out[j++] = prevout = curout;
        }
        while (j < padLength)
            out[j++] = '0';
        return new String(out, 0, j);
    }

    public static void main(String[] args) {
        Soundex inst = new Soundex();
        for (String arg : args)
            System.out.println(inst.toFuzzy(arg));
    }

}
