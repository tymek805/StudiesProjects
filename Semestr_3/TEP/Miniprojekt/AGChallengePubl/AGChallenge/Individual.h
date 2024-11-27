#pragma once
#include <vector>
#include "Evaluator.h"

class Individual
{
public:
	Individual(const vector<int>& genotype, CLFLnetEvaluator& evaluator);
	Individual(const Individual& other);
	bool operator==(Individual& other);

	void mutate(float mutationProbability);
	std::vector<Individual*> cross(const Individual &other);
	double getFitness();
private:
	std::vector<int> genotype;
	CLFLnetEvaluator& evaluator;
	double fitness;
};