/*
 * The MIT License (MIT)
 *
 * StructED - Machine Learning Package for Structured Prediction
 *
 * Copyright (c) 2015 Yossi Adi, E-Mail: yossiadidrum@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.structed.data.entities;

import java.io.Serializable;
import java.util.HashMap;

/**
 * this class is used for name alias
 * this Vector class represents a HashMap between the feature index to its value
 */
public class Vector extends HashMap<Integer, Double> implements Serializable{

    private static final long serialVersionUID = 1L;
    private int maxIndex;
    public Vector(){this.maxIndex = -1;}
    public Vector(Vector v){
        super(v);
    }

    @Override
    public Double put(Integer key, Double value){
        super.put(key, value);
        if(maxIndex < key) maxIndex = key;
        return value;
    }

    // getter
    public int getMaxIndex() {
        return maxIndex;
    }
    public void resetMaxIndex(){
        this.maxIndex = -1;
    }
}
