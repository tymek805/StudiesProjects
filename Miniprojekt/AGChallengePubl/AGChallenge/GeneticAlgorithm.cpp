#include "GeneticAlgorithm.h"

GeneticAlgorithm::GeneticAlgorithm(int populationSize, float crossProbability, float mutationProbability, CLFLnetEvaluator& evaluator)
	: evaluator(evaluator), crossProbability(crossProbability), mutationProbability(mutationProbability)
{
	initializePopulation(populationSize);
	bestIndividual = new Individual(*population.at(0));
}

GeneticAlgorithm::~GeneticAlgorithm()
{
	delete bestIndividual;
	for (int i = 0; i < population.size(); i++)
		delete population.at(i);
}

void GeneticAlgorithm::runOneIteration()
{
	vector<Individual*> newPopulation;
	while (newPopulation.size() < population.size())
	{
		Individual* parent1 = getRandomParent();
		Individual* parent2 = getRandomParent();

		if (dRand() < crossProbability)
		{
			vector<Individual*> crossChildren = parent1->cross(*parent2);
			for (int i = 0; i < crossChildren.size(); i++)
				newPopulation.push_back(crossChildren.at(i));

			delete parent1, parent2;
		}
		else
		{
			newPopulation.push_back(parent1);
			newPopulation.push_back(parent2);
		}
	}

	for (int i = 0; i < newPopulation.size(); i++)
		newPopulation.at(i)->mutate(mutationProbability);

	for (int i = 0; i < population.size(); i++) 
		delete population.at(i);

	population = newPopulation;
	verify();
}

void GeneticAlgorithm::verify()
{
	bool isCompleted = false;
	while (!isCompleted && population.size() > 1) 
	{
		isCompleted = true;
		for (int i = 0; i < population.size() - 1; i++)
		{
			for (int j = i + 1; j < population.size(); j++)
			{
				Individual* individual1 = population.at(i);
				Individual* individual2 = population.at(j);
				if (individual1 == individual2)
				{
					individual2->mutate(1.0);
					isCompleted = false;
				}
			}
		}
	}
}

void GeneticAlgorithm::runIterations(int numberOfIterations)
{
	for (int i = 0; i < numberOfIterations; i++) 
	{
		runOneIteration();
		evaluatePopulation();
		std::cout << getBestCandidate()->getFitness() << std::endl;
	}
}

Individual* GeneticAlgorithm::getBestCandidate()
{
	return bestIndividual;
}

void GeneticAlgorithm::initializePopulation(int populationSize)
{
	for (int i = 0; i < populationSize; i++) 
	{
		std::vector<int> genotype;
		
		for (int j = 0; j < evaluator.iGetNumberOfBits(); j++)
		{
			genotype.push_back(lRand(evaluator.iGetNumberOfValues(j)));
		}
		population.push_back(new Individual(std::move(genotype), evaluator));
	}
}

void GeneticAlgorithm::evaluatePopulation()
{
	Individual* oldBestIndividual = bestIndividual;

	for (int i = 0; i < population.size(); i++)
	{
		Individual* currentIndividual = population.at(i);
		if (currentIndividual->getFitness() > oldBestIndividual->getFitness())
			oldBestIndividual = currentIndividual;
	}

	bestIndividual = new Individual(*oldBestIndividual);
}

Individual* GeneticAlgorithm::getRandomParent()
{
	Individual* candidate1 = population[dRand() * population.size()];
	Individual* candidate2 = population[dRand() * population.size()];
	return candidate1->getFitness() > candidate2->getFitness() ? new Individual(*candidate1) : new Individual(*candidate2);
}