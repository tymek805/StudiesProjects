#include "Individual.h"

Individual::Individual(const vector<int>& genotype, CLFLnetEvaluator& evaluator) : genotype(genotype), evaluator(evaluator), fitness(NULL)
{
}

Individual::Individual(const Individual& other) : genotype(other.genotype), evaluator(other.evaluator), fitness(other.fitness)
{
}

bool Individual::operator==(Individual& other)
{
	for (int i = 0; i < genotype.size(); i++)
	{
		if (genotype.at(i) != other.genotype.at(i))
			return false;
	}
	return true;
}

void Individual::mutate(float mutationProbability)
{
	for (int i = 0; i < genotype.size(); i++)
	{
		if (dRand() < mutationProbability)
			genotype.at(i) = lRand(evaluator.iGetNumberOfValues(i));
	}
}

std::vector<Individual*> Individual::cross(const Individual& other)
{
	int crossoverPoint = dRand() * genotype.size();
	std::vector<int> genotype1, genotype2;

	for (int i = 0; i < genotype.size(); i++) {
		if (crossoverPoint < i)
		{
			genotype1.push_back(genotype.at(i));
			genotype2.push_back(other.genotype.at(i));
		}
		else
		{
			genotype2.push_back(genotype.at(i));
			genotype1.push_back(other.genotype.at(i));
		}
	}

	return
	{
		new Individual(genotype1, evaluator),
		new Individual(genotype2, evaluator)
	};
}

double Individual::getFitness()
{
	if (fitness == NULL)
		fitness = evaluator.dEvaluate(&genotype);
	return fitness;
}
