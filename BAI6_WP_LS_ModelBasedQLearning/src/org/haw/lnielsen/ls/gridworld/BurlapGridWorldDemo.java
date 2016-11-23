package org.haw.lnielsen.ls.gridworld;

import java.util.Objects;
import burlap.behavior.singleagent.auxiliary.performance.LearningAlgorithmExperimenter;
import burlap.behavior.singleagent.auxiliary.performance.PerformanceMetric;
import burlap.behavior.singleagent.auxiliary.performance.TrialMode;
import burlap.behavior.singleagent.learning.LearningAgent;
import burlap.behavior.singleagent.learning.LearningAgentFactory;
import burlap.behavior.singleagent.learning.modellearning.artdp.ARTDP;
import burlap.behavior.singleagent.learning.tdmethods.QLearning;
import burlap.behavior.singleagent.learning.tdmethods.SarsaLam;
import burlap.domain.singleagent.gridworld.GridWorldDomain;
import burlap.domain.singleagent.gridworld.GridWorldVisualizer;
import burlap.domain.singleagent.gridworld.state.GridAgent;
import burlap.domain.singleagent.gridworld.state.GridLocation;
import burlap.domain.singleagent.gridworld.state.GridWorldState;
import burlap.mdp.auxiliary.common.ConstantStateGenerator;
import burlap.mdp.auxiliary.common.SinglePFTF;
import burlap.mdp.core.oo.propositional.PropositionalFunction;
import burlap.mdp.singleagent.SADomain;
import burlap.mdp.singleagent.common.GoalBasedRF;
import burlap.mdp.singleagent.common.VisualActionObserver;
import burlap.mdp.singleagent.environment.SimulatedEnvironment;
import burlap.mdp.singleagent.oo.OOSADomain;
import burlap.statehashing.HashableStateFactory;
import burlap.statehashing.simple.SimpleHashableStateFactory;
import burlap.visualizer.Visualizer;

public class BurlapGridWorldDemo {
	public static void main(String [] args) {
		GridWorldDomain gridworld = new GridWorldDomain(11, 11);
		gridworld.setMapToFourRooms();
		gridworld.setObstacleInCell(2, 2);
		
		// Ends when the agent reaches a location
		gridworld.setTf(new SinglePFTF(PropositionalFunction.findPF(gridworld.generatePfs(), GridWorldDomain.PF_AT_LOCATION)));
		// Reward-Function (Zielbedingung, Zielbelohnung, Wegkosten)
		gridworld.setRf(new GoalBasedRF(gridworld.getTf(), 5.0, -0.1));
		
		// Generate Domain
		final OOSADomain domain = gridworld.generateDomain();
		GridWorldState state = new GridWorldState(
				new GridAgent(0, 0),              // Start-Location
				new GridLocation(10, 10, "loc0")  // Goal-Location
		);
		
		// Define Learning Environment
		SimulatedEnvironment env = new SimulatedEnvironment(domain, new ConstantStateGenerator(state));
		
		// GUI
		Visualizer visualizer = GridWorldVisualizer.getVisualizer(gridworld.getMap());
		VisualActionObserver vob = new VisualActionObserver(domain, visualizer);
		vob.initGUI();
		env.addObservers(vob);
		
		// Define Experiment
		LearningAlgorithmExperimenter experiment = new LearningAlgorithmExperimenter(
				env, 10, 100,
				qLearning(domain),
				sarsaLambda(domain),
				artdp(domain)
		);
		experiment.setUpPlottingConfiguration(
				500, 300,
				2,
				1000, 
				TrialMode.MOST_RECENT_AND_AVERAGE,
				PerformanceMetric.STEPS_PER_EPISODE,
				PerformanceMetric.CUMULATIVE_STEPS_PER_EPISODE, 
				PerformanceMetric.AVERAGE_EPISODE_REWARD
		);
		experiment.startExperiment();
	}
	
	private static LearningAgentFactory qLearning(SADomain domain) {
		return new AgentFactory("QLearning") {
			@Override
			public LearningAgent generateAgent() {
				return new QLearning(domain, 0.99, hashingFactory(), 0.3, 0.1);
			}
		};
	}
	
	private static LearningAgentFactory sarsaLambda(SADomain domain) {
		return new AgentFactory("Sarsa(Lambda)") {
			@Override
			public LearningAgent generateAgent() {
				return new SarsaLam(domain, 0.99, hashingFactory(), 0.3, 0.1, 1);
			}
		};
	}
	
	private static LearningAgentFactory artdp(SADomain domain) {
		return new AgentFactory("Adaptive Realtime Dynamic Programming") {
			@Override
			public LearningAgent generateAgent() {
				return new ARTDP(domain, 0.99, hashingFactory(), 0.3);
			}
		};
	}
	
	private static abstract class AgentFactory implements LearningAgentFactory {
		private String _agentName;
		private final SimpleHashableStateFactory _hashingFactory = new SimpleHashableStateFactory();
		
		public AgentFactory(String agentName) {
			_agentName = Objects.requireNonNull(agentName);
		}
		
		@Override
		public String getAgentName() {
			return _agentName;
		}
		
		public HashableStateFactory hashingFactory() {
			return _hashingFactory;
		}
	}
}
