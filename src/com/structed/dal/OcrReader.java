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

package com.structed.dal;

import com.structed.data.Factory;
import com.structed.data.InstancesContainer;
import com.structed.data.Logger;
import com.structed.data.entities.Example;
import com.structed.data.entities.Vector;

import java.util.ArrayList;

/**
 * Reader for the OCR data set
 * Created by yossiadi on 7/18/15.
 */

public class OcrReader extends StandardReader implements Reader {

    @Override
    public InstancesContainer readData(String path, String dataSpliter, String valueSpliter) {
        // reads the data
        ArrayList<ArrayList<String>> data = readFile(path, dataSpliter);
        // create an Instance Container
        InstancesContainer container = Factory.getInstanceContainer(0);
        int startFeature = 5;
        ArrayList<Example> instances = new ArrayList<Example>();
        ArrayList<Vector> exampleFeatures = new ArrayList<Vector>();
        String label = "";

        for (ArrayList<String> aData : data) {
            String currChar = (aData.get(1)); // the char label is placed at the index number 1
            Vector vec = new Vector();
            boolean isLastCher = (aData.get(2).equalsIgnoreCase("-1"));

            // the features starts from index 5
            for (int j = startFeature; j < aData.size(); j++)
                if (!aData.get(j).equals("0"))
                    vec.put(j - startFeature, Double.valueOf(aData.get(j)));

            // adding the current row and label
            exampleFeatures.add(vec);
            label += currChar;
            if (isLastCher) {
                // create 2D example
                Example example = Factory.getExample(1);
                example.setFeatures2D((ArrayList<Vector>) exampleFeatures.clone());
                example.setLabel(label);
                instances.add(example);
                label = "";
                exampleFeatures.clear();
            }
        }

        container.setInstances(instances);
        return container;
    }
}
