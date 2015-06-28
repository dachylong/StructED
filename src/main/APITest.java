/*
 * StructED - Machine Learning, Structured Prediction Package written in Java.
 * Copyright (C) 2015 Yossi Adi, E-Mail: yossiadidrum@gmail.com
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

package main;

import BL.Algorithms.CRF;
import BL.Algorithms.PassiveAggressive;
import BL.Algorithms.SVM;
import BL.Inference.InferenceDummyData;
import BL.Inference.InferenceMultiClass;
import BL.StructEDModel;
import BL.TaskLoss.TaskLossDummyData;
import BL.TaskLoss.TaskLossMultiClass;
import Constants.Consts;
import Data.*;
import Data.Entities.PredictedLabels;
import Data.Entities.Vector;
import Data.FeatureFunctions.FeatureFunctionsDummy;
import Data.FeatureFunctions.FeatureFunctionsSparse;
import DataAccess.Reader;

import java.util.ArrayList;

import static Data.Factory.getReader;

/**
 * Created by yossiadi on 6/28/15.
 */
public class APITest {

    public static void main(String[] args) throws Exception {

        // ============================ DUMMY DATA ============================ //
        Logger.info("Dummy data example.");
        int readerType = 0;
        int epochNum = 3;
        int isAvg = 1;
        int numExamples2Display = 3;
        String trainPath = "data/db/dummy/train.txt";
        String testPath = "data/db/dummy/test.txt";

        // load the data
        Reader reader = getReader(readerType);
        InstancesContainer dummyTrainInstances = reader.readData(trainPath, Consts.SPACE, Consts.COLON_SPLITTER);
        InstancesContainer dummyTestInstances = reader.readData(testPath, Consts.SPACE, Consts.COLON_SPLITTER);
        if ( dummyTrainInstances.getSize() == 0 ) return;


        // ======= PASSIVE AGGRESSIVE ====== //
        Vector W = new Vector() {{put(0, 0.0);}}; // init the first weight vector
        ArrayList<Double> arguments = new ArrayList<Double>(){{add(3.0);}}; // model parameters
        ArrayList<Double> task_loss_params = new ArrayList<Double>(){{add(1.0);}}; // task loss parameters

        StructEDModel dummy_model = new StructEDModel(W, new PassiveAggressive(), new TaskLossDummyData(),
                new InferenceDummyData(), null, new FeatureFunctionsDummy(), arguments); // create the model
        dummy_model.train(dummyTrainInstances, task_loss_params, null, epochNum, isAvg); // train
        ArrayList<PredictedLabels> labels = dummy_model.predict(dummyTestInstances, task_loss_params, numExamples2Display); // predict


        // print the prediction
        for(int i=0 ; i<dummyTestInstances.getSize() ; i++)
            Logger.info("Y = "+dummyTestInstances.getInstance(i).getLabel()+", Y_HAT = "+labels.get(i).firstKey());
        Logger.info("");

        // ======= CRF ====== //
        W = new Vector() {{put(0, 0.0);}}; // init the first weight vector
        arguments = new ArrayList<Double>(){{add(0.1);add(0.1);}}; // model parameters

        StructEDModel dummy_model_crf = new StructEDModel(W, new CRF(), new TaskLossDummyData(),
                new InferenceDummyData(), null, new FeatureFunctionsDummy(), arguments); // create the model
        dummy_model_crf.train(dummyTrainInstances, task_loss_params, null, epochNum, isAvg); // train
        labels = dummy_model_crf.predict(dummyTestInstances, task_loss_params, numExamples2Display); // predict

        // print the prediction
        for(int i=0 ; i<dummyTestInstances.getSize() ; i++)
            Logger.info("Y = "+dummyTestInstances.getInstance(i).getLabel()+", Y_HAT = "+labels.get(i).firstKey());
        Logger.info("");
        // ==================================================================== //


        // ============================ MNIST DATA ============================ //
        Logger.info("MNIST data example.");
        trainPath = "data/db/MNIST/small.train.txt";
        testPath = "data/db/MNIST/test.data.txt";
        String valPath = "data/db/MNIST/val.data.txt";
        epochNum = 1;

        // load the data
        InstancesContainer mnistTrainInstances = reader.readData(trainPath, Consts.SPACE, Consts.COLON_SPLITTER);
        InstancesContainer mnistDevelopInstances = reader.readData(valPath, Consts.SPACE, Consts.COLON_SPLITTER);
        InstancesContainer mnistTestInstances = reader.readData(testPath, Consts.SPACE, Consts.COLON_SPLITTER);
        if ( mnistTrainInstances.getSize() == 0 ) return;

        // ======= SVM ====== //
        W = new Vector() {{put(0, 0.0);}}; // init the first weight vector
        arguments = new ArrayList<Double>(){{add(0.1); add(0.1);}}; // model parameters

        StructEDModel mnist_model = new StructEDModel(W, new SVM(), new TaskLossMultiClass(),
                new InferenceMultiClass(), null, new FeatureFunctionsSparse(), arguments); // create the model
        mnist_model.train(mnistTrainInstances, task_loss_params, mnistDevelopInstances, epochNum, isAvg); // train
        mnist_model.predict(mnistTestInstances, null, numExamples2Display); // predict
        mnist_model.plotValidationError(false); // plot the error on the validation set
        // ==================================================================== //


        // ============================ DUMMY DATA ============================ //
        // ======================= LOAD & SAVE THE MODEL ====================== //
        Logger.info("Save and Load Dummy model.");

        dummy_model.saveModel("data/weights/dummy/dummy.model"); // save the pa model of the dummy data
        arguments = new ArrayList<Double>(){{add(3.0);}}; // model parameters

        StructEDModel loaded_model = new StructEDModel(null, new PassiveAggressive(), new TaskLossDummyData(),
                new InferenceDummyData(), null, new FeatureFunctionsDummy(), arguments); // create the model
        loaded_model.loadModel("data/weights/dummy/dummy.model"); // load the saved model
        loaded_model.predict(dummyTestInstances, task_loss_params, numExamples2Display); // predict

        // print the prediction
        for(int i=0 ; i<dummyTestInstances.getSize() ; i++)
            Logger.info("Y = "+dummyTestInstances.getInstance(i).getLabel()+", Y_HAT = "+labels.get(i).firstKey());
        Logger.info("");
        // ==================================================================== //
    }
}