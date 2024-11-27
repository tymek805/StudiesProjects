#include "Evaluator.h"
#include "Optimizer.h"
#include "Timer.h"

#include <exception>
#include <iostream>
#include <random>
#include "GeneticAlgorithm.h"

using namespace TimeCounters;

using namespace std;

#define POPULATION_SIZE 100
#define CROSS_PROBABILITY 0.9
#define MUTATION_PROBABILITY 0.001
#define NUMBER_OF_ITERATIONS 10000


void vRunExperiment(CLFLnetEvaluator &cConfiguredEvaluator)
{
	try
	{
		GeneticAlgorithm algorithm(POPULATION_SIZE, CROSS_PROBABILITY, MUTATION_PROBABILITY, cConfiguredEvaluator);
		algorithm.runIterations(NUMBER_OF_ITERATIONS);
		cout << "Best candidate: " << algorithm.getBestCandidate()->getFitness() << endl;
	}//try
	catch (exception &c_exception)
	{
		cout << c_exception.what() << endl;
	}//catch (exception &c_exception)
}//void vRunExperiment(const CEvaluator &cConfiguredEvaluator)



void  vRunLFLExperiment(CString  sNetName)
{
	CLFLnetEvaluator c_lfl_eval;
	c_lfl_eval.bConfigure(sNetName);
	vRunExperiment(c_lfl_eval);
	
}//void vRunRastriginExperiment(int iNumberOfBits, int iBitsPerFloat, int iMaskSeed)



void main(int iArgCount, char **ppcArgValues)
{
	random_device c_mask_seed_generator;
	int i_mask_seed = (int)c_mask_seed_generator();


	//CString  s_test;
	vRunLFLExperiment("104b00");

	/*vRunIsingSpinGlassExperiment(81, 0, i_mask_seed);
	vRunIsingSpinGlassExperiment(81, 0, iSEED_NO_MASK);

	vRunLeadingOnesExperiment(50, i_mask_seed);
	vRunLeadingOnesExperiment(50, iSEED_NO_MASK);

	vRunMaxSatExperiment(25, 0, 4.27f, i_mask_seed);
	vRunMaxSatExperiment(25, 0, 4.27f, iSEED_NO_MASK);

	vRunNearestNeighborNKExperiment(100, 0, 4, i_mask_seed);
	vRunNearestNeighborNKExperiment(100, 0, 4, iSEED_NO_MASK);

	vRunOneMaxExperiment(100, i_mask_seed);
	vRunOneMaxExperiment(100, iSEED_NO_MASK);

	vRunRastriginExperiment(200, 10, i_mask_seed);
	vRunRastriginExperiment(200, 10, iSEED_NO_MASK);*/
}//void main(int iArgCount, char **ppcArgValues)