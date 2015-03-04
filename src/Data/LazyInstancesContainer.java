/*
 * StructED - Machine Learning, Structured Prediction Package written in Java.
 * Copyright (C) 2014 Yossi Adi, E-Mail: yossiadidrum@gmail.com
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package Data;

import Data.Entities.Example;
import DataAccess.VowelDurationReader;

import java.io.File;

public class LazyInstancesContainer extends InstancesContainer{

    VowelDurationReader reader = new VowelDurationReader();

    //C'tor
    public LazyInstancesContainer(){
        super();
    }

    @Override
    //get the requested example
    public Example getInstance(int index) {
        try {
            File file = new File(paths.get(index).get(0));
            if(file.exists()) {
                Example example = reader.readExample(paths.get(index));
                CacheVowelData.updateCache(example);
                example.path = paths.get(index).get(0);
                return example;
            }
            return null;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}