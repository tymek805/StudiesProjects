#pragma once
#include <vector>
#include "Individual.h"
#include "Evaluator.h"
class GeneticAlgorithm
{
public:
	GeneticAlgorithm(int populationSize, float crossProbability, float mutationProbability, CLFLnetEvaluator& evaluator);
	~GeneticAlgorithm();
	void runOneIteration();
	void runIterations(int numberOfIterations);
	Individual* getBestCandidate();
private:
	std::vector<Individual*> population;
	CLFLnetEvaluator& evaluator;
	Individual* bestIndividual;
	float crossProbability;
	float mutationProbability;

	void initializePopulation(int populationSize);
	void evaluatePopulation();
	Individual* getRandomParent();

	void verify();
};