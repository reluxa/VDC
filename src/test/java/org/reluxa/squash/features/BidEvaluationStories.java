package org.reluxa.squash.features;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.StoryReporterBuilder.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;
import org.reluxa.squash.MockBidService;
import org.reluxa.squash.MockPlayerService;
import org.reluxa.squash.MockTimeService;
import org.reluxa.squash.steps.BidEvaluatorSteps;
import org.reluxa.squash.steps.BidSteps;
import org.reluxa.squash.steps.PlayerSteps;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith(JUnitReportingRunner.class)
public class BidEvaluationStories extends JUnitStories {

  // Here we specify the configuration, starting from default MostUsefulConfiguration, and changing only what is needed
  @Override
  public Configuration configuration() {
      return new MostUsefulConfiguration()
          // where to find the stories
          .useStoryLoader(new LoadFromClasspath(this.getClass()))
          // CONSOLE and TXT reporting
          .useStoryReporterBuilder(new StoryReporterBuilder().withDefaultFormats().withFormats(Format.STATS, Format.CONSOLE, Format.TXT));
  }

  // Here we specify the steps classes
  @Override
  public InjectableStepsFactory stepsFactory() {       
      // varargs, can have more that one steps classes
  	
  		MockTimeService timeService = new MockTimeService();
  		MockPlayerService playerService = new MockPlayerService();
  		MockBidService bidService = new MockBidService();
  	
  		BidSteps bidSteps = new BidSteps(bidService, playerService);
  		PlayerSteps playerSteps = new PlayerSteps(playerService);
  		BidEvaluatorSteps bidEvaluatorSteps = new BidEvaluatorSteps(bidService, timeService);
      return new InstanceStepsFactory(configuration(), bidSteps, playerSteps, bidEvaluatorSteps);
  }


	@Override
  protected List<String> storyPaths() {
		return Arrays.asList("example.story");
	}
  
  
	
}
