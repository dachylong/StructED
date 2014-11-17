======
README
======

LaStrada 1.0.0

26 April 2014

Copyright (C) Bar-Ilan  University

This is a README file for the Structure Prediction Package

	==========How to use===========

	==========TRAIN FILE===========

	=====Config file parmeters=====

all the parameters should be as: parameter type then colon ( : ) and then the value of the parameter 

	===============GENERAL================

Here we present the configuration file which loads all the relevant parameters
first we present the general parameters for every algorithm
Few parameters are mandatory and the others are optional, we state for every parameter his condition


 - train_path:							 the path to the training set data - Mandatory 
 - w_output:							 output file of the weights vector (model)	

 - type:							 the algorithm type - Mandatory
 								 0 - Passive Aggressive
								 1 - SVM
								 2 - Direct Loss
								 3 - CRF
								 4 - Ramp Loss
								 5 - Probit Loss
								 6 - RankSVM 
								
 - task:							 the cost/loss function number - Mandatory
 
 - epoch:							 the number of epochs on the data - Mandatory
 
 - task_param:							 cost/loss parameters if needed - Optional
 
 - kernel:							 kernel type, and parameters(i.e sigma) - Optional
 								 0 - poly 2 degree
								 1 - RBF 2nd taylor approximation
								 2 - RBF 3nd taylor approximation	
								
 - init_w:							 the path for the initial weights - Optional
 
 - phi:								 feature function type - Mandatory
 
 - prediction:							 prediction function, should implement also the loss-augmented function - Mandatory
 
 - reader:							 reader type - Mandatory
 - writer:							 writer type - Mandatory
 								 for both reader and writer
								 use 0 for standard form
								 use 1 for standard rank form (as Letor 3.0)
 
 - size_of_vector:						 size of vector after the feature mapping functions - Mandatory
  
	RELEVANT FILES: taskLoss, phiConverter and prediction interfaces and the Factory class

  for the task, phi and prediction the user should implement the taskLoss, phiConverter and prediction interfaces
  after implementing those interfaces the Factory class should be updated by to support the new classes that we've just implement
  we do this by adding another case to the switch-case statement at the getClassifier function
 

	   =======================================
	   ===============SPECIFIC================

 parameters for the algorithm, will be different for each algorithm - Mandatory

	===PA===									   
 - c: 				 C parameter for the PA algorithm				


	===SVM===							  
 - lambda:				 lambda parameter for the SVM						
 - eta:				 eta - learning rate
			    
						  
	===DL===									   
 - eta:				 eta - learning rate
 - epsilon:			 epsilon parameter for the DL	   
						  

	===CRF===	
 - eta:				 eta - learning rate
 - lambda:				 lambda parameter for the CRF				  			
			
						 						  						  
	===RL===	
 - eta:				 eta - learning rate
 - lambda:				 lambda parameter for the RL				  			
						  
						  
	===PL===	
 - eta:				 eta - learning rate
 - lambda:				 lambda parameter for the PL	
 - num_of_iteration:		 number of iteration for generation noise			  

						  						  
	===RankSVM===						  
 - lambda:				 lambda parameter for the RankSVM					
 - eta				 eta - learning rate			    

==============================================================

	==========TEST FILE===========
 - test_path:							 the path to the test set data - Mandatory 

 - output_file:							 the output file for the scores - Mandatory 

 - w_path:								 the weights vector(model) - Mandatory 

 - examples_2_display:						 how many examples to display in the scores file - Mandatory

 - task:								 the loss(cost) function, the same as the train - Mandatory

 - kernel:								 kernel type, and parameters(i.e sigma) - Optional
 								 0 - poly 2 degree
								 1 - RBF 2nd taylor approximation
								 2 - RBF 3nd taylor approximation
	
 - phi:								 feature function type - Mandatory

 - prediction:							 prediction function, should implement also the loss-augmented function - Mandatory

 - reader:								 reader type - Mandatory
 - writer:								 writer type - Mandatory
 								 for both reader and writer
								 use 0 for standard form
								 use 1 for standard rank form (as Letor 3.0)

 - size_of_vector:							 size of vector after the feature mapping functions - Mandatory

==========================Examples===========================

 We provided three examples of implementation of all the interferes that was mentioned above

 The first one is dummy data
 The second one is the MNIST dataset
 The third one is the letor 3.0 dataset

 All the examples can be found on our website

=============================================================