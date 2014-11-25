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

package BL.Algorithms;

import BL.ClassifierData;
import Constants.ConfigParameters;
import Constants.Consts;
import Constants.ErrorConstants;
import Data.Entities.Example;
import Data.Entities.PredictedLabels;
import Data.Entities.Vector;
import Data.Logger;
import Helpers.MathHelpers;

import java.util.ArrayList;
import java.util.Map;

public class RankSVM implements AlgorithmUpdateRule{

    //Singleton
    private static RankSVM ourInstance = new RankSVM();
    public static RankSVM getInstance(ArrayList<Double> arguments) {
        if(arguments.size() != ConfigParameters.SVM_PARAMS_SIZE){
            Logger.error(ErrorConstants.UPDATE_ARGUMENTS_ERROR);
            return null;
        }
        //initialize the parameters
        ourInstance.lambda = arguments.get(0);
        ourInstance.eta = arguments.get(1);

        return ourInstance;
    }

    private RankSVM() {
    }

    //Data members
    double lambda;
    double eta;

    @Override
    //in SVM the lambda value would be in the first cell of the arguments attribute
    //the second cell of the arguments attribute would be the eta
    public Vector update(Vector currentWeights, Example vector, ClassifierData classifierData) {

        try{
            double algorithmIteration = classifierData.iteration;

            //get the prediction
            PredictedLabels prediction;
            //if there's a problem with the predict return the previous weights
            try{
                prediction = classifierData.predict.predictForTrain(vector,currentWeights,vector.getLabel(),classifierData,1);
            }catch (Exception e){
                return currentWeights;
            }
            Vector result = currentWeights;

            for(Map.Entry<String,Double> entry : prediction.entrySet()) {

                if(entry.getKey().equalsIgnoreCase(Consts.CLASSIFICATION_SPLITTER))
                    continue;
                String[] predictLabels = entry.getKey().split(Consts.CLASSIFICATION_SPLITTER);
                if(predictLabels.length != 2)
                    continue;
                if(predictLabels[0].equalsIgnoreCase("") || predictLabels[1].equalsIgnoreCase(""))
                    continue;

                Example phiRealLabel = classifierData.phi.convert(vector, predictLabels[0], classifierData.kernel);
                Example phiPrediction = classifierData.phi.convert(vector, predictLabels[1], classifierData.kernel);
                Vector phiDifference = MathHelpers.subtract2Vectors(phiRealLabel.getFeatures(), phiPrediction.getFeatures());

                double newEta = eta/Math.sqrt(algorithmIteration);
                double coefficientFirstArgument = (1-(lambda*newEta));

                Vector firstArgument = MathHelpers.mulScalarWithVectors(currentWeights, coefficientFirstArgument);
                Vector secondArgument = MathHelpers.mulScalarWithVectors(phiDifference, newEta);

                result = MathHelpers.add2Vectors(firstArgument, secondArgument);
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}